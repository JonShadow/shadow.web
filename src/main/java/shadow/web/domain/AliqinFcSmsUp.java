package shadow.web.domain;

import com.google.gson.annotations.SerializedName;

public class AliqinFcSmsUp {
	
	/**短信内容*/
	private String content;
	
	/**扩展码*/
	@SerializedName("dest_code")
	private String destCode;

	/**手机号码*/
	private String sender;
	
	@SerializedName("sender_time")
	private String senderTime;
	
	@SerializedName("sms_seq")
	private String smsSeq;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDestCode() {
		return destCode;
	}

	public void setDestCode(String destCode) {
		this.destCode = destCode;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getSenderTime() {
		return senderTime;
	}

	public void setSenderTime(String senderTime) {
		this.senderTime = senderTime;
	}

	public String getSmsSeq() {
		return smsSeq;
	}

	public void setSmsSeq(String smsSeq) {
		this.smsSeq = smsSeq;
	}
	
}
