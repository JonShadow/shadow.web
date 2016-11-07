package shadow.web.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.tmc.Message;
import com.taobao.api.internal.tmc.MessageHandler;
import com.taobao.api.internal.tmc.MessageStatus;
import com.taobao.api.internal.tmc.TmcClient;
import com.taobao.api.internal.toplink.LinkException;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;

public class AlidayuApi extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4341351041276471736L;
	private static String serverUrl = "http://gw.api.taobao.com/router/rest";
	private static String appKey ;
	private static String appSecret ;
	private static String groupName ;
	
	static{
		 Properties prop = new Properties();   
	        InputStream in = Object.class.getResourceAsStream("/config.properties");   
	        try {   
	            prop.load(in);   
	            //serverUrl = prop.getProperty("serverUrl").trim();   
	            appKey = prop.getProperty("appKey").trim();  
	            appSecret = prop.getProperty("appSecret").trim(); 
	            groupName = prop.getProperty("tmcGroupName").trim();
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        }   
	}

	public void init(){
	        TmcClient client = new TmcClient(appKey, appSecret, groupName); // 关于default参考消息分组说明
	        client.setMessageHandler(new MessageHandler() {
	            public void onMessage(Message message, MessageStatus status) {
	                try {
	                    System.out.println(message.getContent());
	                    System.out.println(message.getTopic());
	                } catch (Exception e) {
	                    e.printStackTrace();
	                    status.fail(); // 消息处理失败回滚，服务端需要重发
	                  // 重试注意：不是所有的异常都需要系统重试。 
	                  // 对于字段不全、主键冲突问题，导致写DB异常，不可重试，否则消息会一直重发
	                  // 对于，由于网络问题，权限问题导致的失败，可重试。
	                  // 重试时间 5分钟不等，不要滥用，否则会引起雪崩
	                }
	            }
	        });
	        try {
				client.connect("ws://mc.api.taobao.com");
				System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			} catch (LinkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
	}
	
	public static AlidayuApi getAlidayuApi(){
		AlidayuApi alidayuApi = new AlidayuApi();
		return alidayuApi;
	}
	public static void main(String[] args) throws ApiException {
		getAlidayuApi().init();
		/*TaobaoClient client = new DefaultTaobaoClient(serverUrl, appKey, appSecret);
		AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
		req.setExtend("123456");
		req.setSmsType("normal");
		req.setSmsFreeSignName("阿里大于");
		req.setSmsParamString("{\"code\":\"1234\",\"product\":\"alidayu\"}");
		req.setRecNum("13000000000");
		req.setSmsTemplateCode("SMS_585014");
		AlibabaAliqinFcSmsNumSendResponse rsp = client.execute(req);
		System.out.println(rsp.getBody());*/
	}
	
	/* public void init() throws ServletException {  
	        super.init();  
	        try {   
	        Properties pro = new Properties();
	        InputStream in = getClass().getResourceAsStream("/config.properties");
	        System.out.println(in);
	        pro.load(in);
	        serverUrl = pro.getProperty("serverUrl").trim();   
            appKey = pro.getProperty("appKey").trim();  
            appSecret = pro.getProperty("appSecret").trim(); 
            System.out.println(serverUrl+appKey+appSecret);
	        in.close();
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        }
	        
	    } 
	 */
	/* public void init(ServletConfig cfg) throws ServletException {  
	        super.init(cfg);  
	          
	        Properties prop = new Properties();   
	        InputStream in = Object.class.getResourceAsStream("/config.properties");  
	        System.out.println(in);
	        try {   
	            prop.load(in);   
	            serverUrl = prop.getProperty("serverUrl").trim();   
	            appKey = prop.getProperty("appKey").trim();  
	            appSecret = prop.getProperty("appSecret").trim(); 
	            System.out.println(serverUrl+appKey+appSecret);
	        } catch (IOException e) {   
	            e.printStackTrace();   
	        } 
	    } */ 
}
