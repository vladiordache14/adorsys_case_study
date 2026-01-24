package com.example.demo.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.NoSuchElementException;

@ControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE - 1)
public class NoSuchElementExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> handleException(NoSuchElementException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.NOT_FOUND);
    }

}