package com.example.demo.common;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
@Order(1)
public class LogAspect {

    @Around("execution(public * com.example.demo.rest.*.*(..))")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long time = System.currentTimeMillis();
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        String methodFullName = className + "." + methodName;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        String requestUrl = request.getRequestURI();
        Object[] obj = point.getArgs();
        if (null == obj || obj.length < 1) {
            log.info("Request URL: {}, Request Method: {}", requestUrl, methodFullName);
        } else {
            log.info("Request URL: {}, Request Method: {}, Request body : {}", requestUrl, methodFullName, obj);
        }

        Object responseObject = point.proceed();
        log.debug("Request URL: {}, Response: {}", requestUrl, responseObject);
        log.info("Request URL: {}, Spend Time: {}ms", requestUrl, System.currentTimeMillis() - time);
        return responseObject;
    }
}
