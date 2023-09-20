package com.koopey.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.koopey.model.base.Base;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
//import com.koopey.view.RoundImage;
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Event extends Base implements Serializable {
    public static final String EVENT_FILE_NAME = "event.dat";

    public Location location;

    public Users users ;

    @Builder.Default
    public String timeZone = "Etc/UTC";
    public long startTimeStamp;
    public long endTimeStamp;
    @Builder.Default
    public long createTimeStamp = System.currentTimeMillis();
    @Builder.Default
    public long readTimeStamp = 0; //read only
    @Builder.Default
    public long updateTimeStamp = 0; //read only
    @Builder.Default
    public long deleteTimeStamp = 0; //read only



    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public boolean isEmpty() {
        if (this.name.equals("")
                && this.type.equals("")
                && this.users.size() > 0
                && this.startTimeStamp == 0
                && this.endTimeStamp == 0
               ) {
            return true;
        } else {
            return false;
        }
    }

    public String getStartTimeStampAsString() {
        Date date = new Date(this.startTimeStamp);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone(this.timeZone));
        return format.format(date);
    }

    public Date getStartTimeStampAsDate() {
        return new Date(this.startTimeStamp);
    }

    public String getEndTimeStampAsString() {
        Date date = new Date(this.endTimeStamp);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone(this.timeZone));
        return format.format(date);
    }

    public Date getEndTimeStampAsDate() {
        return new Date(this.endTimeStamp);
    }

    public long getRemainingHours() {
        long now = System.currentTimeMillis();
        if (now < this.endTimeStamp) {
            return TimeUnit.MICROSECONDS.toHours(now - this.endTimeStamp);
        } else {
            return 0;
        }
    }

    public long getRemainingDays() {
        long now = System.currentTimeMillis();
        if (now < this.endTimeStamp) {
            return TimeUnit.MICROSECONDS.toDays(this.endTimeStamp - this.startTimeStamp);
        } else {
            return 0;
        }
    }

    public String getCreateTimeStampAsString() {
        Date date = new Date(this.createTimeStamp);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone(this.timeZone));
        return format.format(date);
    }

    public Date getCreateTimeStampAsDate() {
        return new Date(this.createTimeStamp);
    }

    public String getReadTimeStampAsString() {
        Date date = new Date(this.readTimeStamp);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone(this.timeZone));
        return format.format(date);
    }

    public Date getReadTimeStampAsDate() {
        return new Date(this.readTimeStamp);
    }

    public String getUpdateTimeStampAsString() {
        Date date = new Date(this.updateTimeStamp);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone(this.timeZone));
        return format.format(date);
    }

    public Date getUpdateTimeStampAsDate() {
        return new Date(this.updateTimeStamp);
    }

    public String getDeleteTimeStampAsString() {
        Date date = new Date(this.deleteTimeStamp);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone(this.timeZone));
        return format.format(date);
    }

    public Date getDeleteTimeStampAsDate() {
        return new Date(this.deleteTimeStamp);
    }

    public void setLocation(LatLng latLng, String address) {
       // Location. this.location;
        this.location.longitude = latLng.longitude;
        this.location.latitude = latLng.latitude;
        this.location.address = address;
    }
}
