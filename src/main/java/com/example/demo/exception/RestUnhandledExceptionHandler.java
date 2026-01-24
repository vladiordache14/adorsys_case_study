package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class RestUnhandledExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    protected ResponseEntity<Object> handleException(RuntimeException ex, WebRequest request) {
        log.error("Unhandled exception.", ex);
        return handleExceptionInternal(null, null, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
