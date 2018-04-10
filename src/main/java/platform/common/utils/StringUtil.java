package platform.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {

	private final static Logger logger = LoggerFactory.getLogger(StringUtil.class);

	public static void main(String[] args) {

	}

	/**
	 * 判断URL是否需要拦截
	 * 
	 * @param url
	 * @return
	 */
	public static boolean ifNoCheckUrl(String url) {

		for (String vurl : Const.NOCHECK_URLS) {
			// logger.debug("vurl:"+vurl+";ourl:"+url);
			String vurl2 = vurl.replace("/" + Const.SYS_NAME, "");
			logger.debug("vurl2:" + vurl2);
			if (vurl.equalsIgnoreCase(url) || vurl2.equalsIgnoreCase(url)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 合法的地址 判断规则
	 * 
	 * @param sysUrl 系统角色的合法的URL
	 * @param reqUrl请求的URL
	 * @return
	 */
	public static boolean compareUrl(String sysUrl, String reqUrl) {

		// String sysUrl2 = sysUrl.replace("/"+Const.SYS_NAME, "");
		if (sysUrl.equalsIgnoreCase(reqUrl)) {
			return true;
		}

		if (sysUrl.endsWith("/*")) {
			if (sysUrl.length() < 3) {
				return false;
			}
			// 前提是sysUrl 长度大于2
			if (sysUrl.startsWith("/")) {
				sysUrl = sysUrl.substring(1, sysUrl.length() - 2);// 去掉头部的/
			} else {
				sysUrl = sysUrl.substring(0, sysUrl.length() - 2);// 去掉尾部的 /*
			}
			// 包含关系
			if (reqUrl.contains(sysUrl)) {
				return true;
			}

		}
		return false;
	}

	public static String urlDecode(String str) {
		if (StringUtil.isEmpty(str)) {
			return "";
		}
		String temp = "";
		try {
			temp = java.net.URLDecoder.decode(str, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return temp;
	}

	public static boolean chkNoErrors(String[] allows, String msg) {

		if (msg == null || msg.trim().length() <= 0) {
			return true;
		}

		String[] msgs = msg.split("；");
		if (msgs == null || msgs.length < 0) {
			return false;
		}

		int count = 0;
		int len = msgs.length;
		for (String m : msgs) {
			for (String as : allows) {
				if (as.equals(m)) {
					count++;
					break;
				}
			}
		}

		if (count == len) {
			return true;
		}

		return false;
	}

	public static String replaceEmpty(String str) {

		if (str != null && str.trim().length() > 0) {
			return str.replace(" ", "");
		}

		return "";
	}

	public static String convertDatePatten(Date date, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	public static String convertTimeStringPattern(String utime, String oldPattern, String newPattern) {

		logger.debug("oldtime:" + utime);
		SimpleDateFormat format1 = new SimpleDateFormat(oldPattern);
		SimpleDateFormat format2 = new SimpleDateFormat(newPattern);
		Date date = null;
		String newString = "";
		try {
			date = format1.parse(utime);
			newString = format2.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// logger.debug("newtime:" + newString);
		return newString;
	}

	public static String getRandomCode(int maxnum, int bitcount) {
		Random rd = new Random();
		int num = rd.nextInt(maxnum);
		logger.debug("num=" + num);
		String str = num + "";
		int len = str.length();
		for (int i = 0; i < (bitcount - len); i++) {
			str = "0" + str;
		}
		// logger.debug("str=" + str);

		return str;
	}

	public static boolean isMobile(String userAgent){
		if(isEmpty(userAgent)){
			return false;
		}
		if(userAgent.indexOf("iPhone") > -1 || 
				userAgent.indexOf("Android") > -1 || 
					userAgent.indexOf("Windows Phone") > -1 || 
						userAgent.indexOf("iPad") > -1){
				return true;
		}
		return false;
	}

	public static boolean isEmpty(String str) {

		if (str == null || str.trim().length() <= 0 || "null".equalsIgnoreCase(str)) {
			return true;
		} else {
			return false;
		}

	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	public static String getFirstUpperString(String str) {
		String tmp = str.substring(0, 1).toUpperCase();
		return tmp + str.toLowerCase().substring(1, str.length());
	}

	public static String getLevel2Domain(String domain) {
		if (isEmpty(domain)) {
			return "";
		}

		String[] strs = domain.split("\\.");
		// logger.debug("strs.len:" + strs.length);
		if (strs.length == 2) {
			return "";
		} else {
			return strs[0];
		}
	}

	public static String getLevel1Domain(String domain) {
		if (isEmpty(domain)) {
			return "";
		}

		String[] strs = domain.split("\\.");
		// logger.debug("strs.len:" + strs.length);
		if (strs.length == 2) {
			return domain;
		} else {
			String res = "";
			for (int i = 1; i < strs.length; i++) {
				if (i == strs.length - 1) {
					res += strs[i];
				} else {
					res += strs[i] + ".";
				}
			}
			return res;
		}
	}

	public static String readFileByPath(String fileName) {
		// logger.debug("fileName--->" + fileName);
		File file = new File(fileName);
		StringBuilder sb = new StringBuilder(1000);
		BufferedReader reader = null;
		try {
			// System.out.println("以行为单位读取文件内容，一次读一整行：");
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			// int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
				// 显示行号
				// System.out.println("line " + line + ": " + tempString);
				// line++;
				sb.append(tempString);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}

		return sb.toString();
	}
	
	/**
	 * 获取客户端IP<br>
	 * @param request 请求
	 * @return
	 */
	public static String getRemoteIp(HttpServletRequest request) {
		return request.getRemoteAddr();
	}
}
