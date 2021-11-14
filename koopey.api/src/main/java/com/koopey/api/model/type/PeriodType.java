package com.koopey.api.model.type;

public enum PeriodType {
    
    ONCE("once"), HOUR("hour"), DAY("day"), WEEK("week"), MONTH("month");

    public final String type;

    private PeriodType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
