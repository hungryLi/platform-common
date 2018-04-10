package platform.common.base.console.response;

import java.io.Serializable;


/**
 * 
 * 服务回应基类 <功能详细描述>
 * 
 * @author walle
 * @version [版本号, 2013-5-21]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 * 
 */
public class BaseResponse implements Serializable
{
    private static final long serialVersionUID = -7939325253673044395L;
    
    // 命令
    protected String cmd = "";

    protected String errCode = "";

    protected String errMsg = "";

    protected boolean success = false;
    
    public BaseResponse()
    {
    }
    
    public BaseResponse(String cmd, String errCode, String errMsg)
    {
        this.cmd = cmd;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }
    
    public String getErrCode()
    {
        return errCode;
    }
    
    public void setErrCode(String errCode)
    {
        this.errCode = errCode;
    }
    
    public String getErrMsg()
    {
        return errMsg;
    }
    
    public void setErrMsg(String errMsg)
    {
        this.errMsg = errMsg;
    }
    
    public String getCmd()
    {
        return cmd;
    }
    
    public void setCmd(String cmd)
    {
        this.cmd = cmd;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
