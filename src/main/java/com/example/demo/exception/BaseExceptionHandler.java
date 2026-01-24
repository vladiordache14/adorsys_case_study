package com.example.demo.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleException(Exception exception, WebRequest request, HttpStatus status) {
        HttpHeaders headers = new HttpHeaders();
        String body = "{ \"status\": " + status.value() + ", \"message\": \"" + exception.getMessage() + "\" }";
        return handleExceptionInternal(exception, body, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
        if (body == null && !statusCode.is5xxServerError()) {
            body = "{ \"status\": " + statusCode.value() + ", \"message\": \"" + ex.getMessage() + "\" }";
        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }
}
