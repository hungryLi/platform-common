package platform.common.data.webdto;

public enum ReceiveStatus {
	
	WAIT_CONFIRM("1", "待确认"), 
	CONFIRM_RECEIVE("2", "确认收款"), 
	REFUSE_RECEIVE("3", "拒绝收款"); 
	// 成员变量
	private String name;

	private String status;

	// 构造方法
	private ReceiveStatus(String status, String name) {
		this.name = name;
		this.status = status;
	}

	// 普通方法
	public static String getName(String status) {
		for (ReceiveStatus s : ReceiveStatus.values()) {
			if (s.getStatus() == status) {
				return s.name;
			}
		}
		return null;
	}

	// get set 方法
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
