package com.koopey.service;

import android.content.Context;
import android.util.Log;
import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.Search;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.impl.IAssetService;


import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AssetService {

    public interface AssetCrudListener {
        void onAssetCreate(int code, String message, String assetId);
        void onAssetDelete(int code, String message);
        void onAssetRead(int code, String message, String assetId);
        void onAssetUpdateAvailable(int code, String message);
        void onAssetUpdate(int code, String message);
    }

    public interface AssetSearchBuyerOrSellerListener {
        void onAssetsByBuyer(Assets assets);
        void onAssetsByBuyerOrSeller(Assets assets);
    }

    public interface AssetSearchSellerListener {
        void onAssetsBySeller(Assets assets);
    }

    public interface AssetSearchListener {
        void onAssetSearch(Assets assets);
    }

    private AuthenticationService authenticationService;
    private Context context;

    private List<AssetService.AssetCrudListener> assetCrudListeners = new ArrayList<>();

    private List<AssetService.AssetSearchListener> assetSearchListeners = new ArrayList<>();

    private List<AssetService.AssetSearchBuyerOrSellerListener> assetSearchBuyerOrSellerListeners = new ArrayList<>();
    private List<AssetService.AssetSearchSellerListener> assetSearchSellerListeners = new ArrayList<>();

    private AuthenticationUser authenticationUser;

    public AssetService(Context context) {
        super();
        this.context = context;
        authenticationService = new AuthenticationService(context);
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
    }

    public Assets getLocalMyAssetsFromFile() {
        Assets assets = new Assets();
        if (SerializeHelper.hasFile(context, Assets.MY_ASSETS_FILE_NAME)) {
            assets = (Assets) SerializeHelper.loadObject(context, Assets.MY_ASSETS_FILE_NAME);
        }
        return assets;
    }

    public Assets getLocalAssetsSearchResultsFromFile() {
        Assets assets = new Assets();
        if (SerializeHelper.hasFile(context, Assets.ASSET_SEARCH_RESULTS_FILE_NAME)) {
            assets = (Assets) SerializeHelper.loadObject(context, Assets.ASSET_SEARCH_RESULTS_FILE_NAME);
        }
        return assets;
    }

    public boolean hasMyAssetsFile() {
        Assets assets = getLocalMyAssetsFromFile();
        return assets.size() <= 0 ? false : true;
    }

    public boolean hasAssetsSearchResultsFile() {
        Assets assets = getLocalAssetsSearchResultsFromFile();
        return assets.size() <= 0 ? false : true;
    }

    public void setOnAssetCrudListener(AssetService.AssetCrudListener listener) {
        assetCrudListeners.add(listener);
    }

    public void setOnAssetSearchListener(AssetService.AssetSearchListener listener) {
        assetSearchListeners.add(listener);
    }

    public void setOnAssetSearchBuyerOrSellerListener(AssetService.AssetSearchBuyerOrSellerListener listener) {
        assetSearchBuyerOrSellerListeners.add(listener);
    }
    public void setOnAssetSearchSellerListener(AssetService.AssetSearchSellerListener listener) {
        assetSearchSellerListeners.add(listener);
    }
    public void readAsset(String assetId) {
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .readAsset(assetId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Asset> call, Response<Asset> response) {
                        Asset asset = response.body();
                        if (asset == null || asset.isEmpty()) {
                            for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                listener.onAssetRead(HttpURLConnection.HTTP_NO_CONTENT, response.message(), assetId);
                            }
                        } else {
                            for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                listener.onAssetRead(HttpURLConnection.HTTP_OK, response.message(), assetId);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Asset> call, Throwable throwable) {
                        for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                            listener.onAssetRead(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchAssetsByBuyer() {
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage() )
                .searchAssetsByBuyer().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Assets> call, Response<Assets> response) {
                        Assets assets = response.body();
                        if (assets == null || assets.isEmpty()) {
                            Log.i(AssetService.class.getName(), "asset is null");
                        } else {
                            for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                                listener.onAssetsByBuyer(assets);
                            }
                            SerializeHelper.saveObject(context, assets);
                            Log.i(AssetService.class.getName(), assets.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Assets> call, Throwable throwable) {
                        for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                            listener.onAssetsByBuyer(null);
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchAssetsByBuyerOrSeller() {
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchAssetsByBuyerOrSeller().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Assets> call, Response<Assets> response) {
                        Assets assets = response.body();
                        if (assets == null || assets.isEmpty()) {
                            Log.i(AssetService.class.getName(), "assets is null");
                        } else {
                            for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                                listener.onAssetsByBuyerOrSeller(assets);
                            }
                            SerializeHelper.saveObject(context, assets);
                            Log.i(AssetService.class.getName(), assets.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Assets> call, Throwable throwable) {
                        for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                            listener.onAssetsByBuyerOrSeller(null);
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchAssetsBySeller() {
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchAssetsBySeller().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Assets> call, Response<Assets> response) {
                        Assets assets = response.body();
                        if (assets == null || assets.isEmpty()) {
                            Log.i(AssetService.class.getName(), "assets is null");
                        } else {
                            for (AssetService.AssetSearchSellerListener listener : assetSearchSellerListeners) {
                                listener.onAssetsBySeller(assets);
                            }
                            SerializeHelper.saveObject(context, assets);
                            Log.i(AssetService.class.getName(), assets.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Assets> call, Throwable throwable) {
                        for (AssetService.AssetSearchSellerListener listener : assetSearchSellerListeners) {
                            listener.onAssetsBySeller(null);
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void updateAssetAvailable(Boolean available) {
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateAssetAvailable(available).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                            listener.onAssetUpdateAvailable(HttpURLConnection.HTTP_OK,"");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                            listener.onAssetUpdateAvailable(HttpURLConnection.HTTP_BAD_REQUEST,throwable.getMessage());
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void createAsset(Asset asset) {
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .createAsset(asset).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String assetId = response.body();
                        if (assetId == null || assetId.isEmpty()) {
                            for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                listener.onAssetCreate(HttpURLConnection.HTTP_NO_CONTENT, assetId == null || assetId.isEmpty() ? "assets is null" : "", assetId);
                            }
                        } else {
                            for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                listener.onAssetCreate(HttpURLConnection.HTTP_OK,  "", assetId);
                            }
                        }

                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                            listener.onAssetCreate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), "");
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void deleteAsset(Asset asset) {
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .deleteAsset(asset).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                            listener.onAssetDelete(HttpURLConnection.HTTP_OK, "");
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                            listener.onAssetDelete(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchAsset(Search search) {
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchAsset(search).enqueue(new Callback<Assets>() {
                    @Override
                    public void onResponse(Call<Assets> call, Response<Assets> response) {
                        Assets assets = response.body();
                            for (AssetService.AssetSearchListener listener : assetSearchListeners) {
                                listener.onAssetSearch(assets);
                            }
                            SerializeHelper.saveObject(context, assets);
                            Log.i(AssetService.class.getName(), String.valueOf( assets.size()));
                    }
                    @Override
                    public void onFailure(Call<Assets> call, Throwable throwable) {
                        for (AssetService.AssetSearchListener listener : assetSearchListeners) {
                            listener.onAssetSearch(null);
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void updateAsset(Asset asset) {
             HttpServiceGenerator
                .createService(IAssetService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateAsset(asset)                .enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                            listener.onAssetUpdate(HttpURLConnection.HTTP_OK, "");
                        }
                    }
                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                            listener.onAssetUpdate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }
}
