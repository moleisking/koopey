package com.koopey.api.model.type;

public enum AuthenticationType {
    ADMINISTRATOR("administrator"), ANONYMOUS("anonymous"), ADVERTISER("advertiser"), USER("user"), TESTER("tester");

    public final String type;

    private AuthenticationType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
