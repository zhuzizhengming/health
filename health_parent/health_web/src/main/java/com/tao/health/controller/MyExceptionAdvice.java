package com.tao.health.controller;

import com.tao.health.entity.Result;
import com.tao.health.exception.MyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * <p>
 * log日志：
 * info:  记录流程性的内容， 对重要业务时。
 * debug: 记录重要数据id,key
 * error: 记录异常信息 代替system.out, e.printStackTrace();
 * </p>
 *
 * @author: Eric
 * @since: 2021/1/6
 */
//这个注解表示该类是处理异常的类：与前端约定好的，返回的都是json数据
@RestControllerAdvice
public class MyExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(MyExceptionAdvice.class);

    /**
     * 业务异常的处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MyException.class)
    public Result handleMyException(MyException e) {
        return new Result(false, e.getMessage());
    }

    /**
     * 未知异常处理
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        // 记录异常信息
        log.error("发生未知异常", e);
        return new Result(false, "发生未知异常，请联系管理员");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public Result handleAccessDeniedException(AccessDeniedException e) {
        return new Result(false, "没有权限");
    }
}
