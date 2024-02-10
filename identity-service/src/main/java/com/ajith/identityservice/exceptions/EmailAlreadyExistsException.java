package com.ajith.identityservice.exceptions;

public class EmailAlreadyExistsException extends Throwable {
    public EmailAlreadyExistsException (String message) {
        super(message);
    }
}
