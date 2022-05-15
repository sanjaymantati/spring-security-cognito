package com.photoapp.auth.exception;

public class CustomException extends RuntimeException {

    private final String message;
    public CustomException(Throwable cause) {
        super(cause);
    }

    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }
}
