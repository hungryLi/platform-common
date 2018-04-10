package platform.common.base.api;


import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;


/***
 * 请求信息对象
 * 
 * @author : muck
 * @date : 2015年10月15日 下午1:03:24
 * @version : 1.0
 */
public class RequestInfo implements Serializable {

  /**
   * @Fields serialVersionUID : TODO 变量名称
   */

  private static final long serialVersionUID = 6945645258236621137L;
  private String            contentType;
  private String            charset;
  private String            encoding;
  private String            accept;

  private String            requestId;
  private boolean           isEncrypt;
  private boolean           isCompress;
  private long              sendTimestamp;

  private JSONObject        postJson;                               // post
                                                                     // 过来的所有json数据
  private String            servletPath;                            // 目前为/V1_01
  private String            pathInfo;                               // 请求路径每一个请求的path

  public String getContentType() {
    return contentType;
  }
  public void setContentType( String contentType ) {
    this.contentType = contentType;
  }
  public String getCharset() {
    return charset;
  }
  public void setCharset( String charset ) {
    this.charset = charset;
  }
  public String getEncoding() {
    return encoding;
  }
  public void setEncoding( String encoding ) {
    this.encoding = encoding;
  }
  public String getAccept() {
    return accept;
  }
  public void setAccept( String accept ) {
    this.accept = accept;
  }
  public String getRequestId() {
    return requestId;
  }
  public void setRequestId( String requestId ) {
    this.requestId = requestId;
  }
  public boolean isEncrypt() {
    return isEncrypt;
  }
  public void setEncrypt( boolean isEncrypt ) {
    this.isEncrypt = isEncrypt;
  }
  public boolean isCompress() {
    return isCompress;
  }
  public void setCompress( boolean isCompress ) {
    this.isCompress = isCompress;
  }
  public JSONObject getPostJson() {
    return postJson;
  }
  public void setPostJson( JSONObject postJson ) {
    this.postJson = postJson;
  }
  public long getSendTimestamp() {
    return sendTimestamp;
  }
  public void setSendTimestamp( long sendTimestamp ) {
    this.sendTimestamp = sendTimestamp;
  }
  public String getServletPath() {
    return servletPath;
  }
  public void setServletPath( String servletPath ) {
    this.servletPath = servletPath;
  }
  public String getPathInfo() {
    return pathInfo;
  }
  public void setPathInfo( String pathInfo ) {
    this.pathInfo = pathInfo;
  }

}
