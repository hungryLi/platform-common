package platform.common.base.console.exception;

public enum ErrorCode {
	// ///////////////公共:10开头///////////////////////
	@Error(code = "0", msg = "")
	success_0, @Error(code = "1000", msg = "系统错误!")
	system_1000, @Error(code = "1001", msg = "不支持此服务！")
	system_1001, @Error(code = "1002", msg = "参数非法！")
	system_1002, @Error(code = "1003", msg = "帐期已经关闭！")
	system_1003,
	@Error(code = "00000", msg = "")
	success_00000,
	// //////////////公共 -end//////////////////////////////

	@Error(code = "2000", msg = "用户名不能为空!")
	credit_2000, @Error(code = "2001", msg = "密码不能为空!")
	credit_2001, @Error(code = "2002", msg = "验证码不能为空!")
	credit_2002, @Error(code = "2003", msg = "验证码错误!")
	credit_2003, @Error(code = "2004", msg = "用户名/密码错误!")
	credit_2004, @Error(code = "2005", msg = "姓名不能为空!")
	credit_2005, @Error(code = "2006", msg = "手机不能为空!")
	credit_2006, @Error(code = "2007", msg = "邮件不能为空!")
	credit_2007, @Error(code = "2008", msg = "邮件格式非法!")
	credit_2008, @Error(code = "2009", msg = "手机格式非法!")
	credit_2009, @Error(code = "2010", msg = "参数非法!")
	credit_2010, @Error(code = "2011", msg = "密码和重复密码不一致!")
	credit_2011, @Error(code = "2012", msg = "密码强度不够, 需要大写字母、小写字母和数字组成!")
	credit_2012, @Error(code = "2013", msg = "旧密码不正确!")
	credit_2013,
	// 角色管理
	@Error(code = "2020", msg = "角色名不能为空！")
	credit_2020, @Error(code = "2015", msg = "经纪人评价内容的长度1-100！")
	credit_2015, @Error(code = "2016", msg = "页码必须大于0")
	credit_2016, @Error(code = "2017", msg = "每页条数必须大于0")
	credit_2017, @Error(code = "2018", msg = "无效积分规则")
	credit_2018, @Error(code = "2019", msg = "城市ID必须大于0")
	credit_2019,
	// agent credit and score 模块新增 end

	/*---------------------------------------- AgentService ------------------------------------*/
	@Error(code = "3000", msg = "经纪专员ID不能为空!")
	broker_3000, @Error(code = "3100", msg = "经纪人动态参数 agentCommissionerJson.day 异常，正常范围：1~30!")
	agent_3100, @Error(code = "3101", msg = "经纪人动态详情参数 AgentDetailReqJson.day 异常，正常范围：0~30!")
	agent_3101, @Error(code = "3102", msg = "获取经纪人详情异常")
	agent_3102,

	@Error(code = "3001", msg = "开始日期不能为空!")
	broker_3001,

	@Error(code = "3002", msg = "结束日期不能为空!")
	broker_3002,

	/**
	 * 参数校验类错误
	 */
	@Error(code = "4001", msg = "错误的动态类型!")
	error_dynamic,

	@Error(code = "4002", msg = "无效的门店")
	error_store,
	
	@Error(code = "5001", msg = "只能上传两天前的数据!")
	fangyb_5001,
	@Error(code = "5002", msg = "重复上传的数据!")
	fangyb_5002,
		
	/*---------------------------------------- interface Error Info ------------------------------------*/
	@Error(code = "6001", msg = "与成交奖管理中心连接存在异常")
	bmc_block_6001,
	@Error(code = "6002", msg = "与支付中心连接存在异常")
	pay_block_6002,
	@Error(code = "6003", msg = "提现金额与支付金额不一致")
	pay_amount_error_6003,
	@Error(code = "6004", msg = "找不到默认的城市支付方式")
	pay_default_error_6004,
	@Error(code = "6005", msg = "找不到对应的账户信息")
	bank_account_error_6005,
	@Error(code = "6006", msg = "找不到对应的机构代码信息")
	pay_spid_error_6006,

	@Error(code = "0", msg = "")
	success_end,
	/*---------------------------------------- DownLoadQGJReconFileTask ------------------------------------*/
	/**--下载或解析文件失败errCode--*/
	@Error(code = "4000", msg = "读取参数配置文件异常")
	money_keeper_4000,
	@Error(code = "4001", msg = "获取下载文件名异常")
	money_keeper_4001,
	@Error(code = "4002", msg = "解析对账文件异常")
	money_keeper_4002,
	/**--钱管家服务器链接不上或文件不存在errCode--*/
	@Error(code = "4010", msg = "")
	money_keeper_4010,
	
