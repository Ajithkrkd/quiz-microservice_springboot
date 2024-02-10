package com.ajith.identityservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler (value = {EmailAlreadyExistsException.class})
    @ResponseStatus (value = HttpStatus.CONFLICT)
    public ErrorMessage UserBlockedException(EmailAlreadyExistsException ex , WebRequest request) {
        ErrorMessage message = new ErrorMessage();
        message.setStatus (HttpStatus.CONFLICT.value ());
        message.setMessage ( ex.getMessage() );
        message.setDescription ( "try another email" );
        message.setTimestamp ( LocalDateTime.now ( ) );
        return message;
    }


}
