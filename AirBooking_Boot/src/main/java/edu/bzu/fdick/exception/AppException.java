package edu.bzu.fdick.exception;


//  自定义异常类
public class AppException extends RuntimeException {
    private int code;
    private String msg;

    public AppException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public AppException(AppExceptionCodeMsg appExceptionCodeMsg) {
        this.code = appExceptionCodeMsg.getCode();
        this.msg = appExceptionCodeMsg.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

