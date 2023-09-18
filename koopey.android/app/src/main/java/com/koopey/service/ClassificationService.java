package com.koopey.service;

import android.content.Context;
import android.util.Log;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Assets;
import com.koopey.model.Classification;
import com.koopey.model.Classifications;
import com.koopey.model.Tags;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.impl.IClassificationService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassificationService {

    public interface ClassificationCrudListener {
        void onClassificationCreate(int code, String message,Classification classification);
        void onClassificationDelete(int code, String message,Classification classification);
        void onClassificationUpdate(int code, String message,Classification classification);
        void onClassificationRead(int code, String message,Classification classification);
    }

    public interface ClassificationSearchListener {
        void onClassificationSearchByAsset(String assetId);

        void onClassificationSearchByTags(Tags tags);
    }

    AuthenticationService authenticationService;
    AuthenticationUser authenticationUser;
    private Context context;

    private List<ClassificationService.ClassificationCrudListener> classificationCrudListeners = new ArrayList<>();
    private List<ClassificationService.ClassificationSearchListener> classificationSearchListeners = new ArrayList<>();

    public ClassificationService(Context context) {
        super();
        this.context = context;
        authenticationService = new AuthenticationService(context);
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
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

    public void readClassification(String classificationId) {
            HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
                    .getClassification(classificationId).enqueue(new Callback<Classification>() {
            @Override
            public void onResponse(Call<Classification> call, Response<Classification> response) {
                Classification classification = response.body();
                if (classification == null || classification.isEmpty()) {
                    for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                        listener.onClassificationRead(HttpURLConnection.HTTP_NO_CONTENT, "",classification);
                    }
                } else {
                    for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                        listener.onClassificationRead(HttpURLConnection.HTTP_OK, "",classification);
                    }
                }
            }
            @Override
            public void onFailure(Call<Classification> call, Throwable throwable) {
                for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                    listener.onClassificationRead(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(),null);
                }
            }
        });
    }

    public void createClassification(Classification classification) {
   HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
                .Classification(classification).enqueue(new Callback<Classification>() {
            @Override
            public void onResponse(Call<Classification> call, Response<Classification> response) {
                Classification classification = response.body();
                if (classification == null || classification.isEmpty()) {
                    Log.i(ClassificationService.class.getName(), "classification is null");
                } else {
                    for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                        listener.onClassificationCreate(HttpURLConnection.HTTP_BAD_REQUEST, "", classification);
                    }
                    SerializeHelper.saveObject(context, classification);
                    Log.i(ClassificationService.class.getName(), classification.toString());
                }
            }

            @Override
            public void onFailure(Call<Classification> call, Throwable throwable) {
                for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                    listener.onClassificationCreate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                }
                Log.e(ClassificationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void deleteClassification(Classification classification) {
        HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
                .postClassificationDelete(classification).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                    listener.onClassificationDelete(HttpURLConnection.HTTP_OK, "",classification);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                    listener.onClassificationDelete(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(),null);
                }
                Log.e(ClassificationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchClassificationByTags(Tags tags) {
      HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
              .postClassificationSearchByTags(tags).enqueue(new Callback<Assets>() {
            @Override
            public void onResponse(Call<Assets> call, Response<Assets> response) {
                Assets assets = response.body();
                if (assets == null || assets.isEmpty()) {
                    Log.i(ClassificationService.class.getName(), "assets is null");
                } else {
                    for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                        listener.onClassificationSearchByTags(tags);
                    }
                    SerializeHelper.saveObject(context, assets);
                    Log.i(ClassificationService.class.getName(), assets.toString());
                }
            }

            @Override
            public void onFailure(Call<Assets> call, Throwable throwable) {
                for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                    listener.onClassificationSearchByTags(null);
                }
                Log.e(ClassificationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postClassificationSearchByAssets(String assetId) {
        HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
                .postClassificationSearchByAsset(assetId).enqueue(new Callback<Tags>() {
            @Override
            public void onResponse(Call<Tags> call, Response<Tags> response) {
                Tags tags = response.body();
                if (tags == null || tags.isEmpty()) {
                    Log.i(ClassificationService.class.getName(), "classification is null");
                } else {
                    for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                        listener.onClassificationSearchByAsset(assetId);
                    }
                }
            }

            @Override
            public void onFailure(Call<Tags> call, Throwable throwable) {
                for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                    listener.onClassificationSearchByAsset(null);
                }
                Log.e(ClassificationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void postClassificationUpdate(Classification classification) {

        IClassificationService service
                = HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.token)
                .postClassificationUpdate(classification).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                    listener.onClassificationUpdate(HttpURLConnection.HTTP_OK,"", classification);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable throwable) {
                for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                    listener.onClassificationUpdate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                }
                Log.e(ClassificationService.class.getName(), throwable.getMessage());
            }
        });
    }
}
