package com.koopey.api.model.type;

public enum UserType {
    
    BLUE("blue"), BUYER("buyer"), GREEN("green"), GREY("grey"), RECEIVER("receiver"), SELLER("seller"), SENDER("sender"), YELLOW("yellow");

    public final String type;

    private UserType(String type) {
        this.type = type;
    }

}
