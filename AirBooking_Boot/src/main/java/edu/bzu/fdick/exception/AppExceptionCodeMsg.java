package edu.bzu.fdick.exception;


//业务异常 枚举类
public enum AppExceptionCodeMsg {
    INVALID_CODE(10001, "验证码错误"),
    INVALID_USERNAME(10002, "用户名不存在"),
    INVALID_PASSWORD(10003, "密码错误"),
    INVALID_USER(10004, "用户不存在");


    private int code;
    private String msg;

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    AppExceptionCodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }





}
