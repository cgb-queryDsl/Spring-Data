package com.nhnacademy.certificate.controller;

import com.nhnacademy.certificate.exception.ValidationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity handleValidationException(ValidationFailedException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e);
    }
}
