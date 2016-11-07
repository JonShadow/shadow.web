package shadow.web.classLoad;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.internal.toplink.LinkException;

import shadow.web.services.DealTmcService;
import shadow.web.utils.util.StaticContextAccessor;

public class TmcClientDeal implements Runnable,MessageHandler {

	private static final Log logger = LogFactory.getLog(TmcClientDeal.class);
	
	private static String appkey;
	
	private static String appSecret;
	
	private static String tmcUrl;
	
	private static String tmcGroupName;

    protected static Map<Long, Message> notifyMap = new HashMap<Long, Message>();

    protected static int notifyNum = 0;
    
    private DealTmcService dealTmcService;
    
    private void before () {   
        Properties prop = new Properties();   
        InputStream in = getClass().getResourceAsStream("/config.properties");  
        try {   
            prop.load(in);   
            appkey = prop.getProperty("appKey").trim();  
            appSecret = prop.getProperty("appSecret").trim(); 
            tmcUrl = prop.getProperty("tmcUrl").trim(); 
            tmcGroupName = prop.getProperty("tmcGroupName").trim(); 
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
        dealTmcService = (DealTmcService) StaticContextAccessor.getBean("dealTmcService");
    }  

    public void onMessage(Message message, MessageStatus status) throws Exception {
        try {
            // 默认不抛出异常则认为消息处理成功  
            if(message.getContent()!=null){
                putOrGet(message.getId(), message,1);
            }
        } catch (Exception e) {  
            notifyNum++;
            logger.info("已处理"+notifyNum);
            logger.error("process message error.", e);
            status.fail();// 消息处理失败回滚，服务端需要重发  
        }
    }
    
    public static TmcClientDeal getTmcClientDeal(){
    	TmcClientDeal tmcClientDeal = new TmcClientDeal();
    	return tmcClientDeal;
    }
	public void run() {
    	this.before();
    	logger.info("正在执行中........"+appkey);
        TmcClient tmcClient = new TmcClient(appkey, appSecret, tmcGroupName);
        tmcClient.setMessageHandler(new TmcClientDeal());  
        tmcClient.setQueueSize(100000);
        try {
			tmcClient.connect(tmcUrl);
	        logger.info("tmc已连接.....");
		} catch (LinkException e1) {
			e1.printStackTrace();
		}
        int heartbeatMillis = 0;
        int idleMillis = 0;
        int sleepMillis = 500;
        while (true) {
            try {
				Thread.sleep(sleepMillis);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
            //System.out.print('.');
            heartbeatMillis += sleepMillis;
            if (heartbeatMillis >= 60000) {
                heartbeatMillis = 0;
                logger.info("心跳--嘟嘟嘟");
            }
            Collection<Message> collection = putOrGet(0, null, 2);
            if (collection == null || collection.size() == 0) {
                idleMillis += sleepMillis;
                if (idleMillis >= 60000 && idleMillis % 60000 == 0) {
                    logger.info("通知异常,已经有" + idleMillis/1000 + "秒没有通知");
                }
                continue;
            } else {
                idleMillis = 0;
            }
            try {
                long timeStart = System.currentTimeMillis();
                dealTmcService.insertTNObect(collection);
                long timeEnd = System.currentTimeMillis();
                logger.info("...总共花费时间（毫秒）："+(timeEnd-timeStart));
            } catch (Exception e) {
                logger.error("save message error.", e);
            }
        }
    }
    
    /**
     * 放入通知或取出通知 1 放入 2 取出
     */
    public static synchronized Collection<Message> putOrGet(long messageId, Message message, int type) {
        Collection<Message> list = null;
        if (type == 1) {
            notifyMap.put(messageId, message);
        } else if (type == 2) {
            list = notifyMap.values();
            notifyMap = new HashMap<Long, Message>();
            notifyNum = list.size() + notifyNum;
        }
        return list;
    }

}
