package shadow.web.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shadow.web.dao.SendRecordDao;
import shadow.web.domain.SendRecord;

@Service
public class PageShowService {
	private static final Log logger = LogFactory.getLog(PageShowService.class);
	
	@Autowired
	SendRecordDao sendRecordDao;

	public List<SendRecord> getRecordList(String bizId) {
		logger.info("....");
		
		return sendRecordDao.getRecordList(bizId);
	}

}
