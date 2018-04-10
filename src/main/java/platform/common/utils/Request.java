package platform.common.utils;


/**
 * Created with antnest-platform
 * User: chenyuan
 * Date: 12/13/14
 * Time: 6:19 PM
 */
public class Request {

    /**
     * ios
     * android
     * windowsphone
     * web
     */
    private String client;

    /**
     * 业务使用方，业务申请时由后台生成
     */
    private String appKey;

    /**
     * 本次调用的签名信息，客户端在调用时把所有参数拼接起来做一个md5签名，
     * 服务端根据同样规则进行签名校验，通过后方可调用数据
     */
    private String signature;

    /**
     * 当前时间戳
     */
    private String time;

    /**
     * 版本号
     */
    private String version;

    /**
     * json,xml
     */
    private String format;

    private String body;

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
