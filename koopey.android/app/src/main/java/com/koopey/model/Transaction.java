package com.koopey.model;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.koopey.helper.DateTimeHelper;
import com.koopey.model.base.Base;
import com.koopey.model.type.CurrencyType;
import com.koopey.model.type.TransactionType;

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
    private Location currentLocation;
    private Location source;
    private Location destination;
    private String assetId;
    private String buyerId;
    private String sellerId;
    private String sourceId;
    private String destinationId;
    @Builder.Default
    private String guid = UUID.randomUUID().toString();
    @Builder.Default
    private String secret = "";
    @Builder.Default
    private Users users = new Users();
    @Builder.Default
    private String currency = "eur";
    @Builder.Default
    private String period = "once";
    @Builder.Default
    private Double value = 0.0d;
    @Builder.Default
    private Double total = 0.0d;
    @Builder.Default
    private Integer quantity = 0;
    @Builder.Default
    private Integer grade = 0;
    @Builder.Default
    private long start=0;
    @Builder.Default
    private long end=0;

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
        return currency.isEmpty() || quantity == 0 || value == 0 || total == 0 || getType().isEmpty() || super.isEmpty() ? true : false;
    }


    public boolean isQuote() {
        return getType().equals(TransactionType.Quote);
    }

    public boolean isInvoice() {
        return getType().equals(TransactionType.Invoice);
    }

    public boolean isReceipt() {
       return getType().equals(TransactionType.Receipt);
    }

    public boolean isBitcoin() {
        return currency.equals(CurrencyType.BTC);
    }

    public boolean isEthereum() {
        return currency.equals(CurrencyType.ETH);
    }

    public boolean isFiat() {
        if (this.currency.equals("eur") || this.currency.equals("gbp") || this.currency.equals("usd") || this.currency.equals("zar")) {
            return true;
        } else {
            return false;
        }

    }

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

    public void setCurrentPosition(LatLng latLng, String address) {
        currentLocation.setLongitude( latLng.longitude);
        currentLocation.setLongitude(latLng.latitude);
        currentLocation.address = address;
    }

    public void setStartLocation(LatLng latLng, String address) {
        source.setLongitude(latLng.longitude);
        source.setLatitude (latLng.latitude);
        source.address = address;
    }

    public void setEndLocation(LatLng latLng, String address) {
        destination.setLongitude(latLng.longitude);
        destination.setLatitude (latLng.latitude);
        destination.address = address;
    }

}
