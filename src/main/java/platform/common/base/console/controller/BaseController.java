package platform.common.base.console.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.app.server.cache.ApiRedisKeyConstant;
import org.app.server.cache.ShardedJedisUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.support.WebApplicationObjectSupport;

import com.alibaba.fastjson.JSON;

import platform.common.base.console.exception.AppException;
import platform.common.base.console.exception.ErrorCode;
import platform.common.base.console.response.BaseResponse;
import platform.common.utils.JSONUtil;


/**
 * 基础Action
 */
// @Controller
public class BaseController extends WebApplicationObjectSupport {

  protected static final String DATE_FORMATE_STR  = "yyyy/MM/dd";
  protected Logger logger = Logger.getLogger(BaseController.class);

  protected String writeLog( String request, BaseResponse response ) {
    String json = JSON.toJSONString(response);
    this.logger.warn("request：{}"+ request);
    this.logger.warn("response：{}"+ json);
    return json;
  }

  protected String writeLog( String request, Object object ) {
    String json = JSON.toJSONString(object);
    this.logger.warn("request：{}"+ request);
    this.logger.warn("response：{}"+ json);
    return json;
  }

  protected void setStatus( AppException e, BaseResponse status ) {
    status.setErrCode(e.getMessage());
    status.setErrMsg(e.getArg());
    this.logger.error(e.getMessage(), e);
    status.setSuccess(false);
  }

  //
  protected void setStatus( Exception e, BaseResponse status ) {
    this.logger.error(e.getMessage(), e);
    status.setErrCode(ErrorCode.system_1000.getError().code());
    status.setErrMsg(ErrorCode.system_1000.getError().msg() + e.getMessage());
    try {
      // exceptionLogsManager.insert(exceptionLogs);
    }
    catch(Exception e1) {
      /*
       * exceptionLogs = new ExceptionLogs(new SQLException("系统异常日志记录错误." + new
       * Date()), null, AgentCreditConstants.SERVER_NAME, null, null, null,
       * null); e1.printStackTrace();
       */
      try {
        // exceptionLogsManager.insert(exceptionLogs);
      }
      catch(Exception e2) {
        e2.printStackTrace();
      }
    }
    status.setSuccess(false);
  }

  protected void setStatus( ErrorCode errorCode, BaseResponse status ) {
    status.setErrCode(errorCode.getError().code());
    status.setErrMsg(errorCode.getError().msg());
  }

  /**
   * 构造基本的返回结构体<br>
   * 
   * @param actionName
   * @return 基础返回结构体
   */
  protected String buildResponse( ErrorCode errorCode ) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("error_code", errorCode.getError().code());
    response.put("error_msg", errorCode.getError().msg());
    String result = JSONUtil.toJSONString(response);
    return result;
  }
  protected String buildResponse( ErrorCode errorCode, String errorMsg ) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("error_code", errorCode.getError().code());
    response.put("error_msg", errorMsg);
    logger.error("返回错误消息:" + errorMsg);
    String result = JSONUtil.toJSONString(response);
    return result;
  }

  protected String buildResponse( ErrorCode errorCode, Object data ) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("error_code", errorCode.getError().code());
    response.put("error_msg", errorCode.getError().msg());
    logger.error("返回错误消息:" + data);
    String result = JSONUtil.toJSONString(response);
    return result;
  }

  protected String buildResponse( String actionName, ErrorCode errorCode ) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("error_code", errorCode.getError().code());
    response.put("error_msg", errorCode.getError().msg());
    String json = JSON.toJSONStringWithDateFormat(response, "yyyy-MM-dd HH:mm:ss");
    return json;
  }

  protected String buildResponse( String actionName, ErrorCode errorCode, Object data ) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("error_code", errorCode.getError().code());
    logger.error("返回错误消息：" + data);
    response.put("error_msg", errorCode.getError().msg());
    String json = JSON.toJSONStringWithDateFormat(response, "yyyy-MM-dd HH:mm:ss");
    return json;
  }

  protected String buildResponse( String actionName, ErrorCode errorCode, String dataAlias, Object data ) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("error_code", errorCode.getError().code());
    response.put("error_msg", errorCode.getError().msg());
    response.put(dataAlias, data);
    String json = JSON.toJSONStringWithDateFormat(response, "yyyy-MM-dd HH:mm:ss");
    return json;
  }

  protected Map<String, Object> writeResponse( String actionName, ErrorCode errorCode, Map<String, Object> dataMap ) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("cmd", actionName);
    response.put("error_code", errorCode.getError().code());
    response.put("error_msg", errorCode.getError().msg());
    for( String key : dataMap.keySet() ) {
      response.put(key, dataMap.get(key));
    }
    writeJson(response);
    return response;
  }

  protected Map<String, Object> writeResponse( String actionName, ErrorCode errorCode, String dataAlias, Object data ) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("cmd", actionName);
    response.put("error_code", errorCode.getError().code());
    response.put("error_msg", errorCode.getError().msg());
    response.put(dataAlias, data);
    writeJson(response);
    return response;
  }

  protected Map<String, Object> writeResponse( String actionName, ErrorCode errorCode ) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("cmd", actionName);
    response.put("error_code", errorCode.getError().code());
    response.put("error_msg", errorCode.getError().msg());
    writeJson(response);
    return response;
  }

  @SuppressWarnings( "unused" )
  public void writeJson( Object object ) {
    // try {
    String json = JSON.toJSONStringWithDateFormat(object, "yyyy-MM-dd HH:mm:ss");
    HttpServletRequest request =
        ( (ServletRequestAttributes) RequestContextHolder.getRequestAttributes() ).getRequest();
    // HttpServletResponse response = servletContainer.getResponse();
    // response.setContentType("text/html;charset=utf-8");
    // response.getWriter().write(json);
    // response.getWriter().flush();
    // response.getWriter().close();
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
  }
  @SuppressWarnings( "unused" )
  public void writeHtml( String html ) {
    try {
      ServletWebRequest servletContainer = (ServletWebRequest) RequestContextHolder.getRequestAttributes();
      HttpServletRequest request = servletContainer.getRequest();
      HttpServletResponse response = servletContainer.getResponse();
      response.setContentType("text/html;charset=utf-8");
      response.getWriter().write(html);
      response.getWriter().flush();
      response.getWriter().close();
    }
    catch(IOException e) {
      e.printStackTrace();
    }
  }

  protected Map buildSuccessResponse( ErrorCode errorCode ) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("code", errorCode.getError().code());
    response.put("msg", "success");
    return response;
  }

  protected Map buildFailedResponse( ErrorCode errorCode, Object data ) {
    Map<String, Object> response = new HashMap<String, Object>();
    response.put("error_code", errorCode.getError().code());
    logger.error("返回错误消息:" + data);
    response.put("error_msg", errorCode.getError().msg());
    return response;
  }
  	
   /**
    * 校验登录token
    * @param token
    * @return
    */
    public static boolean valiToken(String token){
		 if(StringUtils.isBlank(token)){
			 return false;
		 }
		 ShardedJedisUtils shardJedis = new ShardedJedisUtils();
		 if(shardJedis.existsKey(ApiRedisKeyConstant.API_LOGIN_TOKEN_KEY+token)){
			 String value = shardJedis.getValueByeKey(ApiRedisKeyConstant.API_LOGIN_TOKEN_KEY+token);
			 shardJedis.set(ApiRedisKeyConstant.API_LOGIN_TOKEN_KEY+token, value, ApiRedisKeyConstant.API_LOGIN_TOKEN_TIME);
			 return true;
		 }else{
			 return false;
		 }
	 }
}
