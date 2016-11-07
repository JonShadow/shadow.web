package shadow.web.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import shadow.web.domain.SendRecord;

public interface SendRecordDao {

	List<SendRecord> getRecordList(@Param("bizId")String bizId);

}
