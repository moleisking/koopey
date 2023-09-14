package com.koopey.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.koopey.R;
import com.koopey.controller.GetJSON;
import com.koopey.controller.PostJSON;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Alert;
import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.AuthUser;
import com.koopey.model.Search;
import com.koopey.model.Tags;
import com.koopey.model.Transactions;
import com.koopey.model.authentication.Token;
import com.koopey.service.impl.IAssetService;
import com.koopey.service.impl.ITagService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssetService {
    public interface AssetListener {
        void onGetAsset(String assetId);
        void onGetAssetUpdateAvailable(Boolean available);
        void onGetAssetsByBuyer();
        void onGetAssetsByBuyerOrSeller();
        void onGetAssetsBySeller();
        void onGetAssetUpdate(String assetId);
        void onPostAssetCreate(Asset asset);
        void onPostAssetDelete(Asset asset);
        void onPostAssetUpdate(Asset asset);
        void onPostAssetSearch(Search search);
    }

    AuthenticationService authenticationService;
    private Context context;

    private List<AssetService.AssetListener> assetListeners = new ArrayList<>();

    public AssetService(Context context) {
        super();
        this.context = context;
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

    public void getAsset(String assetId) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IAssetService service
                = HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Asset> callAsync = service.getAsset(assetId);
        callAsync.enqueue(new Callback<Asset>() {
            @Override
            public void onResponse(Call<Asset> call, Response<Asset> response) {
                Asset asset = response.body();
                if (asset == null || asset.isEmpty()) {
                    Log.i(AssetService.class.getName(), "asset is null");
                } else {
                    for (AssetService.AssetListener listener : assetListeners) {
                        listener.onGetAsset(assetId);
                    }
                    SerializeHelper.saveObject(context, asset);
                    Log.i(AssetService.class.getName(), asset.toString());
                }
            }

            @Override
            public void onFailure(Call<Asset> call, Throwable throwable) {
                for (AssetService.AssetListener listener : assetListeners) {
                    listener.onGetAsset(null);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getAssetsSearchByBuyer() {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IAssetService service
                = HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Assets> callAsync = service.getAssetsSearchByBuyer();
        callAsync.enqueue(new Callback<Assets>() {
            @Override
            public void onResponse(Call<Assets> call, Response<Assets> response) {
                Assets assets = response.body();
                if (assets == null || assets.isEmpty()) {
                    Log.i(AssetService.class.getName(), "asset is null");
                } else {
                    for (AssetService.AssetListener listener : assetListeners) {
                        listener.onGetAssetsByBuyer();
                    }
                    SerializeHelper.saveObject(context, assets);
                    Log.i(AssetService.class.getName(), assets.toString());
                }
            }

            @Override
            public void onFailure(Call<Assets> call, Throwable throwable) {
                for (AssetService.AssetListener listener : assetListeners) {
                    listener.onGetAssetsByBuyer();
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getAssetsSearchByBuyerOrSeller() {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IAssetService service
                = HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Assets> callAsync = service.getAssetsSearchByBuyerOrSeller();
        callAsync.enqueue(new Callback<Assets>() {
            @Override
            public void onResponse(Call<Assets> call, Response<Assets> response) {
                Assets assets = response.body();
                if (assets == null || assets.isEmpty()) {
                    Log.i(AssetService.class.getName(), "assets is null");
                } else {
                    for (AssetService.AssetListener listener : assetListeners) {
                        listener.onGetAssetsByBuyerOrSeller();
                    }
                    SerializeHelper.saveObject(context, assets);
                    Log.i(AssetService.class.getName(), assets.toString());
                }
            }

            @Override
            public void onFailure(Call<Assets> call, Throwable throwable) {
                for (AssetService.AssetListener listener : assetListeners) {
                    listener.onGetAssetsByBuyerOrSeller();
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getAssetsSearchBySeller() {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IAssetService service
                = HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Assets> callAsync = service.getAssetsSearchBySeller();
        callAsync.enqueue(new Callback<Assets>() {
            @Override
            public void onResponse(Call<Assets> call, Response<Assets> response) {
                Assets assets = response.body();
                if (assets == null || assets.isEmpty()) {
                    Log.i(AssetService.class.getName(), "assets is null");
                } else {
                    for (AssetService.AssetListener listener : assetListeners) {
                        listener.onGetAssetsBySeller();
                    }
                    SerializeHelper.saveObject(context, assets);
                    Log.i(AssetService.class.getName(), assets.toString());
                }
            }

            @Override
            public void onFailure(Call<Assets> call, Throwable throwable) {
                for (AssetService.AssetListener listener : assetListeners) {
                    listener.onGetAssetsBySeller();
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getAssetUpdateAvailable(Boolean available) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IAssetService service
                = HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Void> callAsync = service.getAssetUpdateAvailable(available);
        callAsync.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                for (AssetService.AssetListener listener : assetListeners) {
                    listener.onGetAssetUpdateAvailable(available);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                for (AssetService.AssetListener listener : assetListeners) {
                    listener.onGetAssetUpdateAvailable(null);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postAssetCreate(Asset asset) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IAssetService service
                = HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<String> callAsync = service.postAssetCreate(asset);
        callAsync.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String assetId = response.body();
                if (assetId == null || assetId.isEmpty()) {
                    Log.i(AssetService.class.getName(), "assets is null");
                } else {
                    for (AssetService.AssetListener listener : assetListeners) {
                        listener.onPostAssetCreate(asset);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable throwable) {
                for (AssetService.AssetListener listener : assetListeners) {
                    listener.onPostAssetCreate(asset);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postAssetDelete(Asset asset) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IAssetService service
                = HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Void> callAsync = service.postAssetDelete(asset);
        callAsync.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                for (AssetService.AssetListener listener : assetListeners) {
                    listener.onPostAssetDelete(asset);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                for (AssetService.AssetListener listener : assetListeners) {
                    listener.onPostAssetDelete(asset);
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postAssetSearch(Search search) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IAssetService service
                = HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Assets> callAsync = service.postAssetSearch(search);
        callAsync.enqueue(new Callback<Assets>() {
            @Override
            public void onResponse(Call<Assets> call, Response<Assets> response) {
                Assets assets = response.body();
                if (assets == null || assets.isEmpty()) {
                    Log.i(AssetService.class.getName(), "assets is null");
                } else {
                    for (AssetService.AssetListener listener : assetListeners) {
                        listener.onGetAssetsBySeller();
                    }
                    SerializeHelper.saveObject(context, assets);
                    Log.i(AssetService.class.getName(), assets.toString());
                }
            }

            @Override
            public void onFailure(Call<Assets> call, Throwable throwable) {
                for (AssetService.AssetListener listener : assetListeners) {
                    listener.onGetAssetsBySeller();
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postAssetUpdate(Asset asset) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IAssetService service
                = HttpServiceGenerator.createService(IAssetService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Void> callAsync = service.postAssetUpdate(asset);
        callAsync.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                for (AssetService.AssetListener listener : assetListeners) {
                    listener.onGetAssetsBySeller();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                for (AssetService.AssetListener listener : assetListeners) {
                    listener.onGetAssetsBySeller();
                }
                Log.e(AssetService.class.getName(), throwable.getMessage());
            }
        });
    }
}
