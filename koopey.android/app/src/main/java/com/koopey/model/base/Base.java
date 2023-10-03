package com.koopey.model.base;

import com.koopey.helper.DateTimeHelper;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
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
public abstract class Base implements Serializable, Comparator<Base>, Comparable<Base> {

    @Builder.Default
    private String id = UUID.randomUUID().toString();
    private String name;
    private String description;
    private String type;
    @Builder.Default
    private String timeZone = "UTC/GMT";
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

    public String getCreateTimeStampAsString() {
        return DateTimeHelper.epochToString(this.getCreateTimeStamp(), this.getTimeZone());
    }

    public Date getCreateTimeStampAsDate() {
        return DateTimeHelper.epochToDate(this.getCreateTimeStamp());
    }

    public String getReadTimeStampAsString() {
        return DateTimeHelper.epochToString(this.getCreateTimeStamp(), this.getTimeZone());
    }

    public Date getReadTimeStampAsDate() {
        return DateTimeHelper.epochToDate(this.getReadTimeStamp());
    }

    public String getUpdateTimeStampAsString() {
        return DateTimeHelper.epochToString(this.getCreateTimeStamp(), this.getTimeZone());
    }

    public Date getUpdateTimeStampAsDate() {
        return DateTimeHelper.epochToDate(this.getUpdateTimeStamp());
    }

    public String getDeleteTimeStampAsString() {
        return DateTimeHelper.epochToString(this.getCreateTimeStamp(), this.getTimeZone());
    }

    public Date getDeleteTimeStampAsDate() {
        return DateTimeHelper.epochToDate(this.getDeleteTimeStamp());
    }

    public boolean equals(Base b) {
        return this.getId().equals(b.getId()) ? true : false;
    }

    public boolean isEmpty() {
        return id == null || name == null || id.length() <= 0 || name.length() <= 0 ? true : false;
    }
}
