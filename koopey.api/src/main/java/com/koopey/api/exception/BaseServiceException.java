package com.koopey.api.exception;

public class BaseServiceException extends RuntimeException {

    private final String[] parameters;

    public BaseServiceException (String message, String... parameters){
        super(message);
        this.parameters =  parameters;
    }

    public String[] getParameters(){return parameters;}

}