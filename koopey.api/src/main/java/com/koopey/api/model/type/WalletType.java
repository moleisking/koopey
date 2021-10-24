package com.koopey.api.model.type;

public enum WalletType {
    
    BITCOIN("bitcoin"), ETHEREUM("ethereum"), IBAN("iban"), TOKO("toko");

    public final String type;

    private WalletType(String type) {
        this.type = type;
    }
}
