package com.koopey.service;

import android.content.Context;
import android.util.Log;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Assets;
import com.koopey.model.Classification;
import com.koopey.model.Classifications;
import com.koopey.model.Tags;
import com.koopey.model.authentication.Token;
import com.koopey.service.impl.IClassificationService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassificationService {

    public interface ClassificationListener {
        void onGetClassification(String classificationId);

        void onPostClassificationCreate(Classification classification);

        void onPostClassificationDelete(Classification classification);

        void onPostClassificationSearchByAsset(String assetId);

        void onPostClassificationSearchByTags(Tags tags);

        void onPostClassificationUpdate(Classification classification);
    }

    AuthenticationService authenticationService;
    private Context context;

    private List<ClassificationService.ClassificationListener> classificationListeners = new ArrayList<>();

    public ClassificationService(Context context) {
        super();
        this.context = context;
    }

    public Classifications getLocalClassificationsFromFile() {
        Classifications classifications = new Classifications();
        if (SerializeHelper.hasFile(context, Classifications.CLASSIFICATIONS_FILE_NAME)) {
            classifications = (Classifications) SerializeHelper.loadObject(context, Classifications.CLASSIFICATIONS_FILE_NAME);
        }
        return classifications;
    }

    public boolean hasClassificationsFile() {
        Classifications classifications = getLocalClassificationsFromFile();
        return classifications.size() <= 0 ? false : true;
    }

    public void getClassification(String classificationId) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IClassificationService service
                = HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Classification> callAsync = service.getClassification(classificationId);
        callAsync.enqueue(new Callback<Classification>() {
            @Override
            public void onResponse(Call<Classification> call, Response<Classification> response) {
                Classification classification = response.body();
                if (classification == null || classification.isEmpty()) {
                    Log.i(ClassificationService.class.getName(), "classification is null");
                } else {
                    for (ClassificationService.ClassificationListener listener : classificationListeners) {
                        listener.onGetClassification(classificationId);
                    }
                    SerializeHelper.saveObject(context, classification);
                    Log.i(ClassificationService.class.getName(), classification.toString());
                }
            }

            @Override
            public void onFailure(Call<Classification> call, Throwable throwable) {
                for (ClassificationService.ClassificationListener listener : classificationListeners) {
                    listener.onGetClassification(null);
                }
                Log.e(ClassificationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postClassificationCreate(String classificationId) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IClassificationService service
                = HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Classification> callAsync = service.getClassification(classificationId);
        callAsync.enqueue(new Callback<Classification>() {
            @Override
            public void onResponse(Call<Classification> call, Response<Classification> response) {
                Classification classification = response.body();
                if (classification == null || classification.isEmpty()) {
                    Log.i(ClassificationService.class.getName(), "classification is null");
                } else {
                    for (ClassificationService.ClassificationListener listener : classificationListeners) {
                        listener.onGetClassification(classificationId);
                    }
                    SerializeHelper.saveObject(context, classification);
                    Log.i(ClassificationService.class.getName(), classification.toString());
                }
            }

            @Override
            public void onFailure(Call<Classification> call, Throwable throwable) {
                for (ClassificationService.ClassificationListener listener : classificationListeners) {
                    listener.onGetClassification(null);
                }
                Log.e(ClassificationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postClassificationDelete(Classification classification) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IClassificationService service
                = HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Void> callAsync = service.postClassificationDelete(classification);
        callAsync.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                for (ClassificationService.ClassificationListener listener : classificationListeners) {
                    listener.onPostClassificationDelete(classification);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                for (ClassificationService.ClassificationListener listener : classificationListeners) {
                    listener.onPostClassificationDelete(null);
                }
                Log.e(ClassificationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postClassificationSearchByTags(Tags tags) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IClassificationService service
                = HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Assets> callAsync = service.postClassificationSearchByTags(tags);
        callAsync.enqueue(new Callback<Assets>() {
            @Override
            public void onResponse(Call<Assets> call, Response<Assets> response) {
                Assets assets = response.body();
                if (assets == null || assets.isEmpty()) {
                    Log.i(ClassificationService.class.getName(), "assets is null");
                } else {
                    for (ClassificationService.ClassificationListener listener : classificationListeners) {
                        listener.onPostClassificationSearchByTags(tags);
                    }
                    SerializeHelper.saveObject(context, assets);
                    Log.i(ClassificationService.class.getName(), assets.toString());
                }
            }

            @Override
            public void onFailure(Call<Assets> call, Throwable throwable) {
                for (ClassificationService.ClassificationListener listener : classificationListeners) {
                    listener.onGetClassification(null);
                }
                Log.e(ClassificationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postClassificationSearchByAssets(String assetId) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IClassificationService service
                = HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Tags> callAsync = service.postClassificationSearchByAsset(assetId);
        callAsync.enqueue(new Callback<Tags>() {
            @Override
            public void onResponse(Call<Tags> call, Response<Tags> response) {
                Tags tags = response.body();
                if (tags == null || tags.isEmpty()) {
                    Log.i(ClassificationService.class.getName(), "classification is null");
                } else {
                    for (ClassificationService.ClassificationListener listener : classificationListeners) {
                        listener.onPostClassificationSearchByAsset(assetId);
                    }
                }
            }

            @Override
            public void onFailure(Call<Tags> call, Throwable throwable) {
                for (ClassificationService.ClassificationListener listener : classificationListeners) {
                    listener.onPostClassificationSearchByAsset(null);
                }
                Log.e(ClassificationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postClassificationUpdate(Classification classification) {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        IClassificationService service
                = HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), token.token);

        Call<Void> callAsync = service.postClassificationUpdate(classification);
        callAsync.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                for (ClassificationService.ClassificationListener listener : classificationListeners) {
                    listener.onPostClassificationUpdate(classification);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                for (ClassificationService.ClassificationListener listener : classificationListeners) {
                    listener.onPostClassificationUpdate(null);
                }
                Log.e(ClassificationService.class.getName(), throwable.getMessage());
            }
        });
    }
}
