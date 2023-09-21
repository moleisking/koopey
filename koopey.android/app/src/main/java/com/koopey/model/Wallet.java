package com.koopey.model;

import android.util.Log;

import com.koopey.model.base.Base;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Comparator;
import java.util.UUID;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Wallet extends Base {

    public static final String WALLET_FILE_NAME = "wallet.dat";
String ownerId;
    @Builder.Default
     Double latitude = 0.0d;
    @Builder.Default
     Double longitude = 0.0d;
    @Builder.Default
     Double value = 0.0d;
    @Builder.Default
     String currency = "eur";
     String identifier ;

    public boolean isEmpty() {
        return this.currency.equals("");
    }

    public boolean isCryptocurrencyEmpty() {
        if (!this.getName().equals("") && !this.currency.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}