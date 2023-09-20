package com.koopey.model.base;

import com.koopey.model.Asset;

import java.io.Serializable;
import java.util.Comparator;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@SuperBuilder
public class Base implements Serializable, Comparator<Base>, Comparable<Base> {

    @Builder.Default
    public String id = UUID.randomUUID().toString();
    public String name;
    public String description;
    public String type;

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

    /*public boolean equals(Base base) {
        if (base.id.equals(this.id)) {
            return true;
        } else {
            return false;
        }
    }*/

    public boolean isEmpty() {
        return id == null || name == null || id.length() <= 0 || name.length() <= 0 ? true : false;
    }

   /* @Override
    public int hashCode() {
        return String.join(id, name, description, type).hashCode();
    }*/
}
