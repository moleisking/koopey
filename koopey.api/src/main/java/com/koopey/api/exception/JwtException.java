package com.koopey.api.exception;

public class JwtException extends BaseServiceException {
    public JwtException(String message) {
        super(message);
    }
}
