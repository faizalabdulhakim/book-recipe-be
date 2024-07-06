package com.faizal.book.recipe.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;


@lombok.Data
public class ApiException {
    
    private final String message;
    private final Integer status;
    private final HttpStatus error;
    private final ZonedDateTime timestamp;

    public ApiException(String message, Integer status, HttpStatus error, ZonedDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.error = error;
        this.timestamp = timestamp;
    }
}
