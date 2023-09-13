package com.koopey.model.base;

public class Audit extends Base {
    public String name = "";
    public String description = "";
    public String type = "";

    public boolean isEmpty() {
        return id == null || name == null  || id.length() <= 0 || name.length() <= 0  ? true : false;
    }
}
