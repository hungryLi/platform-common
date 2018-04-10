package platform.common.data.webdto;

/**
 * @author zhoulipei 解冻审批状态
 * 
 */
public enum UnfreezeStatus {
	FOR_AUDIT("00", "用户申请放弃中"), 
	THROUGH("01", "项目经理审批通过"), 
	REFUSE("02", "项目经理审批不通过"), 
	WAIT_FINANCE("03", "财务审批中"), 
	FINANCE_AGREE("04", "财务审批通过"), 
	FINANCE_REFUSE("05", "财务审批不通过"), 
	SUCCESS("06", "放弃成功"), 
	CANCEL_BY_USER("07", "用户已取消放弃");
	// 成员变量
	private String name;

	private String status;

	// 构造方法
	private UnfreezeStatus(String status, String name) {
		this.name = name;
		this.status = status;
	}

	// 普通方法
	public static String getName(String status) {
		for (UnfreezeStatus s : UnfreezeStatus.values()) {
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
