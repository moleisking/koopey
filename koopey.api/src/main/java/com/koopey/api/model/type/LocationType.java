package com.koopey.api.model.type;

public enum LocationType {
    
    ABODE("abode"), BANK("bank"), DELIVERY("delivery"), PAST("past"), PRESENT("present");

    public final String type;

    private LocationType(String type) {
        this.type = type;
    }    
}
