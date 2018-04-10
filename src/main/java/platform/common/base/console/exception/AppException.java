package platform.common.base.console.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

@SuppressWarnings("serial")
public class AppException extends RuntimeException {

    private Throwable rootcause;

    private Object[] args;

    private String arg;
    
    private ErrorCode errorCode;

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public Throwable getRootcause() {
        return rootcause;
    }

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Object... args) {
        super(message);
        this.args = args;
    }

    public AppException(String message, String arg) {
        super(message);
        this.arg = arg;
    }

    public AppException(ErrorCode errorCode) {
        super(errorCode.getError().code());
        this.arg = errorCode.getError().msg();
        this.errorCode = errorCode;
    }

    public AppException(ErrorCode errorCode, String msg) {
    	super(msg);
        this.arg = errorCode.getError().msg() + " " + msg;
    }

    public AppException(Throwable cause) {
        rootcause = cause;
    }

    public AppException(String message, Throwable cause) {
        super(message);
        rootcause = cause;
    }

    public AppException(String message, Throwable cause, Object... args) {
        super(message);
        rootcause = cause;
        this.args = args;
    }

    public String getMessage() {
        if (rootcause == null) {
            return super.getMessage();
        } else {
            return super.getMessage() + "; cause exception is: \n\t" + rootcause.toString();
        }
    }

    public void printStackTrace() {
        printStackTrace(System.err);
    }

    public void printStackTrace(PrintStream s) {
        if (rootcause == null) {
            super.printStackTrace(s);
        } else {
            s.println(this);
            rootcause.printStackTrace(s);
        }
    }

    public void printStackTrace(PrintWriter s) {
        if (rootcause == null) {
            super.printStackTrace(s);
        } else {
            s.println(this);
            rootcause.printStackTrace(s);
        }
    }

    public String getArg() {
        return arg;
    }

    public void setArg(String arg) {
        this.arg = arg;
    }

	public ErrorCode getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
}
