package com.photoapp.auth.exception;

public class InvalidCredentialsException extends RuntimeException{


    private final String message;
    public InvalidCredentialsException(Throwable cause) {
        super(cause);
    }

    public InvalidCredentialsException(String message) {
        super(message);
        this.message = message;
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }
}
