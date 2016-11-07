package shadow.web.domain;

import com.google.gson.annotations.SerializedName;

public class AliqinFcCallCdr {
	
	/**流水号*/
	@SerializedName("biz_id")
	private String bizId;
	
	/**DTMF*/
	private String dtmf;

	/**通话时长，未接通为0*/
	private String duration;
	
	/**通话结束时间，未接通则为空*/
	@SerializedName("end_time")
	private String endTime;
	
	/**扩展字段回传，将调用api时传入的字段返回*/
	private String extend;
	
	/**通话开始时间，未接通则为空*/
	@SerializedName("start_time")
	private String startTime;
	
	/**呼叫结果状态码*/
	@SerializedName("status_code")
	private String statusCode;
	
	/**	结果描述*/
	@SerializedName("status_msg")
	private String statusMsg;

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getDtmf() {
		return dtmf;
	}

	public void setDtmf(String dtmf) {
		this.dtmf = dtmf;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusMsg() {
		return statusMsg;
	}

	public void setStatusMsg(String statusMsg) {
		this.statusMsg = statusMsg;
	}
	
}
