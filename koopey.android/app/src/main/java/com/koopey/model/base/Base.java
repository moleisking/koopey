package com.koopey.model.base;

import com.koopey.model.Asset;

import java.io.Serializable;
import java.util.Comparator;

public class Base implements Serializable, Comparator<Base>, Comparable<Base> {

    public String id = "";
    public String name = "";
    public String description = "";
    public String type = "";

    @Override
    public int compare(Base a, Base b) {
        if (a.hashCode() < b.hashCode()) {
            return -1;
        } else if (a.hashCode() > b.hashCode()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int compareTo(Base o) {
        return compare(this, o);
    }

    public boolean equals(Base base) {
        if (base.id.equals(this.id)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEmpty() {
        return id == null || name == null || id.length() <= 0 || name.length() <= 0 ? true : false;
    }
}
