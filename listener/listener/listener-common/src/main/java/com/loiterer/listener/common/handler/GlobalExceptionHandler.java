package com.loiterer.listener.common.handler;

import com.loiterer.listener.common.exception.ListenerException;
import com.loiterer.listener.common.result.ResultCode;
import com.loiterer.listener.common.result.ResultEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局的统一异常处理类
 * @author XieZhiJie
 * @date 2020/10/25 13:01
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 自定义异常处理
     * @param e 自定义异常
     * @return  返回统一的自定义异常的信息
     */
    @ExceptionHandler(ListenerException.class)
    @ResponseBody
    public ResultEntity listenerException(ListenerException e) {
        log.error(e.getMessage());
        // e.getMessage是自己抛出自定义异常的信息
        return ResultEntity.fail().message(e.getMessage());
    }

    /**
     * 404异常处理
     * @param e 找不到请求的handler异常
     * @return  返回统一的404异常的信息
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public ResultEntity notFountException(NoHandlerFoundException e) {
        log.error(e.getMessage());
        return ResultEntity.fail().status(ResultCode.NOT_FOUND).message("404, 该请求无法处理!");
    }

    /**
     * 处理所有异常的方法
     * @param e Exception异常
     * @return  返回统一的异常信息
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultEntity error(Exception e) {
        log.error(e.getMessage());
        // 因为目前是测试阶段, 因此直接将e.getMessage返回前端, 方便看是什么错
        return ResultEntity.fail().message(e.getMessage());
    }

}
