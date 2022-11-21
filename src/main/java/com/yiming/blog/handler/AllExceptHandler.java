package com.yiming.blog.handler;

import com.yiming.blog.vo.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
//AOP思想
@ControllerAdvice
@ResponseBody
public class AllExceptHandler {
    @ExceptionHandler(Exception.class)
    public Result handleRuntimeException(Exception e) {
//        log.error(e.toString(), e);
        e.printStackTrace();
        return Result.fail(-999,"服务器异常");
    }
}
