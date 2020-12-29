package com.password.manager.service.exception;

public class ArgumentRequiredException extends RuntimeException{
    public ArgumentRequiredException() {
        super();
    }

    public ArgumentRequiredException(String message) {
        super(message);
    }

    public ArgumentRequiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentRequiredException(Throwable cause) {
        super(cause);
    }

    protected ArgumentRequiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
