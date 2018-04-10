package platform.common.utils.sendmail;


/**
 * 
 * @since  2014年11月25日
 */
@Deprecated
public class Email {
    //收件人
    private String[] to;
    //抄送人
    private String[] cc;
    //发件人
    private String from;
    //主题
    private String subject;
    //内容
    private String content;
    
    
    public Email(String[] to, String[] cc, String from, String subject, String content) {
        super();
        this.to = to;
        this.cc = cc;
        this.from = from;
        this.subject = subject;
        this.content = content;
    }

    public String[] getTo() {
        return to;
    }
    
    public void setTo(String[] to) {
        this.to = to;
    }
    
    public String[] getCc() {
        return cc;
    }
    
    public void setCc(String[] cc) {
        this.cc = cc;
    }
    
    public String getFrom() {
        return from;
    }
    
    public void setFrom(String from) {
        this.from = from;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    
}
