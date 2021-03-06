package platform.common.utils;


import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;


/**
 * 日期处理帮助类<BR>
 * <BR>
 * 
 * @JDK 1.5.0<BR>
 * @author long.amu@gmail.com<BR>
 * @Time 2007-11-23<BR>
 * @version 1.0<BR>
 */
public class DateUtil implements Serializable {

  /**
   * serialVersionUID
   */
  private static final long              serialVersionUID       = 1238226379012286690L;
  /**
   * AM/PM
   */
  public static final String             AM_PM                  = "a";
  /**
   * 一个月里第几天
   */
  public static final String             DAY_IN_MONTH           = "dd";
  /**
   * 一年里第几天
   */
  public static final String             DAY_IN_YEAR            = "DD";
  /**
   * 一周里第几天(从Sunday开始)
   */
  public static final String             DAY_OF_WEEK            = "EEEE";
  /**
   * 以天为单位
   */
  public static final int                DIFF_DAY               = Calendar.DAY_OF_MONTH;
  /**
   * 以小时为单位
   */
  public static final int                DIFF_HOUR              = Calendar.HOUR_OF_DAY;
  /**
   * 以毫秒为单位
   */
  public static final int                DIFF_MILLSECOND        = Calendar.MILLISECOND;
  /**
   * 以分钟为单位
   */
  public static final int                DIFF_MINUTE            = Calendar.MINUTE;
  /**
   * 以月份为单位，按照每月30天计算
   */
  public static final int                DIFF_MONTH             = Calendar.MONTH;
  /**
   * 以秒为单位
   */
  public static final int                DIFF_SECOND            = Calendar.SECOND;
  /**
   * 以星期为单位，按照每星期7天计算
   */
  public static final int                DIFF_WEEK              = Calendar.WEEK_OF_MONTH;
  /**
   * 以年为单位，按照每年365天计算
   */
  public static final int                DIFF_YEAR              = Calendar.YEAR;
  /**
   * 半天内小时(0-11)
   */
  public static final String             HOUR_IN_APM            = "KK";
  /**
   * 一天内小时(0-23)
   */
  public static final String             HOUR_IN_DAY            = "HH";
  /**
   * 半天内小时(1-12)
   */
  public static final String             HOUR_OF_APM            = "hh";
  /**
   * 一天内小时(1-24)
   */
  public static final String             HOUR_OF_DAY            = "kk";

  /**
   * 年(四位)
   */
  public static final String             LONG_YEAR              = "yyyy";
  /**
   * 毫秒
   */
  public static final String             MILL_SECOND            = "SSS";
  /**
   * 分钟
   */
  public static final String             MINUTE                 = "mm";
  /**
   * 月
   */
  public static final String             MONTH                  = "MM";
  /**
   * 秒
   */
  public static final String             SECOND                 = "ss";
  /**
   * 年(二位)
   */
  public static final String             SHORT_YEAR             = "yy";
  /**
   * 一个月里第几周
   */
  public static final String             WEEK_IN_MONTH          = "W";
  /**
   * 一年里第几周
   */
  public static final String             WEEK_IN_YEAR           = "ww";

  /** 日期格式(yyyy-MM-dd HH:mm:ss) */
  public static final String             yyyy_MM_dd_HH_mm_ss_EN = "yyyy-MM-dd HH:mm:ss";

  /** DateFormat缓存 */
  private static Map<String, DateFormat> dateFormatMap          = new HashMap<String, DateFormat>();

  /**
   * 检查目的时间是否已超过源时间值加上时间段长度
   * <p>
   * 用于判别当前是否已经超时
   * 
   * @param destDate
   *          目的时间，一般为当前时间
   * @param sourceDate
   *          源时间，一般为事件产生时间
   * @param type
   *          时间计算单位，为分钟、小时等
   * @param elapse
   *          持续时间长度
   * @return 是否超时
   * @throws CodedException
   */
  public static boolean compareElapsedTime( Date destDate, Date sourceDate, int type, int elapse )
      throws RuntimeException {
    if(destDate == null || sourceDate == null) throw new RuntimeException("compared date invalid");

    return destDate.getTime() > getRelativeDate(sourceDate, type, elapse).getTime();
  }

