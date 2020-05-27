package com.lg.springbootexample.interceptor;

import com.alibaba.fastjson.JSON;
import com.lg.springbootexample.tools.ErrorCode;
import com.lg.springbootexample.tools.ServiceException;
import lombok.extern.log4j.Log4j2;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/**
 * @author ligang
 * @date 2020-05-27
 */
@Aspect
@Log4j2
public class ControllerInterceptor {

    @Around("execution(* com.lg.springbootexample.controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        long startTime = System.currentTimeMillis();
        String method = joinPoint.getSignature().getName();
        if (args.length > 0 && args[1] instanceof BindingResult) {
            BindingResult bindingResult = (BindingResult) args[1];
            if (bindingResult.hasErrors()) {
                ObjectError error = bindingResult.getAllErrors().get(0);
                return recordLog(ErrorCode.FAIL, "", startTime);
            }
        }
        try {
            Object result = joinPoint.proceed();
            return recordLog(ErrorCode.SUCCESS, result, startTime);
        } catch (Throwable t) {
            if (t instanceof ServiceException) {
                ServiceException e = (ServiceException) t;
                return recordLog(e.getError(), e.getError().getMsg(), startTime);
            }
            return recordLog(ErrorCode.FAIL, t.getMessage(), startTime);
        }
    }

    public Object recordLog(ErrorCode errorCode, Object result, long startTime) {
        return JSON.toJSONString(result);
    }
}