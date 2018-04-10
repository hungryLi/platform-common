package platform.common.data.thirddto;

public class PayBalanceInfo {
	
	//总余额，但不包括红包
	private String balance;
	
	//冻结款
	private String freeze;
	
	//冻结红包
	private String freezeGift;
	
	//可用红包，不包括冻结红包
	private String gift;
	
	//可用余额，用于显示在个人钱包
	private String useful;

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getFreeze() {
		return freeze;
	}

	public void setFreeze(String freeze) {
		this.freeze = freeze;
	}

	public String getFreezeGift() {
		return freezeGift;
	}

	public void setFreezeGift(String freezeGift) {
		this.freezeGift = freezeGift;
	}

	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	public String getUseful() {
		return useful;
	}

	public void setUseful(String useful) {
		this.useful = useful;
	}

}