	/**--新房收款errCode--*/
	@Error(code = "7000", msg = "付款方名称为空")
	receipt_7000,
	@Error(code = "7001", msg = "城市为空")
	receipt_7001,
	@Error(code = "7002", msg = "楼盘为空")
	receipt_7002,
	@Error(code = "7003", msg = "收款日期为空")
	receipt_7003,
	@Error(code = "7004", msg = "会计期间为空")
	receipt_7004,
	@Error(code = "7005", msg = "收款类别为空")
	receipt_7005,
	@Error(code = "7006", msg = "收款凭证号为空")
	receipt_7006,
	@Error(code = "7007", msg = "收款方名称为空")
	receipt_7007,
	@Error(code = "7008", msg = "收款方式为空")
	receipt_7008,
	@Error(code = "7009", msg = "收款金额为空")
	receipt_7009,
	@Error(code = "7010", msg = "当前会计期间已关闭")
	receipt_7010,
	/**--二手房收款errCode--*/
	@Error(code = "7011", msg = "确认收款")
	receipt_7011,
	@Error(code = "7012", msg = "实收金额小于应收金额，请点击拒绝按钮，拒绝收款")
	receipt_7012,
	/**--银行账号errCode--*/
	@Error(code = "8000", msg = "城市ID为空")
	bankAccount_8000,
	@Error(code = "8001", msg = "账户户名为空")
	bankAccount_8001,
	@Error(code = "8002", msg = "银行简称为空")
	bankAccount_8002,
	@Error(code = "8003", msg = "银行名称为空")
	bankAccount_8003,
	@Error(code = "8004", msg = "银行账号为空")
	bankAccount_8004,
	@Error(code = "8005", msg = "城市名称为空")
	bankAccount_8005,
	/**--应收errCode--*/
	@Error(code = "9000", msg = "会计期间为空")
	should_receipt_9000,
	@Error(code = "9001", msg = "城市为空")
	should_receipt_9001,
	@Error(code = "9002", msg = "项目名称为空")
	should_receipt_9002,
	@Error(code = "9003", msg = "业务类型为空")
	should_receipt_9003,
	@Error(code = "9004", msg = "应收类型为空")
	should_receipt_9004,
	@Error(code = "9005", msg = "应收金额为空")
	should_receipt_9005,
	@Error(code = "9006", msg = "单据正在审核中")
	should_receipt_9006,
	@Error(code = "9007", msg = "单据已经审核")
	should_receipt_9007,
	@Error(code = "9008", msg = "只有提交人才能作废")
	should_receipt_9008,
	@Error(code = "10000", msg = "无数据,不需要导出")
	report_excel_10000,
	@Error(code = "10001", msg = "数据条数大于20000条，请分批导出")
	report_excel_10001,
	@Error(code = "10002", msg = "业务类型为借项，应收金额只能是正数")
	should_receipt_10002,
	@Error(code = "10003", msg = "业务类型为贷项，应收金额只能是负数")
	should_receipt_10003,
	@Error(code = "10004", msg = "只有新房数据专员才能提交任务")
	should_receipt_10004,
	@Error(code = "10005", msg = "只有新房收入会计才能审核任务")
	should_receipt_10005,
	/**--业绩确认单errCode--*/
	@Error(code = "10006", msg = "城市为空")
	achievement_10006,
	@Error(code = "10007", msg = "楼盘为空")
	achievement_10007,
	@Error(code = "10008", msg = "认购起始时间为空")
	achievement_10008,
	@Error(code = "10009", msg = "认购结束时间为空")
	achievement_10009,
	@Error(code = "100010", msg = "日期格式不正确")
	achievement_100010,
	@Error(code = "100011", msg = "找不到该订单")
	achievement_100011,
	@Error(code = "100012", msg = "审核中的单据不能作废")
	achievement_100012,
	@Error(code = "100013", msg = "已审批的单据不能作废")
	achievement_100013,
	@Error(code = "100014", msg = "已作废的单据不能作废")
	achievement_100014,
	@Error(code = "100015", msg = "单据还没有生成，不能作废")
	achievement_100015,
	/**--订单内佣计提errCode--*/
	@Error(code = "100016", msg = "城市为空")
	commission_cal_100016,
	@Error(code = "100017", msg = "楼盘为空")
	commission_cal_100017,
	@Error(code = "100018", msg = "计佣月份格式不正确或者为空")
	commission_cal_100018,
	@Error(code = "100019", msg = "部门为空")
	commission_cal_100019,
	@Error(code = "100020", msg = "姓名为空")
	commission_cal_100020,
	@Error(code = "100021", msg = "计提比例为空或者不能输入大于1的数")
	commission_cal_100021,
	@Error(code = "100022", msg = "退款扣除比例为空或者不能输入大于1的数")
	commission_cal_100022,
	@Error(code = "100023", msg = "内佣计提人员不能为空")
	commission_cal_100023,
	@Error(code = "100024", msg = "退款金额不能为空")
	commission_cal_100024,
	@Error(code = "100025", msg = "退款扣除不能为空")
	commission_cal_100025,
	@Error(code = "100026", msg = "计提内拥不能为空")
	commission_cal_100026,
	@Error(code = "100027", msg = "作废后不能审核通过")
	commission_cal_100027,
	@Error(code = "100028", msg = "审核通过后不能作废")
	commission_cal_100028,
	@Error(code = "100029", msg = "请求参数格式不正确")
	commission_cal_100029,
	//线上请佣需求相关错误码
	@Error(code = "100030", msg = "提现单已经存在")
	withdraw_exist_100030,
	@Error(code = "100031", msg = "提现单不存在")
	withdraw_not_exist_100031,
	@Error(code = "100032", msg = "请佣单已经存在")
	commission_apply_exist_100032,
	@Error(code = "100033", msg = "请佣单不存在")
	commission_apply_not_exist_100033,
	@Error(code = "100034", msg = "返回的响应为空")
	null_response_100034,
	@Error(code = "100035", msg = "该提现申请单不处于审核拒绝状态，不允许进行修改")
	forbidden_modify_withdraw,
	@Error(code = "100036", msg = "该请佣申请单不处于审核拒绝状态，不允许进行修改")
	forbidden_modify_commission,
	@Error(code = "100037", msg = "付款之后通知房佣宝失败")
	nofity_fangyongbao_error,
	@Error(code = "100038", msg = "请佣单已撤回或者已失效")
	apply_invalid_or_roll_back;
	
	/**
	 * 返回错误码
	 */
	public Error getError() {
		Error error;
		try {
			error = this.getClass().getField(this.name())
					.getAnnotation(Error.class);
		} catch (Exception e) {
			return null;
		}
		return error;
	}

}