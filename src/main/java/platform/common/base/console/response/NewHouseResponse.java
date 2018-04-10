package platform.common.base.console.response;

import platform.common.base.console.exception.ErrorCode;

public class NewHouseResponse<T> {
	private ErrorCode errorCode;
	private T responseBody;
	public ErrorCode getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	public T getResponseBody() {
		return responseBody;
	}
	public void setResponseBody(T responseBody) {
		this.responseBody = responseBody;
	}
	
}
