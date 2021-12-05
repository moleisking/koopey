package com.koopey.api.model.type;

public enum LocationType {
    
    FINANCE("finance"), POSITION("position"), RESIDENCE("residence");

    public final String type;

    private LocationType(String type) {
        this.type = type;
    }    

    @Override
    public String toString() {
        return type;
    }
}
