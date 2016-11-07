package shadow.web.domain;

public class PageInfo {

	private int type;
	private String time;
	private String accountPrice;
	private String UV;
	private String tryCount;
	private String payCount;
	private String price;
	private String[] everyDayTryNicks;
	private String[] everyDayPayNicks;
	
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getAccountPrice() {
		return accountPrice;
	}
	public void setAccountPrice(String accountPrice) {
		this.accountPrice = accountPrice;
	}
	public String getUV() {
		return UV;
	}
	public void setUV(String uV) {
		UV = uV;
	}
	public String getTryCount() {
		return tryCount;
	}
	public void setTryCount(String tryCount) {
		this.tryCount = tryCount;
	}
	public String getPayCount() {
		return payCount;
	}
	public void setPayCount(String payCount) {
		this.payCount = payCount;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
    public String[] getEveryDayTryNicks() {
        return everyDayTryNicks;
    }
    public void setEveryDayTryNicks(String[] everyDayTryNicks) {
        this.everyDayTryNicks = everyDayTryNicks;
    }
    public String[] getEveryDayPayNicks() {
        return everyDayPayNicks;
    }
    public void setEveryDayPayNicks(String[] everyDayPayNicks) {
        this.everyDayPayNicks = everyDayPayNicks;
    }
	
}
