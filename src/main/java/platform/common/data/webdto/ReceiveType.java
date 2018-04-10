package platform.common.data.webdto;

public enum ReceiveType {
	
	WAIT_CONFIRM(1, "POS机刷卡"), 
	CONFIRM_RECEIVE(2, "支付宝转账"), 
	REFUSE_RECEIVE(3, "其他"); 
	// 成员变量
	private String receiveTypeName;

	private Integer receiveType;

	// 构造方法
	private ReceiveType(Integer receiveType, String receiveTypeName) {
		this.receiveTypeName = receiveTypeName;
		this.receiveType = receiveType;
	}

	// 普通方法
	public static String getreceiveTypeName(Integer receiveType) {
		for (ReceiveType s : ReceiveType.values()) {
			if (s.getreceiveType() == receiveType) {
				return s.receiveTypeName;
			}
		}
		return null;
	}

	// get set 方法
	public String getreceiveTypeName() {
		return receiveTypeName;
	}

	public void setreceiveTypeName(String receiveTypeName) {
		this.receiveTypeName = receiveTypeName;
	}

	public Integer getreceiveType() {
		return receiveType;
	}

	public void setreceiveType(Integer receiveType) {
		this.receiveType = receiveType;
	}
}
