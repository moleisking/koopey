package com.koopey.api.model.type;

public enum TransactionType {
    
    INVOICE("invoice"), QUOTE("quote"), RECEIPT("receipt");

    public final String type;

    private TransactionType(String type) {
        this.type = type;
    }
}
