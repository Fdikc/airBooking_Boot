package edu.bzu.fdick.exception;


import edu.bzu.fdick.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Result handleException(Exception e) {
        if (e instanceof AppException) {
            AppException appException = (AppException) e;
            return Result.error(appException.getCode(), appException.getMsg());
        }
        return Result.error(500,e.getMessage());
    }
}
