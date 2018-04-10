package platform.common.data.thirddto;

public class WithdrawInfo {
	
	private String withdrawId;
	
	private String userId;
	
	private String accountType;
	
	private String userName;
	
	private String spId;
	
	private String amount;
	
	private String memo;
	
	private String payChannel;
	
	private String payerAccName;
	
	private String payerAccount;
	
	private String payerBranchBank;

	public String getWithdrawId() {
		return withdrawId;
	}

	public void setWithdrawId(String withdrawId) {
		this.withdrawId = withdrawId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getPayerAccount() {
		return payerAccount;
	}

	public void setPayerAccount(String payerAccount) {
		this.payerAccount = payerAccount;
	}

	public String getPayerBranchBank() {
		return payerBranchBank;
	}

	public void setPayerBranchBank(String payerBranchBank) {
		this.payerBranchBank = payerBranchBank;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getPayerAccName() {
		return payerAccName;
	}

	public void setPayerAccName(String payerAccName) {
		this.payerAccName = payerAccName;
	}

}
