package com.ec.prontiauto.exception;

import org.springframework.http.HttpStatus;

public class ApiRequestException extends RuntimeException {

    private HttpStatus statusCode;


    public ApiRequestException(String message, Throwable cause, HttpStatus statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public ApiRequestException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return this.statusCode;
    }

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
