package com.koopey.model;



import com.koopey.model.base.Base;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * Created by Scott on 18/08/2017.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Bitcoin extends Base {

    public static final String BITCOIN_FILE_NAME = "bitcoin.dat";

    String account ;
     String address ;
     String asm ;
    String fromaccount ;
     String fromaddress ;
     String hash;
     String hex ;
     String pubkey ;
     String scriptPubKey ;
    String toaddress ;
     String txid ;
    String type ;
    @Builder.Default
     Double amount = 0d;
    @Builder.Default
     int confirmations = 0;
    @Builder.Default
     int timestamp = 0;
    @Builder.Default
     int vout = 0;
    @Builder.Default
     int version = 0;
    @Builder.Default
     boolean complete = true;
    @Builder.Default
     boolean iscompressed = false;
    @Builder.Default
   boolean isvalid = false;
    @Builder.Default
     boolean ismine = false;
    @Builder.Default
     boolean iswatchonly = false;
    @Builder.Default
     boolean isscript = false;
    @Builder.Default
     boolean spendable = true;
    @Builder.Default
     boolean solvable = true;

    public Bitcoin() {
    }

    public boolean isBalance() {
        return this.type.equals("balance");
    }

    public boolean isTransaction() {
        return this.type.equals("transaction");
    }


    public boolean isEmpty() {
        return (this.account.isEmpty() && this.address.isEmpty()) ? true : false;
    }

    public void parseWallet(Wallet wallet) {
        this.address = wallet.getName();
    }

}
