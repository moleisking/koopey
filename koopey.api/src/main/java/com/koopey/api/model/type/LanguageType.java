package com.koopey.api.model.type;

public enum LanguageType {

    CHINES("cn"), DUTCH("nl"), ENGLISH("en"), FRENCH("fr"), ITALIAN("it"), GERMAN("de"), PORTUGUESE("pt"),
    SPANISH("es");

    public final String type;

    private LanguageType(String type) {
        this.type = type;
    }    

}
