package com.koopey.api.model.type;

public enum MeasurementType {
    
    IMPERIAL("imperial"), METRIC("metric");

    public final String type;

    private MeasurementType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
