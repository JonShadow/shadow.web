package shadow.web.dao;

import org.apache.ibatis.annotations.Param;

import shadow.web.domain.AliqinFcSmsUp;

public interface AliqinFcSmsUpDao {
	
	void insertTNObect(@Param("aliqinFcSmsUp")AliqinFcSmsUp aliqinFcSmsUp);

}
