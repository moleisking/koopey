package com.koopey.api.model.type;

public enum TransactionType {
    
    INVOICE("invoice"), QUOTE("quote"), RECEIPT("receipt"), TEMPLATE("template");

    public final String type;

    private TransactionType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
