package com.koopey.model;

import com.koopey.model.base.Base;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Bitcoin extends Base {

    public static final String BITCOIN_FILE_NAME = "bitcoin.dat";
    private String account;
    private String address;
    private String asm;
    private String fromaccount;
    private String fromaddress;
    private String hash;
    private String hex;
    private String pubkey;
    private String scriptPubKey;
    private String toaddress;
    private String txid;

    @Builder.Default
    private Double amount = 0d;
    @Builder.Default
    private int confirmations = 0;
    @Builder.Default
    private int timestamp = 0;
    @Builder.Default
    private int vout = 0;
    @Builder.Default
    private int version = 0;
    @Builder.Default
    private boolean complete = true;
    @Builder.Default
    private boolean iscompressed = false;
    @Builder.Default
    private boolean isvalid = false;
    @Builder.Default
    private boolean ismine = false;
    @Builder.Default
    private boolean iswatchonly = false;
    @Builder.Default
    private boolean isscript = false;
    @Builder.Default
    private boolean spendable = true;
    @Builder.Default
    private boolean solvable = true;

    public Bitcoin() {
    }

    public boolean isBalance() {
        return this.getType().equals("balance");
    }

    public boolean isTransaction() {
        return this.getType().equals("transaction");
    }


    public boolean isEmpty() {
        return (this.account.isEmpty() && this.address.isEmpty()) ? true : false;
    }

    public void parseWallet(Wallet wallet) {
        this.address = wallet.getName();
    }

}
