package com.koopey.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.koopey.model.base.Base;

import org.json.JSONArray;
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

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Transaction extends Base {
    public static final String TRANSACTION_FILE_NAME = "transaction.dat";

    Location currentLocation;
    Location startLocation;
    Location endLocation;

    String buyerId;
    String sellerId;
    String sourceId;
    String destinationId;
    @Builder.Default
    String guid = UUID.randomUUID().toString();
    @Builder.Default
    String secret = "";
    @Builder.Default
    Users users = new Users();

    String reference ;

    @Builder.Default
    String state = "quote";
    @Builder.Default
    String currency = "eur";
    @Builder.Default
    String timeZone = "Etc/UTC";
    @Builder.Default
    String period = "once";
    @Builder.Default
    Double itemValue = 0.0d;
    @Builder.Default
    Double totalValue = 0.0d;
    @Builder.Default
    Integer quantity = 0;
    long startTimeStamp;
    long endTimeStamp;



    public int countBuyers() {
        int counter = 0;
        for (int i = 0; i <= this.users.size(); i++) {
            User user = this.users.get(i);
            if (user.getType() == "buyer") {
                counter++;
            }
        }
        return counter;
    }

    public int countSellers() {
        int counter = 0;
        for (int i = 0; i <= users.size(); i++) {
            User user = this.users.get(i);
            if (user.getType() == "seller") {
                counter++;
            }
        }
        return counter;
    }

    public boolean isEmpty() {
        //Note* userid is also passed in token so userId check is not necessary
        if (this.isEmpty()                && this.currency.equals("")
                && this.quantity == 0
                && this.itemValue == 0
                && this.totalValue == 0
                && (!isQuote() || !isInvoice() || !isReceipt())) {
            return true;
        } else {
            return false;
        }
    }


    public boolean isQuote() {
        return this.state.equals("quote");
    }

    public boolean isInvoice() {
        return this.state.equals("invoice");
    }

    public boolean isReceipt() {
        return this.state.equals("receipt");
    }

    public boolean isBitcoin() {
        return this.currency.equals("btc");
    }

    public boolean isEthereum() {
        return this.currency.equals("eth");
    }

    public boolean isFiat() {
        if (this.currency.equals("eur") || this.currency.equals("gbp") || this.currency.equals("usd") || this.currency.equals("zar")) {
            return true;
        } else {
            return false;
        }

    }

    public boolean isSeller(User authUser) {
        boolean result = false;
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            if (user.getId().equals(authUser.getId()) && user.getType().equals("seller")) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean isBuyer(User authUser) {
        boolean result = false;
        for (int i = 0; i < this.users.size(); i++) {
            User user = users.get(i);
            if (user.getId().equals(authUser.getId()) && user.getType().equals("buyer")) {
                result = true;
                break;
            }
        }
        return result;
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

    public void setCurrentPosition(LatLng latLng, String address) {
        this.currentLocation.setLongitude( latLng.longitude);
        this.currentLocation.setLongitude(latLng.latitude);
        this.currentLocation.address = address;
    }

    public void setStartLocation(LatLng latLng, String address) {
        this.startLocation.setLongitude(latLng.longitude);
        this.startLocation.setLatitude (latLng.latitude);
        this.startLocation.address = address;
    }

    public void setEndLocation(LatLng latLng, String address) {
        this.endLocation.setLongitude(latLng.longitude);
        this.endLocation.setLatitude (latLng.latitude);
        this.endLocation.address = address;
    }

}
