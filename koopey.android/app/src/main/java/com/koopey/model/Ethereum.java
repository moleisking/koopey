package com.koopey.model;

import android.util.Log;

import com.koopey.model.base.Base;

import org.json.JSONObject;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * Created by Scott on 18/08/2017.
 * https://github.com/ethereum/wiki/wiki/JSON-RPC#eth_getbalance
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Ethereum extends Base {

    public static final String ETHEREUM_FILE_NAME = "ethereum.dat";


    @Builder.Default
     Double balance = 0d;
     String account ;
     String address ;
     String jsonrpc ;
     String result ;
     String from ;
     String to ;
     String data ;
    public String value ;
    public String gas ;
    public String gasPrice ;
    public String nonce ;
    public String type ;

    public Ethereum() {
    }


    public boolean isBalance() {
        return this.type.equals("balance");
    }

    public boolean isTransaction() {
        return this.type.equals("transaction");
    }

    public boolean isEmpty() {
        return this.account.isEmpty();
    }


    public void parseWallet(Wallet wallet) {
        this.account = wallet.getName();
        // this.address = wallet.address;
    }


}
