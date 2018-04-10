package platform.common.data.thirddto;

public class PayInfoResp {
	
	//{"cmd":"user_bank_bind_query","code":"00000","data":{"accName":"t t t","account":"22","area":"广东省","bankChannel":0,"bankName":"兴业银行","city":"广州","userName":"18520126199"},"msg":""}
	
	private String cmd;
	
	private String code;
	
	private String data;
	
	private String msg;

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	

}
