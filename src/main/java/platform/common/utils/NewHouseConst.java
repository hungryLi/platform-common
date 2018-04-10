package platform.common.utils;

public class NewHouseConst {
	public static class Receipt{ //收款
		public static final byte WAIT_TO_CONFIRM=1;//等待确认
		public static final byte ALREADY_TO_CONFIRM=2;//已确认
		public static final byte ALREADY_FAIL=3;//已经废弃
		
		public static final byte SHOULD_INCOME=0;//应收款
		public static final byte ZAIXIANG_INCOME=1;//杂项收款
	}
	
	public static class ShouldReceipt{ //应收款
		public static final byte NOT_AUDIT=0;//未审核
		public static final byte AUDITTING=1;//审核中
		public static final byte ALREADY_AUDIT=2;//已审核
		public static final byte AUDIT_NOT_PASS=3;//审核不通过
		public static final byte FAIL=4;//作废
		
		public static final byte DEBIT=0;//借项
		public static final byte CREDIT=1;//贷项
		
	}
	
	public static class Achievement{ //业绩确认单
		public static final byte ALREADY_SAVE=0;//已经保存
		public static final byte ALREADY_BUILD=1;//已经生成
		public static final byte AUDITTING=2;//审核中
		public static final byte ALREADY_AUDIT=3;//已审核
		public static final byte AUDIT_NOT_PASS=4;//审核不通过
		public static final byte FAIL=5;//作废
	}
}
