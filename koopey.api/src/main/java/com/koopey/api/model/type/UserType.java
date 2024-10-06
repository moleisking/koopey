package com.koopey.api.model.type;

public enum UserType {

    BLACK("black"), BLUE("blue"), BUYER("buyer"), GREEN("green"), GREY("grey"), RECEIVER("receiver"), RED("red"), SELLER("seller"), SENDER("sender"), YELLOW("yellow"), WHITE("white");

    public final String type;

    private UserType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }

}
