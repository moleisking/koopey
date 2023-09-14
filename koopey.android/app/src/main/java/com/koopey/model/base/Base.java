package com.koopey.model.base;

public class Base {
    public String id = "";

    public boolean isEmpty() {
        return id == null || id.length() <= 0  ? true : false;
    }
}
