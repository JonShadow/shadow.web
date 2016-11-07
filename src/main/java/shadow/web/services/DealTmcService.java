package shadow.web.services;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.taobao.api.internal.tmc.Message;

import shadow.web.dao.AliqinFcCallCdrDao;
import shadow.web.dao.AliqinFcSmsDrDao;
import shadow.web.dao.AliqinFcSmsUpDao;
import shadow.web.domain.AliqinFcCallCdr;
import shadow.web.domain.AliqinFcSmsDR;
import shadow.web.domain.AliqinFcSmsUp;

@Service("dealTmcService")
public class DealTmcService {
	private static final Log logger = LogFactory.getLog(DealTmcService.class);
	
	@Autowired
	AliqinFcSmsDrDao aliqinFcSmsDrDao;
	@Autowired
	AliqinFcCallCdrDao aliqinFcCallCdrDao;
	@Autowired
	AliqinFcSmsUpDao aliqinFcSmsUpDao;

	public boolean insertTNObect(Collection<Message> list) {
		if(list == null || list.size() == 0){
            return true;
        }
        for (Message message:list) {
        	logger.info("topic:"+message.getTopic()+"\t"+"内容:"+message.getContent());
        	Gson gson = new Gson();
            //没有定义处理的通知不进行处理
            /*if(topicToIntValue(message.getTopic())<0){
                continue;
            }*/
            if(message.getTopic().contains("FcSmsDR")){
            	AliqinFcSmsDR aliqinFcSmsDR = gson.fromJson(message.getContent(), AliqinFcSmsDR.class);
            	aliqinFcSmsDrDao.insertTNObect(aliqinFcSmsDR);
            }else if(message.getTopic().contains("FcCallCdr")){
            	AliqinFcCallCdr aliqinFcCallCdr = gson.fromJson(message.getContent(), AliqinFcCallCdr.class);
            	aliqinFcCallCdrDao.insertTNObect(aliqinFcCallCdr);
            }else if(message.getTopic().contains("FcSmsUp")){
            	AliqinFcSmsUp aliqinFcCallCdr = gson.fromJson(message.getContent(), AliqinFcSmsUp.class);
            	aliqinFcSmsUpDao.insertTNObect(aliqinFcCallCdr);
            }else{
                logger.warn("未知类型");
                return false;
            }
        }
		return true;
	}

}
