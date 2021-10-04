package com.koopey.api.model.type;

public enum TransactionType {
    
    CASH_ON_DELIVERY("cashOnDelivery"), DIRECT_TRANSFER("directTransfer"), CROWED_FUND("crowedFund");

    public final String type;

    private TransactionType(String type) {
        this.type = type;
    }
}
