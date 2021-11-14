package com.koopey.api.model.type;

public enum CurrencyType {

    BITCOIN("btc"), SWISS_FRANK("chf"), RENMINBI("cny"), ETHEREUM("eth"), EURO("eur"), BRITISH_POUND("gbp"),
    JAPANESE_YEN("jpy"), UNITED_STATES_DOLLAR("usd"), SOUTH_AFRICAN_RAND("zar"), LOCAL("tok");

    public final String type;

    private CurrencyType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
