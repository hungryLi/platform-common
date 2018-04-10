package platform.common.handler;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

public class FinanceExceptionHandler {
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		// 这里可以把异常信息通过邮件发送到相应维护人员的邮箱中
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ex", ex);
		
		System.out.println("error...");
		ex.printStackTrace();
		
		return new ModelAndView("forward:/WEB-INF/common/500.jsp", model);
	}

}
