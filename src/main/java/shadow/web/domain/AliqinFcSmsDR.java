package shadow.web.domain;

import com.google.gson.annotations.SerializedName;

public class AliqinFcSmsDR {
	
	/**流水号*/
	@SerializedName("biz_id")
	private String bizId;
	
	/**返回原因code*/
	@SerializedName("err_code")
	private String errCode;

	/**扩展字段回传，调用api时传入，消息通知原样返回*/
	private String extend;
	
	/**短信接收号码*/
	private String receiver;
	
	/**报告时间*/
	@SerializedName("rept_time")
	private String reptTime;
	
	/**发送时间*/
	@SerializedName("send_time")
	private String sendTime;
	
	/**状态 1：成功，2：失败*/
	private String state;

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}

	public String getReptTime() {
		return reptTime;
	}

	public void setReptTime(String reptTime) {
		this.reptTime = reptTime;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
