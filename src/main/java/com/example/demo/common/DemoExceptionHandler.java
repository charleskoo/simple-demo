package com.example.demo.common;

import com.example.demo.entity.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class DemoExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorResponse jsonErrorHandler(Exception exception) {
        log.error(exception.getMessage(), exception);
        return ErrorResponse.ofException(exception);
    }
}
