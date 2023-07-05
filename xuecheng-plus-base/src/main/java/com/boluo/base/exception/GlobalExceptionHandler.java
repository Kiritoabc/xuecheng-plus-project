package com.boluo.base.exception;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kirito
 * @version 1.0
 * @description: TODO
 * @date 2023/5/30 17:13
 */
@Slf4j
@ControllerAdvice
//@RestControllerAdvice
public class GlobalExceptionHandler {

    //对项目的自定义异常类型进行处理
    @ResponseBody
    @ExceptionHandler(XueChengPlusException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse customException(XueChengPlusException e){

        //记录异常
        log.error("系统异常{}",e.getErrMessage(),e);
        //..

        //解析出异常信息
        String errMessage = e.getErrMessage();
        RestErrorResponse restErrorResponse = new RestErrorResponse(errMessage);
        return restErrorResponse;
    }

    // 其他异常信息
    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse exception(Exception e){

        //记录异常
        log.error("系统异常{}",e.getMessage(),e);

        //解析出异常信息
        RestErrorResponse restErrorResponse = new RestErrorResponse(CommonError.UNKOWN_ERROR.getErrMessage());
        return restErrorResponse;
    }

    // 数据校验框架的异常
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public RestErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e){

        BindingResult bindingResult = e.getBindingResult();
        List<String> errors = new ArrayList<>();
        bindingResult.getFieldErrors().stream().forEach(item -> {
            errors.add(item.getDefaultMessage());
        });
        // 拼接异常信息
        String errMessagee = StringUtils.join(errors, ',');
        //记录异常
        log.error("系统异常{}",e.getMessage(),e);

        //解析出异常信息
        RestErrorResponse restErrorResponse = new RestErrorResponse(errMessagee);
        return restErrorResponse;
    }
}
