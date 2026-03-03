package com.example.demo.exception;

import org.example.authaspect.exception.InsufficientRoleException;
import org.example.authaspect.exception.MissingTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class AuthorizationExceptionHandler extends BaseExceptionHandler {

    @ExceptionHandler(MissingTokenException.class)
    public ResponseEntity<Object> handleMissingToken(MissingTokenException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InsufficientRoleException.class)
    public ResponseEntity<Object> handleInsufficientRole(InsufficientRoleException ex, WebRequest request) {
        return handleException(ex, request, HttpStatus.FORBIDDEN);
    }
}