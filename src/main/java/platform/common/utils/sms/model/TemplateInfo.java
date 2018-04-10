
/**
 * 应用id及模版id
 */
package platform.common.utils.sms.model;


public class TemplateInfo {
  private String appId;
  private String templateId;
  private String TemplateContent; // 模板内容

  public TemplateInfo( String appId, String templateId ) {
    this.appId = appId;
    this.templateId = templateId;
  }
  public String getAppId() {
    return appId;
  }
  public void setAppId( String appId ) {
    this.appId = appId;
  }
  public String getTemplateId() {
    return templateId;
  }
  public void setTemplateId( String templateId ) {
    this.templateId = templateId;
  }
  public String getTemplateContent() {
    return TemplateContent;
  }
  public void setTemplateContent( String templateContent ) {
    TemplateContent = templateContent;
  }

}
