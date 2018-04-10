package platform.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import platform.common.mybatis.pageinterceptor.domain.Order;
import platform.common.mybatis.pageinterceptor.domain.PageBounds;
import platform.common.mybatis.pageinterceptor.domain.PageList;
import platform.common.mybatis.pageinterceptor.domain.Paginator;

public class JSPUtil {

	public static boolean isMobileClient() {
		boolean isMobile = false;
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String agent = request.getHeader("user-agent");
		String agentcheck = agent.trim().toLowerCase();
		String[] keywords = { "mobile", "android", "symbianos", "iphone", "wp\\d*", "windows phone", "mqqbrowser", "nokia", "samsung", "midp-2", "untrusted/1.0",
				"windows ce", "blackberry", "ucweb", "brew", "j2me", "yulong", "coolpad", "tianyu", "ty-", "k-touch", "haier", "dopod", "lenovo", "huaqin", "aigo-",
				"ctc/1.0", "ctc/2.0", "cmcc", "daxian", "mot-", "sonyericsson", "gionee", "htc", "zte", "huawei", "webos", "gobrowser", "iemobile", "wap2.0", "WAPI" };
		Pattern pf = Pattern.compile("wp\\d*");
		java.util.regex.Matcher mf = pf.matcher(agentcheck);
		if (agentcheck != null && (agentcheck.indexOf("windows nt") == -1 && agentcheck.indexOf("Ubuntu") == -1)
				|| (agentcheck.indexOf("windows nt") > -1 && mf.find())) {
			for (int i = 0; i < keywords.length; i++) {
				Pattern p = Pattern.compile(keywords[i]);
				java.util.regex.Matcher m = p.matcher(agentcheck);
				// 排除 苹果桌面系统 和ipad 、iPod
				if (m.find() && agentcheck.indexOf("ipad") == -1 && agentcheck.indexOf("ipod") == -1 && agentcheck.indexOf("macintosh") == -1) {
					isMobile = true;
					break;
				}
			}
		}
		return isMobile;
	}

	public static String getParameterString(String name) {
		return getParameterString(name, null);
	}

	public static String getParameterString(String name, String defaultValue) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String value = defaultValue;
		try {
			String str = (String) request.getParameter(name);
			value = StringUtil.isEmpty(str) ? defaultValue : str;
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return value;
	}

	public static Integer getParameterInteger(String name, Integer defaultValue) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		Integer value = defaultValue;
		try {
			String str = (String) request.getParameter(name);
			value = Integer.valueOf(str);
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return value;
	}

	public static Integer getParameterInteger(String name) {
		return getParameterInteger(name, null);
	}

	public static Integer getSessionAttributeInteger(String name, Integer defaultValue) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = request.getSession();
		Integer value = defaultValue;
		try {
			value = (Integer) session.getAttribute(name);
			if (value == null) {
				value = defaultValue;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return value;
	}

	public static Integer getSessionAttributeInteger(String name) {
		return getSessionAttributeInteger(name, null);
	}

	private static String addColumnSplash(String fieldName) {
		if (fieldName == null) {
			return null;
		}
		String columnName = "";
		for (int i = 0; i < fieldName.length(); i++) {
			char c = fieldName.charAt(i);
			if (Character.isLowerCase(c)) {
				columnName += c;
			} else {
				columnName += "_" + Character.toLowerCase(c);
			}
		}

		return columnName;
	}

	@SuppressWarnings("unused")
	public static PageBounds getPagerBoundFromRequest() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		String sortField = JSPUtil.getParameterString("sidx");
		// sortField = addColumnSplash(sortField);
		String sortOrder = JSPUtil.getParameterString("sord");
		Integer pageIndex = JSPUtil.getParameterInteger("page", 0);
		Integer pageSize = JSPUtil.getParameterInteger("rows", 20);

		if (pageIndex == null || pageSize == null) {
			return null;
		}

		PageBounds pageBounds = new PageBounds(pageIndex, pageSize, Order.formString(sortField + "." + sortOrder));
		return pageBounds;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object pagelistToJSONMap(PageList list) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (list != null) {
			Paginator paginator = list.getPaginator();
			map.put("totalCount", paginator.getTotalCount());
			map.put("totalPages", paginator.getTotalPages());
			map.put("page", paginator.getPage());
			map.put("items", new ArrayList(list));
		}
		return map;
	}

	public static void main(String[] args) {
		JSPUtil.addColumnSplash("userNameField");
	}

}
