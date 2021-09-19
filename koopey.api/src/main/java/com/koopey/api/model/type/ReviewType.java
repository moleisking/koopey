package com.koopey.api.model.type;

public enum ReviewType {

    COMMENT("comment"), STAR("star"), THUMB("thumb");

    public final String type;

    private ReviewType(String type) {
        this.type = type;
    }
}
