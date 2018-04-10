package platform.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.app.server.cache.RedisKeyConstant;
import org.app.server.cache.ShardedJedisUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

/**
 * HTTP相关工具类<br>
 * 
 * @since 1.0
 * @date 2014年11月24日
 *
 */
public class HttpUtil {
	
	private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private final static String SYS_COOKIE_PATH = "/";
//	private final static String SYS_COOKIE_DOMAIN = ".ocalou.com";
//	private final static String SYS_FDD_SESSION_ID = "fangdd_sessionid";
	
	/**
	 * 获取客户端的sessionid信息
	 * @param request
	 * @return
	 */
	public static String getCookieSessionId(HttpServletRequest request){
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				logger.debug("name:{},value:{},path:{}",c.getName(),c.getValue(),c.getPath());
				if ("JSESSIONID".equalsIgnoreCase(c.getName())) {
					return c.getValue();
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 获取客户端的Session数据
	 * @param request
	 * @return
	 */
	/*public static String getCookieFddSessionId(HttpServletRequest request){
		
		logger.debug(request.getHeader("Cookie"));
		
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length > 0) {
			for (Cookie c : cookies) {
				logger.debug("name:{},value:{},path:{}",c.getName(),c.getValue(),c.getPath());
				if (SYS_FDD_SESSION_ID.equalsIgnoreCase(c.getName())) {
					return c.getValue();
				}
			}
		}
		
		return null;
	}*/
	
	/**
	 * 设置Session信息<br>
	 * 
	 * @param response
	 * @param name
	 * @param value
	 */
/*	public static void setCookie(HttpServletResponse response,String name,String value){
		Cookie ck = new Cookie(name, value);
		ck.setPath(SYS_COOKIE_PATH);
		ck.setDomain(SYS_COOKIE_DOMAIN);
		response.addCookie(ck);
	}*/

	
	
	
	
  private static final String DEFAULT_CHARSET = "UTF-8";
  private static final String METHOD_POST = "POST";
  private static final String METHOD_GET = "GET";
  /**
   * time out setting
   */
  private static final int DEFAULT_TIMEOUT = 10000;

  /**
   * 当前本机的网络IP
   */
  private static String localIp;

  /**
   * 获取本机的网络IP
   */
  public static String getLocalNetWorkIp() {
      if (localIp != null) {
          return localIp;
      }
      try {
          Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
          InetAddress ip = null;
          while (netInterfaces.hasMoreElements()) {// 遍历所有的网卡
              NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
              if (!ni.isUp() || ni.isLoopback() || ni.isVirtual() || ni.isPointToPoint()) {
                  continue;
              }
              Enumeration<InetAddress> addresss = ni.getInetAddresses();
              while (addresss.hasMoreElements()) {
                  InetAddress address = addresss.nextElement();
                  if (address instanceof Inet4Address) {// 这里暂时只获取ipv4地址
                      ip = address;
                      break;
                  }
              }
              if (ip != null) {
                  break;
              }
          }
          if (ip != null) {
              localIp = ip.getHostAddress();
          } else {
              localIp = "127.0.0.1";
          }
      } catch (Exception e) {
          localIp = "127.0.0.1";
      }
      return localIp;
  }

  /**
   * 执行HTTP POST请求。
   * 
   * @param url 请求地址
   * @param params 请求参数
   * @return 响应字符串
   * @throws IOException
   */
  public static String doPost(String url, Map<String, String> params, int connectTimeout, int readTimeout) throws IOException {
      return doPost(url, params, DEFAULT_CHARSET, connectTimeout, readTimeout);
  }

  public static String doPost(String url, Map<String, String> params) throws IOException {
      return doPost(url, params, DEFAULT_CHARSET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
  }

  /**
   * 执行HTTP POST请求。
   * 
   * @param url 请求地址
   * @param params 请求参数
   * @param charset 字符集，如UTF-8, GBK, GB2312
   * @return 响应字符串
   * @throws IOException
   */
  public static String doPost(String url, Map<String, String> params, String charset, int connectTimeout, int readTimeout) throws IOException {

      String ctype = "application/x-www-form-urlencoded;charset=" + charset;
      String query = buildQuery(params, charset);
      byte[] content = {};
      if (query != null) {
          content = query.getBytes(charset);
      }

      HttpURLConnection conn = null;
      OutputStream out = null;
      String rsp = null;
      try {
          try {
              conn = getConnection(new URL(url), METHOD_POST, ctype);

              conn.setConnectTimeout(connectTimeout);
              conn.setReadTimeout(readTimeout);
          } catch (IOException e) {
              throw e;
          }
          try {
              out = conn.getOutputStream();
              out.write(content);
              rsp = getResponseAsString(conn);
          } catch (IOException e) {
              throw e;
          }

      } finally {
          if (out != null) {
              out.close();
          }
          if (conn != null) {
              conn.disconnect();
          }
      }

      return rsp;
  }

  /**
   * 从URL中获取请求参数Map
   */
  public static Map<String, String> getParamsFromUrl(String url) {
      Map<String, String> map = new HashMap<String, String>();
      if (url != null && url.indexOf('?') != -1) {
          String query = url.substring(url.indexOf('?') + 1);

          String[] pairs = query.split("&");
          if (pairs != null && pairs.length > 0) {
              for (String pair : pairs) {
                  String[] param = pair.split("=", 2);
                  if (param != null && param.length == 2) {
                      map.put(param[0], param[1]);
                  }
              }
          }
      }

      return map;
  }

  /**
   * 执行HTTP GET请求。
   * 
   * @param url 请求地址
   * @param params 请求参数
   * @return 响应字符串
   * @throws IOException
   */
  public static String doGet(String url, Map<String, String> params) throws IOException {
      return doGet(url, params, DEFAULT_CHARSET);
  }

  /**
   * 执行HTTP GET请求。
   * 
   * @param url 请求地址
   * @param params 请求参数
   * @param charset 字符集，如UTF-8, GBK, GB2312
   * @return 响应字符串
   * @throws IOException
   */
  public static String doGet(String url, Map<String, String> params, String charset) throws IOException {
      HttpURLConnection conn = null;
      String rsp = null;

      try {
          String ctype = "application/x-www-form-urlencoded;charset=" + charset;
          String query = buildQuery(params, charset);
          try {
              conn = getConnection(buildGetUrl(url, query), METHOD_GET, ctype);
          } catch (IOException e) {
              throw e;
          }

          try {
              rsp = getResponseAsString(conn);
          } catch (IOException e) {
              throw e;
          }

      } finally {
          if (conn != null) {
              conn.disconnect();
          }
      }

      return rsp;
  }

  private static HttpURLConnection getConnection(URL url, String method, String ctype) throws IOException {
      HttpURLConnection conn = null;
      conn = (HttpURLConnection) url.openConnection();

      conn.setRequestMethod(method);
      conn.setDoInput(true);
      conn.setDoOutput(true);
      conn.setRequestProperty("Accept", "application/xml,application/json,text/javascript,text/html");
      conn.setRequestProperty("User-Agent", "top-sdk-java");
      conn.setRequestProperty("Content-Type", ctype);
      return conn;
  }

  private static URL buildGetUrl(String strUrl, String query) throws IOException {
      URL url = new URL(strUrl);
      if (StringUtils.isEmpty(query)) {
          return url;
      }

      if (StringUtils.isEmpty(url.getQuery())) {
          if (strUrl.endsWith("?")) {
              strUrl = strUrl + query;
          } else {
              strUrl = strUrl + "?" + query;
          }
      } else {
          if (strUrl.endsWith("&")) {
              strUrl = strUrl + query;
          } else {
              strUrl = strUrl + "&" + query;
          }
      }
      System.out.println(strUrl);

      return new URL(strUrl);
  }

  public static String buildQuery(Map<String, String> params, String charset) throws IOException {
      if (params == null || params.isEmpty()) {
          return null;
      }

      StringBuilder query = new StringBuilder();
      Set<Entry<String, String>> entries = params.entrySet();
      boolean hasParam = false;

      for (Entry<String, String> entry : entries) {
          String name = entry.getKey();
          String value = entry.getValue() == null ? "" : entry.getValue();
          // 忽略参数名为空的参数，参数值为空需要传递EmptyString
          if (!StringUtils.isEmpty(name)) {
              if (hasParam) {
                  query.append("&");
              } else {
                  hasParam = true;
              }

              query.append(name).append("=").append(URLEncoder.encode(value, charset));
          }
      }

      return query.toString();
  }

  protected static String getResponseAsString(HttpURLConnection conn) throws IOException {
      String charset = getResponseCharset(conn.getContentType());
      InputStream es = conn.getErrorStream();
      if (es == null) {
          return getStreamAsString(conn.getInputStream(), charset);
      } else {
          String msg = getStreamAsString(es, charset);
          if (StringUtils.isEmpty(msg)) {
              throw new IOException(conn.getResponseCode() + ":" + conn.getResponseMessage());
          } else {
              throw new IOException(msg);
          }
      }
  }

  private static String getStreamAsString(InputStream stream, String charset) throws IOException {
      try {
          BufferedReader reader = new BufferedReader(new InputStreamReader(stream, charset));
          StringWriter writer = new StringWriter();

          char[] chars = new char[256];
          int count = 0;
          while ((count = reader.read(chars)) > 0) {
              writer.write(chars, 0, count);
          }

          return writer.toString();
      } finally {
          if (stream != null) {
              stream.close();
          }
      }
  }

  private static String getResponseCharset(String ctype) {
      String charset = DEFAULT_CHARSET;

      if (!StringUtils.isEmpty(ctype)) {
          String[] params = ctype.split(";");
          for (String param : params) {
              param = param.trim();
              if (param.startsWith("charset")) {
                  String[] pair = param.split("=", 2);
                  if (pair.length == 2) {
                      if (!StringUtils.isEmpty(pair[1])) {
                          charset = pair[1].trim();
                      }
                  }
                  break;
              }
          }
      }

      return charset;
  }


  public static String doPost(String url, String params) throws IOException {
      return doPost(url, params, DEFAULT_CHARSET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
  }
  /**
   * POST 请求
   *
   * @param url            地址
   * @param params         参数
   * @param charset        编码
   * @param connectTimeout 超时时间
   * @param readTimeout    读取时间
   * @return
   * @throws IOException
   */
  private static String doPost(String url, String params, String charset, int connectTimeout, int readTimeout) throws IOException {
      HttpURLConnection urlconn = null;
      urlconn = (HttpURLConnection) new URL (url).openConnection ();
      urlconn.getRequestProperties ();
      urlconn.setRequestProperty("Content-Type", "application/json");
      urlconn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.113 Safari/537.36");
      urlconn.setRequestMethod("POST");
      urlconn.setConnectTimeout (connectTimeout);
      urlconn.setReadTimeout (readTimeout);
      urlconn.setDoInput (true);
      urlconn.setDoOutput (true);
      urlconn.getOutputStream ().write (params.getBytes());
      urlconn.getOutputStream ().close ();

      BufferedReader rd = new BufferedReader (new InputStreamReader (urlconn.getInputStream (),charset));

      String temp = null;
      StringBuffer sb = new StringBuffer ();
      temp = rd.readLine ();
      while (temp != null) {
          sb.append (temp);
          temp = rd.readLine ();
      }
      rd.close ();
      urlconn.disconnect ();
      return sb.toString ();
  }
  
  
  
  /**
   * 前端Api 请求调用方法
   * @param url
   * @param secretAccessKeyID
   * @param params
   * @return
   * @throws IOException
   */
  public static String doPost(String url, String secretAccessKeyID, String params) throws IOException {
	  if (StringUtil.isEmpty(secretAccessKeyID)) {
		  return null;
	  }
	  return doPost(url, null, secretAccessKeyID, params, DEFAULT_CHARSET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
  }
  
  public static String doPost(String url, String userAgent, String secretAccessKeyID, String params) throws IOException {
	  if (StringUtil.isEmpty(secretAccessKeyID)) {
		  return null;
	  }
	  return doPost(url, userAgent, secretAccessKeyID, params, DEFAULT_CHARSET, DEFAULT_TIMEOUT, DEFAULT_TIMEOUT);
  }
  
  /**
   * POST 请求
   *
   * @param url            地址
   * @param params         参数
   * @param charset        编码
   * @param connectTimeout 超时时间
   * @param readTimeout    读取时间
   * @return
   * @throws IOException
   */
  private static String doPost(String url, String userAgent, String secretAccessKeyID, String params, String charset, int connectTimeout, int readTimeout) throws IOException {
      HttpURLConnection urlconn = null;
      urlconn = (HttpURLConnection) new URL (url).openConnection ();
      urlconn.getRequestProperties ();
      String sign = null;
	  try {
		   ShardedJedisUtils shardJedis = new ShardedJedisUtils();
		   String signTemp = HMACSHA1.HmacSHA1Encrypt(shardJedis.getValueByeKey(RedisKeyConstant.API_SECRETACCESSKEYID_+secretAccessKeyID), params+url);
		   sign = new sun.misc.BASE64Encoder().encode(signTemp.getBytes()); 
		} catch (Exception e) {
			 e.printStackTrace();
		}
	  if(StringUtil.isNotEmpty(userAgent)){
		  urlconn.setRequestProperty("User-Agent", userAgent);
	  }
      urlconn.setRequestProperty("Authorization", "OGC "+secretAccessKeyID+":"+sign);
      urlconn.setRequestProperty ("content-type", "application/json");
      urlconn.setRequestMethod ("POST");
      urlconn.setConnectTimeout (connectTimeout);
      urlconn.setReadTimeout (readTimeout);
      urlconn.setDoInput (true);
      urlconn.setDoOutput (true);
      urlconn.getOutputStream ().write (params.getBytes());
      urlconn.getOutputStream ().close ();

      BufferedReader rd = new BufferedReader (new InputStreamReader (urlconn.getInputStream (),charset));

      String temp = null;
      StringBuffer sb = new StringBuffer ();
      temp = rd.readLine ();
      while (temp != null) {
          sb.append (temp);
          temp = rd.readLine ();
      }
      rd.close ();
      urlconn.disconnect ();
      return sb.toString ();
  }
}
