package com.koopey.api.exception;

import org.springframework.beans.factory.BeanCreationException;

public class ServiceCreationException extends BeanCreationException {
    public ServiceCreationException(String message) {
        super(message);
    }
}
