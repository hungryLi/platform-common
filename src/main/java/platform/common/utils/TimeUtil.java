package platform.common.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TimeUtil {

  private final static Logger logger                      = LoggerFactory.getLogger(TimeUtil.class);
  public static final String  DATE_FORM_YY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

  public static void main( String[] args ) {
    // convertFormat(new Date(), "yyyy-MM-dd HH:mm:ss");
    // logger.debug("{}",new Date().getTime());
    // logger.debug("{}",24 * 60 * 60);
    // logger.info("time" + dateAddMinute(new Date(), -12 * 60));
    System.out.println(new Date().getDay());
  }

  public static Boolean isInTime( int fhour, int fminute, int thour, int tminute ) {
    String todaystr = getTodayStr();
    int chour = getHour(todaystr, "yyyy-MM-dd HH:mm:ss");
    int cminute = getMinute(todaystr, "yyyy-MM-dd HH:mm:ss");
    logger.info("fhour:" + fhour);
    logger.info("fminute:" + fminute);
    logger.info("thour:" + thour);
    logger.info("tminute:" + tminute);
    logger.info("chour:" + chour);
    logger.info("cminute:" + cminute);

    int ftime = fhour * 60 + fminute;
    int ttime = thour * 60 + tminute;
    int ctime = chour * 60 + cminute;
    if(ftime <= ctime && ctime <= ttime) {
      return true;
    }

    return false;
  }

  public static String convertFormat( String date, String oldformat, String newformat ) {
    SimpleDateFormat oldsdf = new SimpleDateFormat(oldformat);
    SimpleDateFormat newsdf = new SimpleDateFormat(newformat);
    String res = "";
    try {
      res = newsdf.format(oldsdf.parse(date));
    }
    catch(ParseException e) {

    }
    return res;
  }

  public static String convertFormat( Date date, String newformat ) {
    SimpleDateFormat newsdf = new SimpleDateFormat(newformat);
    String res = newsdf.format(date);
    return res;
  }

  public static String getOrderDate() {
    Date date = getRegularDate();
    Calendar cl = Calendar.getInstance();
    cl.setTime(date);
    if(cl.get(Calendar.HOUR_OF_DAY) < 3) {
      date = dateAddDay(date, -1);
    }
    return dateToString(date, "yyyy-MM-dd");
  }

  public static Date getRegularDate() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
    Date date = null;
    try {
      date = sdf2.parse(sdf.format(new Date()));
    }
    catch(ParseException e) {

    }
    return date;
  }

  public static boolean dateIsLargeOrEqual( String sdate, String edate, String pattern ) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    try {
      Date sd = sdf.parse(sdate);
      Date ed = sdf.parse(edate);
      if(ed.getTime() >= sd.getTime()) {
        return true;
      }
    }
    catch(ParseException e) {

    }

    return false;
  }

  public static Integer getYear( String strdate, String pattern ) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    try {
      Date date = sdf.parse(strdate);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar.get(Calendar.YEAR);
    }
    catch(ParseException e) {
      //
    }

    return null;
  }

  public static Integer getMonth( String strdate, String pattern ) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    try {
      Date date = sdf.parse(strdate);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar.get(Calendar.MONTH) + 1;
    }
    catch(ParseException e) {
      //
    }

    return null;
  }

  public static Integer getDay( String strdate, String pattern ) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    try {
      Date date = sdf.parse(strdate);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar.get(Calendar.DAY_OF_MONTH);
    }
    catch(ParseException e) {

    }

    return null;
  }

  public static Integer getHour( String strdate, String pattern ) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    try {
      Date date = sdf.parse(strdate);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar.get(Calendar.HOUR_OF_DAY);
    }
    catch(ParseException e) {

    }

    return null;
  }

  public static Integer getMinute( String strdate, String pattern ) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    try {
      Date date = sdf.parse(strdate);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return calendar.get(Calendar.MINUTE);
    }
    catch(ParseException e) {

    }

    return null;
  }

  public static String getWeekName( String strdate, String pattern ) {
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    try {
      Date date = sdf.parse(strdate);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      return Color.getName(calendar.get(Calendar.DAY_OF_WEEK));
    }
    catch(ParseException e) {
      //
    }

    return null;
  }

  public static double getHourBetweenTwoDays( String startTime, String endTime, String format ) {
    logger.info("startTime:" + startTime);
    logger.info("endTime:" + endTime);
    // 按照传入的格式生成一个simpledateformate对象
    SimpleDateFormat sd = new SimpleDateFormat(format);
    long nd = 1000 * 24 * 60 * 60;// 一天的毫秒数
    long nh = 1000 * 60 * 60;// 一小时的毫秒数
    long nm = 1000 * 60;// 一分钟的毫秒数
    // 获得两个时间的毫秒时间差异
    long diff = 0;
    double res = 0;
    try {
      diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
      long min = diff % nd % nh / nm;// 计算差多少分钟
      res = ( diff / nh ) + ( min / 60.0 );// 计算差多少小时
    }
    catch(ParseException e) {

    }
    logger.info("res::" + res);
    return res;
  }

  public enum Color {
    // Sunday Monday Tuesday Wednesday Thursday Friday Saturday
    Sunday("Sunday", 1), Monday("Monday", 2), Tuesday("Tuesday", 3), Wednesday("Wednesday", 4), Thursday("Thursday", 5), Friday(
        "Friday", 6), Saturday("Saturday", 7);
    // 成员变量
    private String name;
    private int    index;

    // 构造方法
    private Color( String name, int index ) {
      this.name = name;
      this.index = index;
    }

    // 普通方法
    public static String getName( int index ) {
      for( Color c : Color.values() ) {
        if(c.getIndex() == index) {
          return c.name;
        }
      }
      return null;
    }

    // get set 方法
    public String getName() {
      return name;
    }

    public void setName( String name ) {
      this.name = name;
    }

    public int getIndex() {
      return index;
    }

    public void setIndex( int index ) {
      this.index = index;
    }
  }

  public static String getWeekDayString() {
    Calendar cl = Calendar.getInstance();
    cl.setTime(new Date());
    return Color.getName(cl.get(Calendar.DAY_OF_WEEK));
  }

  public static Integer getTodayWeek() {
    Calendar cl = Calendar.getInstance();
    cl.setTime(new Date());
    return cl.get(Calendar.DAY_OF_WEEK);
  }

  public static String getCurTimeStamp() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date today = new Date();
    return sdf.format(today);
  }

  public static String dateToString( Date date, String format ) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    return sdf.format(date);
  }

  public static Date stringToDate( String date, String format ) {
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    try {
      return sdf.parse(date);
    }
    catch(ParseException e) {

    }
    return null;
  }

  public static boolean isInDateInterval( String fappdate, String tappdate, Long interval ) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date fdate;
    Date tdate;
    Long myinterval = 0L;

    try {
      fdate = sdf.parse(fappdate);
      tdate = sdf.parse(tappdate);
      myinterval = ( tdate.getTime() - fdate.getTime() ) / ( 24 * 60 * 60 * 1000 );
    }
    catch(ParseException e) {

    }

    logger.debug("fappdate=" + fappdate);
    logger.debug("tappdate=" + tappdate);
    logger.debug("myinterval=" + myinterval);

    return interval - myinterval >= 0 ? true : false;
  }

  public static boolean isInDateInterval( String fappdate, Date tdate, Long interval ) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date fdate;
    Long myinterval = 0L;

    try {
      fdate = sdf.parse(fappdate);
      myinterval = ( tdate.getTime() - fdate.getTime() ) / ( 24 * 60 * 60 * 1000 );
    }
    catch(ParseException e) {

    }
    logger.debug("fappdate=" + fappdate);
    logger.debug("tdate=" + tdate);
    logger.debug("myinterval=" + myinterval);
    return interval - myinterval >= 0 ? true : false;
  }

  /**
   * 返回date日期前或者后month的日期,day正表示：后；day负表示前
   * 
   * @param date
   * @param day
   * @return
   */
  public static Date dateAddMonth( Date date, Integer mons ) {
    mons = mons == null ? 0 : mons;
    Calendar cl = Calendar.getInstance();
    cl.setTime(date);
    cl.add(Calendar.MONTH, mons);
    return cl.getTime();
  }

  /**
   * 返回date日期前或者后month的日期,day正表示：后；day负表示前
   * 
   * @param date
   * @param day
   * @return
   */
  public static String dateAddMonth( String date, Integer mons ) {
    Date cur = stringToDate(date, "yyyy-MM-dd");
    Date after = dateAddMonth(cur, mons);
    return dateToString(after, "yyyy-MM-dd");
  }

  /**
   * 返回date日期前或者后day天的日期,day正表示：后；day负表示前
   * 
   * @param date
   * @param day
   * @return
   */
  public static Date dateAddDay( Date date, Integer day ) {
    Calendar cl = Calendar.getInstance();
    day = day == null ? 0 : day;
    cl.setTime(date);
    cl.add(Calendar.DATE, day);
    return cl.getTime();
  }

  /**
   * 加上分钟后的日期
   * 
   * @param date
   * @param minutes
   * @return
   */
  public static Date dateAddMinute( Date date, Integer minutes ) {
    minutes = minutes == null ? 0 : minutes;
    Calendar cl = Calendar.getInstance();
    cl.setTime(date);
    logger.debug("cl:" + cl);
    logger.debug("date:" + date);
    cl.add(Calendar.MINUTE, minutes);
    return cl.getTime();
  }

  /**
   * 获得今天日期，美国LA时区
   * 
   * @param pattern
   *          格式
   * @return
   */
  public static String getTodayStr( String pattern ) {
    if(StringUtil.isEmpty(pattern)) {
      pattern = "yyyy-MM-dd HH:mm:ss";
    }
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    Date date = new Date();
    return sdf.format(date);
  }

  /**
   * 获得今天日期，美国LA时区
   * 
   * @return
   */
  public static String getTodayStr() {
    String pattern = "yyyy-MM-dd HH:mm:ss";
    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
    Date date = new Date();
    return sdf.format(date);
  }

  /**
   * 获得今天日期，美国LA时区
   * 
   * @return
   */
  public static Date getToday() {
    String todaystr = getTodayStr("yyyy-MM-dd");
    return stringToDate(todaystr, "yyyy-MM-dd");
  }

  /**
   * 检查日期格式
   * 
   * @param sourceDate
   * @param pattern
   * @return
   */
  public static boolean checkDate( String sourceDate, String pattern ) {
    if(sourceDate == null) {
      return false;
    }
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(pattern);
      sdf.setLenient(false);
      sdf.parse(sourceDate);
      return true;
    }
    catch(Exception e) {}
    return false;
  }

}
