package com.ec.prontiauto.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = { ApiRequestException.class })
    public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
        final HttpStatus statusCode = Objects.isNull(e.getStatusCode()) ? HttpStatus.BAD_REQUEST : e.getStatusCode();
        ApiException apiException = new ApiException(e.getMessage(), statusCode.toString());
        return new ResponseEntity<>(apiException, statusCode);
    }
}
