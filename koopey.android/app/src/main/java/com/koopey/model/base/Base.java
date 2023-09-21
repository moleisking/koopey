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
    private String id = UUID.randomUUID().toString();
    private String name;
    private String description;
    private String type;
    @Builder.Default
    public long createTimeStamp = 0;
    @Builder.Default
    public long readTimeStamp = 0;
    @Builder.Default
    public long updateTimeStamp = 0;
    @Builder.Default
    public long deleteTimeStamp = 0;

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

    public boolean isEmpty() {
        return id == null || name == null || id.length() <= 0 || name.length() <= 0 ? true : false;
    }
}
