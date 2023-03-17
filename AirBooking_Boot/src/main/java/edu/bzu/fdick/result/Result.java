package edu.bzu.fdick.result;


import edu.bzu.fdick.exception.AppExceptionCodeMsg;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {

    private int code;
    private String msg;
    private Object data;


    public Result(Object data) {
        this.data = data;
    }
    public Result(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static  Result success() {
        return new Result(200,"操作成功");
    }

    public static  Result success(Object data) {
        return new Result(200,"操作成功",data);
    }

    public static  Result success(int code,String msg,Object data) {
        return new Result(code,msg,data);
    }

    public static  Result error(AppExceptionCodeMsg appExceptionCodeMsg) {
        return new Result(appExceptionCodeMsg.getCode(),appExceptionCodeMsg.getMsg(),null);
    }
    public static  Result error(int code, String msg) {
        return new Result(400, msg);
    }
    public static  Result error(String msg) {
        return new Result(400, msg);
    }

    public int getCode() {
        return code;
    }
    public String getMsg() {
        return msg;
    }
    public Object getData() {
        return data;
    }

}