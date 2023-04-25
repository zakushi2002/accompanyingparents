package com.webapp.accompanyingparents.config.exception;

public class UnauthorizationException extends RuntimeException {
    public UnauthorizationException(String message) {
        super(message);
    }
}