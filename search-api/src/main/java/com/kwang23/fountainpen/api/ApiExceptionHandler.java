package com.kwang23.fountainpen.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST.value()));
    }
    @ExceptionHandler(HttpClientErrorException.class)
    protected ResponseEntity<ErrorResponse> handleHttpClientErrorException(HttpClientErrorException e) {
        return ResponseEntity.status(e.getStatusCode()).body(new ErrorResponse(e.getMessage(), e.getStatusCode().value()));
    }
}
