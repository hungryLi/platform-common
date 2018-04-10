package platform.common.data.thirddto;

public class PayZFBInfo {
	
	//c端用户名
	private String userName;
	
	private String bankChannel;
	
	//支付宝账号名
	private String collectId;
	
	//支付宝账号
	private String collectName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBankChannel() {
		return bankChannel;
	}

	public void setBankChannel(String bankChannel) {
		this.bankChannel = bankChannel;
	}

	public String getCollectId() {
		return collectId;
	}

	public void setCollectId(String collectId) {
		this.collectId = collectId;
	}

	public String getCollectName() {
		return collectName;
	}

	public void setCollectName(String collectName) {
		this.collectName = collectName;
	}

}
