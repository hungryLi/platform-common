package platform.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 系统常量
 * 
 * @author andy.li
 * 
 */
public class Const {

	private static InputStream in = null;

	// 配制文件
	public static final String filename = "/env/config.properties";

	// 属性文件
	public static Properties p = null;

	// 目录分隔符
	public static String SEP = System.getProperty("file.separator");

	// 读取配制文件信息，这样好处是当修改了配制文件不用重新编译代码
	static {
		try {
			in = Const.class.getResourceAsStream(filename);
			p = new Properties();
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	// 系统名称
	public static final String SYS_NAME = (String) p.get("SYS_NAME");

	public static final Integer DEFAULT_PAGEROWNUM = Integer.parseInt((String) p.get("DEFAULT_PAGEROWNUM"));

	public static final String SYS_IMGTYPE_LEVE1MENU_ID = (String) p.get("SYS_IMGTYPE_LEVE1MENU_ID");

	public static final String SYS_IMGTYPE_SYSMENU_ID = (String) p.get("SYS_IMGTYPE_SYSMENU_ID");

	public static final String SYS_CURUSER_SESSION_NAME = (String) p.get("SYS_CURUSER_SESSION_NAME");

	public static final String SYS_LOGIN_URL = (String) p.get("SYS_LOGIN_URL");

	public static final String SYS_LOGIN_URL1 = (String) p.get("SYS_LOGIN_URL1");

	public static final String SYS_VALIDATE_URL = (String) p.get("SYS_VALIDATE_URL");

	public static final String[] NOCHECK_URLS = new String[] { SYS_LOGIN_URL, SYS_LOGIN_URL1, SYS_VALIDATE_URL };

	// 用户登录过期时间，单位分钟
	public static final Integer LOGIN_EXPIRATION_TIME = Integer.parseInt((String) p.get("LOGIN_EXPIRATION_TIME"));

	public static final String SYS_COOKIE_SESSION_ID = (String) p.get("SYS_COOKIE_SESSION_ID");

	public static final String SYS_COOKIE_DOMAIN = (String) p.get("SYS_COOKIE_DOMAIN");

	public static final String SYS_COOKIE_PATH = (String) p.get("SYS_COOKIE_PATH");

	public static final String SYS_COOKIE_USER_UUID = (String) p.get("SYS_COOKIE_USER_UUID");

	public static final String SYS_COOKIE_USER_ID = (String) p.get("SYS_COOKIE_USER_ID");

	// 用户登录
	public static final String USER_LOGIN_URL = (String) p.get("USER_LOGIN_URL");

	public static final String APP_KEY = (String) p.get("APP_KEY");

	public static final String NAME_SPACE = (String) p.get("NAME_SPACE");

	public static final String USER_TYPE = (String) p.get("USER_TYPE");

	// 经纪专员角色,专员 28、主管 8、经理 31
	public static final Integer SYS_ROLE_BROKER_ID = Integer.parseInt((String) p.get("SYS_ROLE_BROKER_ID"));

	public static final Integer SYS_ROLE_DIRECTOR_ID = Integer.parseInt((String) p.get("SYS_ROLE_DIRECTOR_ID"));

	public static final Integer SYS_ROLE_MANAGER_ID = Integer.parseInt((String) p.get("SYS_ROLE_MANAGER_ID"));

	/**
	 * 系统当前版本号
	 */
	public static final String SYS_CUR_VERTION = (String) p.get("SYS_CUR_VERTION");
	
	/**
	 * 系统类型，这里都为2
	 */
	public static final Integer SYS_TYPE = Integer.parseInt((String) p.get("SYS_TYPE"));

	public static void main(String[] args) {

		System.out.println(DEFAULT_PAGEROWNUM);

	}

}
