package com.password.manager.ts_encrypt.exception;

public class UnsupportedSymbolException extends RuntimeException {
    public UnsupportedSymbolException() {
        super();
    }

    public UnsupportedSymbolException(String message) {
        super(message);
    }

    public UnsupportedSymbolException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnsupportedSymbolException(Throwable cause) {
        super(cause);
    }
}
