package platform.common.utils;

/**
 * Created with antnest-platform
 * User: chenyuan
 * Date: 12/13/14
 * Time: 6:25 PM
 */
public class Response {

    private Integer code = 0;
    private String message = "操作成功！";
    private Long time = System.currentTimeMillis();
    private String version = "1.0.0";
    private Object body;

    public Response() {
    }

    public Response(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Response(Object body) {
        this.body = body;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public static Response ok() {
        return new Response();
    }

    public static Response build(Object result) {
        return new Response(result);
    }
}
