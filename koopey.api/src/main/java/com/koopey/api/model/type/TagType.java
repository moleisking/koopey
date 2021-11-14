package com.koopey.api.model.type;

public enum TagType {

    ADULT("adult"), NORMAL("normal"), POPULAR("popular");

    public final String type;

    private TagType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

}
