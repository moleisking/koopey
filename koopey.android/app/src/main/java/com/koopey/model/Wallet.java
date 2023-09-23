package com.koopey.model;

import com.koopey.model.base.Base;

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
    private String ownerId;
    @Builder.Default
    private Double latitude = 0.0d;
    @Builder.Default
    private Double longitude = 0.0d;
    @Builder.Default
    private Double value = 0.0d;
    @Builder.Default
    private String currency = "eur";
    private String identifier;

    public void setValueAsString(String value) {
        this.value = Double.valueOf(value);
    }

    public String getValueAsString() {
        return this.value.toString();
    }

    public boolean isCryptocurrencyEmpty() {
        if (!this.getName().equals("") && !this.currency.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}