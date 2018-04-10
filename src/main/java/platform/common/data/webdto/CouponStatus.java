package platform.common.data.webdto;

/**
 * @author zhoulipei
 * 优惠券状态
 */
public enum CouponStatus {
	
    NOTPAY("11", "未支付"), 
    PROFITING("00", "收益中"), 
    PROFITING_NO_ABANDON("01", "收益中不允许放弃"), // 开盘前一天后一天不允许放弃
    USED("02", "使用"),
    ANANDON("03", "放弃"),
    ANANDON_APPLY("04", "放弃申请中"),
    OVERDUE("05", "逾期"),
    ABANDON_FAIL("06", "放弃失败"); 
      // 成员变量  
      private String name; 
      
      private String status;  
      // 构造方法  
      private CouponStatus( String status,String name) {  
          this.name = name;  
          this.status = status;  
      }  
      // 普通方法  
      public static String getName(String status) {  
          for (CouponStatus s : CouponStatus.values()) {  
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
