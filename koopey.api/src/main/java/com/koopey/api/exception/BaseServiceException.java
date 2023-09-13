package com.koopey.api.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BaseServiceException extends RuntimeException {

     public BaseServiceException (String message){
        super(message);
        log.info("KoopeyException {}", message); 
    }
}