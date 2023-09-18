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

/**
 * Created by Scott on 18/01/2017.
 */
public class Transaction extends Base {


    public static final String TRANSACTION_FILE_NAME = "transaction.dat";

    public Location currentLocation;
    public Location startLocation;
    public Location endLocation;
    public String id = UUID.randomUUID().toString();
    public String guid = UUID.randomUUID().toString();
    public String secret = "";

    public Users users = new Users();

    public String reference = "";

    public String state = "quote";
    public String currency = "eur";
    public String timeZone = "Etc/UTC";
    public String period = "once";
    public Double itemValue = 0.0d;
    public Double totalValue = 0.0d;
    public Integer quantity = 0;
    public long startTimeStamp;
    public long endTimeStamp;
    public long createTimeStamp = System.currentTimeMillis();
    public long readTimeStamp = 0; //read only
    public long updateTimeStamp = 0; //read only
    public long deleteTimeStamp = 0; //read only

    public Transaction() {
    }

    public int countBuyers() {
        int counter = 0;
        for (int i = 0; i <= this.users.size(); i++) {
            User user = this.users.get(i);
            if (user.type == "buyer") {
                counter++;
            }
        }
        return counter;
    }

    public int countSellers() {
        int counter = 0;
        for (int i = 0; i <= users.size(); i++) {
            User user = this.users.get(i);
            if (user.type == "seller") {
                counter++;
            }
        }
        return counter;
    }

    public boolean isEmpty() {
        //Note* userid is also passed in token so userId check is not necessary
        if (this.name.equals("")
                && this.type.equals("")
                && this.currency.equals("")
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
            if (user.id.equals(authUser.id) && user.type.equals("seller")) {
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
            if (user.id.equals(authUser.id) && user.type.equals("buyer")) {
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
        this.currentLocation.longitude = latLng.longitude;
        this.currentLocation.latitude = latLng.latitude;
        this.currentLocation.address = address;
    }

    public void setStartLocation(LatLng latLng, String address) {
        this.startLocation.longitude = latLng.longitude;
        this.startLocation.latitude = latLng.latitude;
        this.startLocation.address = address;
    }

    public void setEndLocation(LatLng latLng, String address) {
        this.endLocation.longitude = latLng.longitude;
        this.endLocation.latitude = latLng.latitude;
        this.endLocation.address = address;
    }

}
