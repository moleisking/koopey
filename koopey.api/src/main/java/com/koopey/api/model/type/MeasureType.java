package com.koopey.api.model.type;

public enum MeasureType {
    
    IMPERIAL("imperial"), METRIC("metric");

    public final String type;

    private MeasureType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
