package com.koopey.model.base;

public class Audit extends Base {
    public String name = "";
    public String description = "";
    public String type = "";

    public boolean isEmpty() {
        return  name == null   || name.length() <= 0 || super.isEmpty() ? true : false;
    }
}
