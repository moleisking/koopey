package com.koopey.service;

import android.content.Context;
import android.util.Log;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.Search;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.type.AssetType;
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
        void onAssetsByBuyer(int code, String message, Assets assets);

        void onAssetsByBuyerOrSeller(int code, String message, Assets assets);
    }

    public interface AssetSearchSellerListener {
        void onAssetsBySeller(int code, String message, Assets assets);
    }

    public interface AssetSearchListener {
        void onAssetSearch(int code, String message, Assets assets);
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

    public void create(Asset asset) {
        final String methodName = AssetService.class.getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName();
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .create(asset).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String assetId = response.body();
                        switch (response.code()) {
                            case HttpURLConnection.HTTP_NO_CONTENT:
                                break;
                            case HttpURLConnection.HTTP_OK:
                                break;
                            case HttpURLConnection.HTTP_UNAUTHORIZED:
                                break;
                            default:
                        }
                        if (assetId == null || assetId.isEmpty()) {
                            for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                listener.onAssetCreate(HttpURLConnection.HTTP_NO_CONTENT, assetId == null || assetId.isEmpty() ? "assets is null" : "", assetId);
                            }
                        } else {
                            for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                listener.onAssetCreate(HttpURLConnection.HTTP_OK, "", assetId);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                            listener.onAssetCreate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), "");
                        }
                        Log.e(AssetService.class.getName(), AssetService.class.getEnclosingMethod().getName() + " " +throwable.getMessage());
                    }
                });
    }

    public void delete(Asset asset) {
        final String methodName = AssetService.class.getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName();
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .delete(asset).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        switch (response.code()) {
                            case HttpURLConnection.HTTP_OK:
                                for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                    listener.onAssetDelete(HttpURLConnection.HTTP_OK, "");
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_OK));
                                break;
                            case HttpURLConnection.HTTP_UNAUTHORIZED:
                                for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                    listener.onAssetDelete(HttpURLConnection.HTTP_UNAUTHORIZED, "");
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_UNAUTHORIZED));
                                break;
                            default:
                                for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                    listener.onAssetDelete(HttpURLConnection.HTTP_GONE, "");
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_GONE));
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                            listener.onAssetDelete(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(AssetService.class.getName(), AssetService.class.getEnclosingMethod().getName() + " " + throwable.getMessage());
                    }
                });
    }

    public void read(String assetId) {
        final String methodName = AssetService.class.getName() + "." + AssetService.class.getEnclosingMethod().getName();
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .read(assetId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Asset> call, Response<Asset> response) {
                        Asset asset = response.body();
                        switch (response.code()) {
                            case HttpURLConnection.HTTP_NO_CONTENT:
                                for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                    listener.onAssetRead(HttpURLConnection.HTTP_NO_CONTENT, response.message(), assetId);
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_NO_CONTENT));
                                break;
                            case HttpURLConnection.HTTP_OK:
                                for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                    listener.onAssetRead(HttpURLConnection.HTTP_OK, response.message(), assetId);
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_OK));
                                break;
                            case HttpURLConnection.HTTP_UNAUTHORIZED:
                                for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                    listener.onAssetRead(HttpURLConnection.HTTP_UNAUTHORIZED, response.message(), assetId);
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_UNAUTHORIZED));
                                break;
                            default:
                                for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                    listener.onAssetRead(HttpURLConnection.HTTP_GONE, response.message(), assetId);
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_GONE));
                        }
                    }

                    @Override
                    public void onFailure(Call<Asset> call, Throwable throwable) {
                        for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                            listener.onAssetRead(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(methodName, throwable.getMessage());
                    }
                });
    }

    public void search(Search search) {
        final String methodName = AssetService.class.getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName();
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .search(search).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Assets> call, Response<Assets> response) {
                        Assets assets = response.body();
                        switch (response.code()) {
                            case HttpURLConnection.HTTP_BAD_REQUEST:
                                for (AssetService.AssetSearchListener listener : assetSearchListeners) {
                                    listener.onAssetSearch(HttpURLConnection.HTTP_BAD_REQUEST, "", new Assets());
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST));
                                break;
                            case HttpURLConnection.HTTP_OK:
                                for (AssetService.AssetSearchListener listener : assetSearchListeners) {
                                    listener.onAssetSearch(HttpURLConnection.HTTP_OK, "", assets);
                                }
                                SerializeHelper.saveObject(context, assets);
                                Log.d(methodName,  assets.toString());
                                break;
                            case HttpURLConnection.HTTP_NO_CONTENT:
                                for (AssetService.AssetSearchListener listener : assetSearchListeners) {
                                    listener.onAssetSearch(HttpURLConnection.HTTP_NO_CONTENT, "", new Assets());
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_NO_CONTENT));
                                break;
                            case HttpURLConnection.HTTP_UNAUTHORIZED:
                                for (AssetService.AssetSearchListener listener : assetSearchListeners) {
                                    listener.onAssetSearch(HttpURLConnection.HTTP_UNAUTHORIZED, "", assets);
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_UNAUTHORIZED));
                                break;
                            default:
                                for (AssetService.AssetSearchListener listener : assetSearchListeners) {
                                    listener.onAssetSearch(HttpURLConnection.HTTP_GONE, "", new Assets());
                                }
                                Log.d(methodName, String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Assets> call, Throwable throwable) {
                        for (AssetService.AssetSearchListener listener : assetSearchListeners) {
                            listener.onAssetSearch(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), new Assets());
                        }
                        Log.e(methodName,  throwable.getMessage());
                    }
                });
    }

    public void searchByBuyer() {
        final String methodName = AssetService.class.getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName();
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByBuyer().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Assets> call, Response<Assets> response) {
                        Assets assets = response.body();
                        switch (response.code()) {
                            case HttpURLConnection.HTTP_NO_CONTENT:
                                for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                                    listener.onAssetsByBuyer(HttpURLConnection.HTTP_NO_CONTENT, "", new Assets());
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_NO_CONTENT));
                                break;
                            case HttpURLConnection.HTTP_OK:
                                for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                                    listener.onAssetsByBuyer(HttpURLConnection.HTTP_OK, "", assets);
                                }
                                SerializeHelper.saveObject(context, assets);
                                Log.d(methodName, assets.toString());
                                break;
                            case HttpURLConnection.HTTP_UNAUTHORIZED:
                                for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                                    listener.onAssetsByBuyer(HttpURLConnection.HTTP_UNAUTHORIZED, "", new Assets());
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_UNAUTHORIZED));
                                break;
                            default:
                                for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                                    listener.onAssetsByBuyer(HttpURLConnection.HTTP_GONE, "", new Assets());
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_GONE));
                        }
                    }

                    @Override
                    public void onFailure(Call<Assets> call, Throwable throwable) {
                        for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                            listener.onAssetsByBuyer(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchByBuyerOrSeller() {
        final String methodName = AssetService.class.getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName();
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByBuyerOrSeller().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Assets> call, Response<Assets> response) {
                        Assets assets = response.body();
                        switch (response.code()) {
                            case HttpURLConnection.HTTP_BAD_REQUEST:
                                for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                                    listener.onAssetsByBuyerOrSeller(HttpURLConnection.HTTP_BAD_REQUEST, "", new Assets());
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_BAD_REQUEST));
                                break;
                            case HttpURLConnection.HTTP_OK:
                                for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                                    listener.onAssetsByBuyerOrSeller(HttpURLConnection.HTTP_OK, "", assets);
                                }
                                SerializeHelper.saveObject(context, assets);
                                Log.d(methodName,  assets.toString());
                                break;
                            case HttpURLConnection.HTTP_NO_CONTENT:
                                for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                                    listener.onAssetsByBuyerOrSeller(HttpURLConnection.HTTP_NO_CONTENT, "", new Assets());
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_NO_CONTENT));
                                break;
                            case HttpURLConnection.HTTP_UNAUTHORIZED:
                                for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                                    listener.onAssetsByBuyerOrSeller(HttpURLConnection.HTTP_UNAUTHORIZED, "", assets);
                                }
                                Log.d(methodName,  String.valueOf(HttpURLConnection.HTTP_UNAUTHORIZED));
                                break;
                            default:
                                for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                                    listener.onAssetsByBuyerOrSeller(HttpURLConnection.HTTP_GONE, "", new Assets());
                                }
                                Log.d(methodName, String.valueOf(response.code()));
                        }
                    }

                    @Override
                    public void onFailure(Call<Assets> call, Throwable throwable) {
                        String errorMessage = throwable.getMessage() != null ? throwable.getMessage() : "";
                        for (AssetService.AssetSearchBuyerOrSellerListener listener : assetSearchBuyerOrSellerListeners) {
                            listener.onAssetsByBuyerOrSeller(HttpURLConnection.HTTP_BAD_REQUEST, errorMessage, null);
                        }
                        Log.e(methodName, throwable.getMessage());
                    }

                });
    }

    public void searchBySeller() {
        final String methodName = AssetService.class.getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName();
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchBySeller().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Assets> call, Response<Assets> response) {
                        Assets assets = response.body();
                        switch (response.code()) {
                            case HttpURLConnection.HTTP_NO_CONTENT:
                                for (AssetService.AssetSearchSellerListener listener : assetSearchSellerListeners) {
                                    listener.onAssetsBySeller(HttpURLConnection.HTTP_NO_CONTENT, "", new Assets());
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_NO_CONTENT));
                                break;
                            case HttpURLConnection.HTTP_OK:
                                for (AssetService.AssetSearchSellerListener listener : assetSearchSellerListeners) {
                                    listener.onAssetsBySeller(HttpURLConnection.HTTP_OK, "", assets);
                                }
                                SerializeHelper.saveObject(context, assets);
                                Log.d(methodName, assets.toString());
                                break;
                            case HttpURLConnection.HTTP_UNAUTHORIZED:
                                for (AssetService.AssetSearchSellerListener listener : assetSearchSellerListeners) {
                                    listener.onAssetsBySeller(HttpURLConnection.HTTP_UNAUTHORIZED, "", new Assets());
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_UNAUTHORIZED));
                                break;
                            default:
                                for (AssetService.AssetSearchSellerListener listener : assetSearchSellerListeners) {
                                    listener.onAssetsBySeller(HttpURLConnection.HTTP_GONE, "", new Assets());
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_GONE));
                        }
                    }

                    @Override
                    public void onFailure(Call<Assets> call, Throwable throwable) {
                        for (AssetService.AssetSearchSellerListener listener : assetSearchSellerListeners) {
                            listener.onAssetsBySeller(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), new Assets());
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void update(Asset asset) {
        final String methodName = AssetService.class.getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName();
        HttpServiceGenerator
                .createService(IAssetService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .update(asset).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        switch (response.code()) {
                            case HttpURLConnection.HTTP_OK:
                                for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                    listener.onAssetUpdate(HttpURLConnection.HTTP_OK, "");
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_OK));
                                break;
                            case HttpURLConnection.HTTP_UNAUTHORIZED:
                                for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                    listener.onAssetUpdate(HttpURLConnection.HTTP_UNAUTHORIZED, "");
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_UNAUTHORIZED));
                                break;
                            default:
                                for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                    listener.onAssetUpdate(HttpURLConnection.HTTP_GONE, "");
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_GONE));
                        }

                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                            listener.onAssetUpdate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(AssetService.class.getName(), AssetService.class.getEnclosingMethod().getName() + " " + throwable.getMessage());
                    }
                });
    }

    public void updateAvailable(Boolean available) {
        final String methodName = AssetService.class.getName() + "." + new Object() {}.getClass().getEnclosingMethod().getName();
        HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateAssetAvailable(available).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        switch (response.code()) {
                            case HttpURLConnection.HTTP_OK:
                                for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                    listener.onAssetUpdateAvailable(HttpURLConnection.HTTP_OK, "");
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_OK));
                                break;
                            case HttpURLConnection.HTTP_UNAUTHORIZED:
                                for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                    listener.onAssetUpdateAvailable(HttpURLConnection.HTTP_UNAUTHORIZED, "");
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_UNAUTHORIZED));
                                break;
                            default:
                                for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                                    listener.onAssetUpdateAvailable(HttpURLConnection.HTTP_GONE, "");
                                }
                                Log.d(methodName, String.valueOf(HttpURLConnection.HTTP_GONE));
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (AssetService.AssetCrudListener listener : assetCrudListeners) {
                            listener.onAssetUpdateAvailable(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(AssetService.class.getName(), throwable.getMessage());
                    }
                });
    }

}
