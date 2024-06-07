package com.koopey.api.model.type;

public enum MessageType {
    
    RECAD("read"), SENT("sent"), FAIL("fail");

    public final String type;

    private MessageType(String type) {
        this.type = type;
    }  
    
    @Override
    public String toString() {
        return type;
    }
}
