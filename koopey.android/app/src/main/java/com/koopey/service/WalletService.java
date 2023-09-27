package com.koopey.service;

import com.koopey.model.Wallets;

public class WalletService {

    public interface WalletCrudListener {
        void onWalletCreate(int code, String message, String locationId);
        void onWalletDelete(int code, String message);
        void onWalletRead(int code, String message, String locationId);
        void onWalletUpdate(int code, String message);
    }

    public interface WalletSearchListener {
        void onWalletSearch(Wallets wallets);
    }
}
