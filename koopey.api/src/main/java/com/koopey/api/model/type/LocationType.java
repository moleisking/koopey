package com.koopey.api.model.type;

public enum LocationType {
    
    INVOICE("Invoice"), DESTINATION("destination"), POSITION("position"), RESIDENCE("residence"),  SOURCE("source");

    public final String type;

    private LocationType(String type) {
        this.type = type;
    }    

    @Override
    public String toString() {
        return type;
    }
}