  /**
   * 取当前时间字符串
   * <p>
   * 时间字符串格式为：年(4位)-月份(2位)-日期(2位) 小时(2位):分钟(2位):秒(2位)
   * 
   * @return 时间字符串
   */
  public static String getCurrentDateString() {
    return getCurrentDateString("yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 按格式取当前时间字符串
   * <p>
   * 
   * @param formatString
   *          格式字符串
   * @return
   */
  public static String getCurrentDateString( String formatString ) {
    Date currentDate = new Date();

    return getDateString(currentDate, formatString);
  }

  /**
   * 取当天在一周的第几天
   * <p>
   * 
   * @return int CurrentDayOfWeek
   */
  public static int getCurrentDayOfWeek() {
    return getDayOfWeek(new Date());
  }

  public static Date getDate( Date date ) {
    return getDateFromString(getDateString(date, "yyyy-MM-dd"), "yyyy-MM-dd");
  }

  /**
   * 根据时间字符串生成时间
   * 
   * @param dateString
   *          时间字符串格式
   * @return 时间
   * @throws RuntimeException
   */
  public static Date getDateFromString( String dateString ) throws RuntimeException {
    return getDateFromString(dateString, "yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 根据字符串生成时间
   * 
   * @param dateString
   *          时间字符串
   * @param pattern
   *          时间字符串格式定义
   * @return 时间
   * @throws RuntimeException
   */
  public static Date getDateFromString( String dateString, String pattern ) throws RuntimeException {
    SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
    Date date = null;
    try {
      date = dateFormat.parse(dateString);
    }
    catch(java.text.ParseException e) {
      throw new RuntimeException(
        "parse date string '" + dateString + "' with pattern '" + pattern + "' failed: " + e.getMessage());
    }

    return date;
  }

  /**
   * 取时间字符串 "yyyy-MM-dd HH:mm:ss"
   * 
   * @param date
   *          时间
   * @return 时间字符串
   */
  public static String getDateString( Date date ) {
    return getDateString(date, "yyyy-MM-dd HH:mm:ss");
  }

  /**
   * 取时间字符串
   * 
   * @param date
   *          时间
   * @return 时间字符串
   */
  public static String getDateStringYMD( Date date ) {
    return getDateString(date, "yyyy-MM-dd");
  }

  /**
   * 取时间字符串
   * 
   * @param date
   *          时间
   * @param formatString
   *          转换格式
   * @return 时间字符串
   */
  public static String getDateString( Date date, String formatString ) {
    return getDateString(date, formatString, Locale.PRC);
  }

  /**
   * 取时间字符串
   * 
   * @param date
   *          时间
   * @param formatString
   *          转换格式
   * @param locale
   *          地区
   * @return 时间字符串
   */
  public static String getDateString( Date date, String formatString, Locale locale ) {
    if(date == null) return null;

    SimpleDateFormat dateFormat = new SimpleDateFormat(formatString, locale);

    return dateFormat.format(date);
  }

  /**
   * 取日期在一周的第几天
   * 
   * @param date
   *          日期
   * @return
   */
  public static int getDayOfWeek( Date date ) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    return calendar.get(Calendar.DAY_OF_WEEK);
  }

  /**
   * 取日期在一月的第几天
   * 
   * @param date
   *          日期
   * @return
   */
  public static int getDayOfMonth( Date date ) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    return calendar.get(Calendar.DAY_OF_MONTH);
  }

  /**
   * 取一个月的最大天数
   * 
   * @param date
   *          日期
   * @return
   */
  public static int getDaysOfMonth( Date date ) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  /**
   * 取日期所在月份的最大天数
   * 
   * @param date
   *          日期
   * @return
   */
  public static int getMaximumDay( Date date ) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);

    return calendar.getMaximum(Calendar.DAY_OF_MONTH);
  }

  /**
   * 根据源时间和时长计算目的时间
   * 
   * @param date
   *          源时间
   * @param type
   *          时间单位
   * @param relate
   *          时长
   * @return 目的时间
   */
  public static Date getRelativeDate( Date date, int type, int relate ) {
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(type, relate);

    return calendar.getTime();
  }

  /**
   * 根据当前时间和时长计算目的时间
   * 
   * @param type
   *          时间单位
   * @param relate
   *          时长
   * @return 目的时间
   */
  public static Date getRelativeDate( int type, int relate ) {
    Date current = new Date();

    return getRelativeDate(current, type, relate);
  }

