package com.example.demo.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 2)
public class IllegalStateExceptionHandler extends BaseExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<Object> handleIllegalStateException(IllegalArgumentException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.BAD_REQUEST);
    }
}