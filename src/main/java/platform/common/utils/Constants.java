package platform.common.utils;

public interface Constants {
	
	/**
	 * 备用金标识
	 * 1：备用金付款 2：银企直联付款 
	 */
	public final static int SECOND_SPARE_TYPE_YES = 1;
	public final static int SECOND_SPARE_TYPE_NO = 2;
	
	/**
	 * 是否退款标识
	 * 1：退款   2：正常付款
	 */
	public final static int SECOND_REFUND_TYPE_YES = 1;
	public final static int SECOND_REFUND_TYPE_NO = 2;
	
	/**
	 * 收款申请单的收款状态
	 * 1:待确认 2:确认收款 3:拒绝收款
	 */
	public final static int SECOND_RECEIVE_STATUS_UNCONFIRMED = 1;
	public final static int SECOND_RECEIVE_STATUS_CONFIRM = 2;
	public final static int SECOND_RECEIVE_STATUS_REFUSE = 3;
	
	/**
	 * 收付款申请单附件类别
	 * 1：收款 2：付款
	 */
	public final static int SECOND_ATTACHMENT_TYPE_RECEIVE = 1;
	public final static int SECOND_ATTACHMENT_TYPE_PAYMENT = 2;
	
	/**
	 * 付款申请单的单据状态
	 * 1:审批中 2:审批通过 3:审批拒绝 4:付款成功 5:付款失败 6:确认付款
	 */
	public final static int SECOND_PAYMENT_STATUS_UNCONFIRMED = 1;
	public final static int SECOND_PAYMENT_STATUS_APPROVED = 2;
	public final static int SECOND_PAYMENT_STATUS_REFUSE = 3;
	public final static int SECOND_PAYMENT_STATUS_SUCCESS = 4;
	public final static int SECOND_PAYMENT_STATUS_FAILURE = 5;
	public final static int SECOND_PAYMENT_STATUS_CONFIRM = 6;
	
	/**
	 * 收付款确认状态是否重发到业务系统
	 * 1：重发，2：不重发
	 */
	public final static int SECOND_ISRESEND_STATUS_YES = 1;
	public final static int SECOND_ISRESEND_STATUS_NO = 2;

	/**
	 * 代理商的代理类型
	 * 1  表示线上现金代理，
	 * 2 线下现金代理，后台注册代理商为该类型
	 */
	public final static int ONLINE_AGENT_TYPE = 1;
	public final static int OFFLINE_AGENT_TYPE = 2;

	/**
	 * '状态\n1:新建\n2:正常\n3:失效\n',
	 */
	public final static int STATUS_NEW = 1;
	public final static int STATUS_NORMOL = 2;
	public final static int STATUS_DISABLE = 3;
	
	
	public final static String OPERATOR_SUCCESS="0";
	
}