  /**
   * 根据当前时间和时长生成目的时间字符串
   * 
   * @param type
   *          时间单位
   * @param relate
   *          时长
   * @param formatString
   *          时间格式
   * @return 时间字符串
   */
  public static String getRelativeDateString( int type, int relate, String formatString ) {
    return getDateString(getRelativeDate(type, relate), formatString);
  }

  /**
   * 取时间戳字符串
   * 
   * @param date
   *          时间
   * @return 时间戳字符串
   */
  public static String getTimestampString( Date date ) {
    return getDateString(date, "yyyyMMddHHmmssSSS");
  }

  /**
   * 取当天日期值
   * 
   * @return 日期的整数值
   */
  public static int getToday() {
    return Integer.parseInt(getCurrentDateString("dd"));
  }

  public static long getTimeDiff( Date fromDate, Date toDate, int type ) {
    fromDate = ( fromDate == null ) ? new Date() : fromDate;
    toDate = ( toDate == null ) ? new Date() : toDate;
    long diff = toDate.getTime() - fromDate.getTime();

    switch(type) {
      case DIFF_MILLSECOND:
        break;

      case DIFF_SECOND:
        diff /= 1000;
        break;

      case DIFF_MINUTE:
        diff /= 1000 * 60;
        break;

      case DIFF_HOUR:
        diff /= 1000 * 60 * 60;
        break;

      case DIFF_DAY:
        diff /= 1000 * 60 * 60 * 24;
        break;

      case DIFF_MONTH:
        diff /= 1000 * 60 * 60 * 24 * 30;
        break;

      case DIFF_YEAR:
        diff /= 1000 * 60 * 60 * 24 * 365;
        break;

      default:
        diff = 0;
        break;
    }

    return diff;
  }

  /**
   * 比较时间戳是否相同
   * 
   * @param arg0
   *          时间
   * @param arg1
   *          时间
   * @return 是否相同
   */
  public static boolean isTimestampEqual( Date arg0, Date arg1 ) {
    return getTimestampString(arg0).compareTo(getTimestampString(arg1)) == 0;
  }

  /**
   * 把时间转换为传入的时间格式 formate
   * 
   * @param formate
   * @param date
   * @return
   */
  public static Date getDateByStringFormat( String format, Date date ) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);

    String dtaeStr = getDateString(date, format, Locale.CHINESE);

    // String str = sdf.format(date);
    // System.out.println(dtaeStr);
    // Date d =
    try {
      return sdf.parse(dtaeStr);
    }
    catch(ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
      return null;
    }

  }

  /**
   * 将Date转换成字符串“yyyy-mm-dd hh:mm:ss”的字符串
   * 
   * @param date
   * @return
   */
  public static String dateToDateString( Date date ) {
    return dateToDateString(date, yyyy_MM_dd_HH_mm_ss_EN);
  }

  /**
   * 将Date转换成formatStr格式的字符串
   * 
   * @param date
   * @param formatStr
   * @return
   */
  public static String dateToDateString( Date date, String formatStr ) {
    DateFormat df = getDateFormat(formatStr);
    return df.format(date);
  }

  /**
   * 获取DateFormat
   * 
   * @param dateTimeStr
   * @param formatStr
   * @return
   */
  public static DateFormat getDateFormat( String formatStr ) {
    DateFormat df = dateFormatMap.get(formatStr);
    if(df == null) {
      df = new SimpleDateFormat(formatStr);
      dateFormatMap.put(formatStr, df);
    }
    return df;
  }

  /**
   * @Title: getUTCDate
   * @Description: 取得当前的UTC时间
   * @Author: Administrator
   * @Since: 2012-10-15上午11:25:36
   * @return
   */
  public static Date getUTCDate() {
    java.util.Calendar cal = java.util.Calendar.getInstance();
    int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
    int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
    cal.add(java.util.Calendar.MILLISECOND, -( zoneOffset + dstOffset ));
    Date utcDate = new Date(cal.getTimeInMillis());
    return utcDate;
  }

  public static Date getDate( Long time ) {
    if(time != null) {
      return new Date(time);
    }
    else {
      return null;
    }
  }

 
  public static void main( String[] args ) {
    System.out.println(getDate(Long.valueOf("1446866687473031")));
  }
}
