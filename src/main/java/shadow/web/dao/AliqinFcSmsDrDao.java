package shadow.web.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import shadow.web.domain.AliqinFcSmsDR;



@Component
public interface AliqinFcSmsDrDao {

	void insertTNObect(@Param("aliqinFcSmsDR")AliqinFcSmsDR aliqinFcSmsDR);

}
