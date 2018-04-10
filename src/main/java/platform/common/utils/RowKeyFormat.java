package platform.common.utils;


import java.util.Date;


/***
 * hbase rowkey 格式工具
 * 
 * @author : muck
 * @date : 2015年11月5日 下午2:04:44
 * @version : 1.0
 */
public class RowKeyFormat {
  public static String formatUuid( String uuid ) {
    return uuid;
  }
  public static String formatAppVersionCode( String appVersionCode ) {
    return appVersionCode;
  }
  public static String formatAppCode( String appCode ) {
    return lpad(2, Integer.valueOf(appCode));
  }
  public static String formatChannelId( String channelId ) {
    return lpad(3, Integer.valueOf(channelId));
  }
  public static String formatDate( Date date ) {
    return TimeUtil.convertFormat(date, "yyyyMMddHHmm");
  }
  public static String formatDateWithSec( Date date ) {
    return TimeUtil.convertFormat(date, "yyyyMMddHHmmss");
  }
  public static String formatEventCode( String eventCode ) {
    return lpad(3, Integer.valueOf(eventCode));
  }
  /**
   * 补齐不足长度
   * 
   * @param length
   *          长度
   * @param number
   *          数字
   * @return
   */
  private static String lpad( int length, int number ) {
    String f = "%0" + length + "d";
    return String.format(f, number);
  }

  public static void main( String[] args ) {
    System.out.println(new Date().getTime());
    System.err.println(formatDate(new Date()));
  }
}
