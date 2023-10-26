package com.koopey.service;

import android.content.Context;
import android.util.Log;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Search;
import com.koopey.model.Wallet;
import com.koopey.model.Wallets;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.impl.IWalletService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WalletService {

    public interface WalletCrudListener {
        void onWalletCreate(int code, String message, String walletId);

        void onWalletDelete(int code, String message);

        void onWalletRead(int code, String message, Wallet wallet);

        void onWalletUpdate(int code, String message);
    }

    public interface WalletSearchListener {
        void onWalletSearch(int code, String message, Wallets wallets);
    }

    private AuthenticationService authenticationService;
    private AuthenticationUser authenticationUser;
    private Context context;

    private List<WalletService.WalletCrudListener> walletCrudListeners = new ArrayList<>();
    private List<WalletService.WalletSearchListener> walletSearchListeners = new ArrayList<>();

    public WalletService(Context context) {
        super();
        this.context = context;
        authenticationService = new AuthenticationService(context);
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
    }

    public void setOnWalletCrudListener(WalletService.WalletCrudListener walletCrudListener) {
        walletCrudListeners.add(walletCrudListener);
    }

    public void setOnWalletSearchListener(WalletService.WalletSearchListener walletSearchListener) {
        walletSearchListeners.add(walletSearchListener);
    }

    public Wallets getLocalWalletsFromFile() {
        Wallets wallets = new Wallets();
        if (SerializeHelper.hasFile(context, Wallets.WALLETS_FILE_NAME)) {
            wallets = (Wallets) SerializeHelper.loadObject(context, Wallets.WALLETS_FILE_NAME);
        }
        return wallets;
    }

    public boolean hasWalletsFile() {
        Wallets wallets = getLocalWalletsFromFile();
        return wallets.size() <= 0 ? false : true;
    }

    public void create(Wallet wallet) {
        HttpServiceGenerator.createService(IWalletService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .create(wallet).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String walletId = response.body();
                        for (WalletService.WalletCrudListener listener : walletCrudListeners) {
                            listener.onWalletCreate(HttpURLConnection.HTTP_OK, "", walletId);
                        }
                        SerializeHelper.saveObject(context, wallet);
                        Log.d(WalletService.class.getName(), wallet.toString());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        for (WalletService.WalletCrudListener listener : walletCrudListeners) {
                            listener.onWalletCreate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(WalletService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void delete(Wallet wallet) {
        HttpServiceGenerator.createService(IWalletService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .delete(wallet).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (WalletService.WalletCrudListener listener : walletCrudListeners) {
                            listener.onWalletDelete(HttpURLConnection.HTTP_OK, "");
                        }
                        SerializeHelper.deleteObject(context, Wallet.WALLET_FILE_NAME);
                        Log.d(WalletService.class.getName(), wallet.toString());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (WalletService.WalletCrudListener listener : walletCrudListeners) {
                            listener.onWalletDelete(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(WalletService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void read(String walletId) {
        HttpServiceGenerator.createService(IWalletService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .read(walletId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Wallet> call, Response<Wallet> response) {
                        Wallet wallet = response.body();
                        if (wallet == null || wallet.isEmpty()) {
                            for (WalletService.WalletCrudListener listener : walletCrudListeners) {
                                listener.onWalletRead(HttpURLConnection.HTTP_NO_CONTENT, "", null);
                            }
                            Log.i(TransactionService.class.getName(), "Wallet is null");
                        } else {
                            for (WalletService.WalletCrudListener listener : walletCrudListeners) {
                                listener.onWalletRead(HttpURLConnection.HTTP_OK, "", wallet);
                            }
                            SerializeHelper.saveObject(context, wallet);
                            Log.i(TransactionService.class.getName(), wallet.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Wallet> call, Throwable throwable) {
                        for (WalletService.WalletCrudListener listener : walletCrudListeners) {
                            listener.onWalletRead(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(TransactionService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void search() {
        HttpServiceGenerator.createService(IWalletService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .search().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Wallets> call, Response<Wallets> response) {
                        Wallets wallets = response.body();
                        if (wallets == null || wallets.isEmpty()) {
                            for (WalletService.WalletSearchListener listener : walletSearchListeners) {
                                listener.onWalletSearch(HttpURLConnection.HTTP_NO_CONTENT, "", new Wallets());
                            }
                            Log.i(WalletService.class.getName(), "wallet is null");
                        } else {
                            for (WalletService.WalletSearchListener listener : walletSearchListeners) {
                                listener.onWalletSearch(HttpURLConnection.HTTP_OK, "", wallets);
                            }
                            SerializeHelper.saveObject(context, wallets);
                            Log.i(WalletService.class.getName(), wallets.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Wallets> call, Throwable throwable) {
                        for (WalletService.WalletSearchListener listener : walletSearchListeners) {
                            listener.onWalletSearch(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(WalletService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void update(Wallet wallet) {
        HttpServiceGenerator.createService(IWalletService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .delete(wallet).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (WalletService.WalletCrudListener listener : walletCrudListeners) {
                            listener.onWalletUpdate(HttpURLConnection.HTTP_OK, "");
                        }
                        SerializeHelper.deleteObject(context, Wallet.WALLET_FILE_NAME);
                        Log.d(WalletService.class.getName(), wallet.toString());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (WalletService.WalletCrudListener listener : walletCrudListeners) {
                            listener.onWalletUpdate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(WalletService.class.getName(), throwable.getMessage());
                    }
                });
    }
}
