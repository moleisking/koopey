package com.koopey.api.model.type;

public enum MessageType {
    
    RECIEVED("recieved"), SENT("sent");

    public final String type;

    private MessageType(String type) {
        this.type = type;
    }  
    
    @Override
    public String toString() {
        return type;
    }
}
