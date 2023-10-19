package com.koopey.model;

import com.koopey.helper.DateTimeHelper;
import com.koopey.model.base.Base;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Advert extends Base {
    public static final String ADVERT_FILE_NAME = "advert.dat";

    @Builder.Default
    private long start = 0;
    @Builder.Default
    private long end = 0; //read only


    public String getStartAsString() {
        return   DateTimeHelper.epochToString(this.getStart(), this.getTimeZone());
    }

    public Date getStartAsDate() {
        return new Date(this.start);
    }

    public String getEndAsString() {
        return   DateTimeHelper.epochToString(this.getEnd(), this.getTimeZone());
    }

    public Date getEndAsDate() {
        return new Date(this.end);
    }

    public long getRemainingHours() {
        long now = System.currentTimeMillis();
        if (now < this.end) {
            return TimeUnit.MICROSECONDS.toHours(now - this.end);
        } else {
            return 0;
        }
    }

    public long getRemainingDays() {
        long now = System.currentTimeMillis();
        if (now < this.end) {
            return TimeUnit.MICROSECONDS.toDays(this.end - this.start);
        } else {
            return 0;
        }
    }

}
