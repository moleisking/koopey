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
        void onClassificationCreate(int code, String message, String classificationId);

        void onClassificationDelete(int code, String message, Classification classification);

        void onClassificationUpdate(int code, String message, Classification classification);

        void onClassificationRead(int code, String message, Classification classification);
    }

    public interface ClassificationSearchListener {
        void onClassificationSearchByTags(int code, String message, Assets assets);

        void onClassificationSearchByAsset(int code, String message, Tags tags);
    }

    private AuthenticationService authenticationService;
    private AuthenticationUser authenticationUser;
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

    public void setOnClassificationCrudListener(ClassificationCrudListener listener) {
        classificationCrudListeners.add(listener);
    }

    public void setOnClassificationSearchListener(ClassificationSearchListener listener) {
        classificationSearchListeners.add(listener);
    }

    public void read(String classificationId) {
        HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .read(classificationId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Classification> call, Response<Classification> response) {
                        Classification classification = response.body();
                        if (classification == null || classification.isEmpty()) {
                            for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                                listener.onClassificationRead(HttpURLConnection.HTTP_NO_CONTENT, "", classification);
                            }
                        } else {
                            for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                                listener.onClassificationRead(HttpURLConnection.HTTP_OK, "", classification);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Classification> call, Throwable throwable) {
                        for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                            listener.onClassificationRead(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                    }
                });
    }

    public void create(Classification classification) {
        HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .create(classification).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String classificationId = response.body();
                        if (classificationId == null || classificationId.isEmpty()) {
                            Log.d(ClassificationService.class.getName(), "classification is null");
                        } else {
                            for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                                listener.onClassificationCreate(HttpURLConnection.HTTP_BAD_REQUEST, "", classificationId);
                            }
                            Log.d(ClassificationService.class.getName() + ".create", classification.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                            listener.onClassificationCreate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(ClassificationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void delete(Classification classification) {
        HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .delete(classification).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                            listener.onClassificationDelete(HttpURLConnection.HTTP_OK, "", classification);
                        }
                        Log.d(ClassificationService.class.getName() + ".delete", classification.toString());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                            listener.onClassificationDelete(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(ClassificationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchByAsset(String assetId) {
        HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByAsset(assetId).enqueue(new Callback<Tags>() {
                    @Override
                    public void onResponse(Call<Tags> call, Response<Tags> response) {
                        Tags tags = response.body();
                        if (tags == null || tags.isEmpty()) {
                            for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                                listener.onClassificationSearchByAsset(HttpURLConnection.HTTP_NO_CONTENT, "", new Tags());
                            }
                            Log.d(ClassificationService.class.getName() + ".searchByAsset", "tags is null");
                        } else {
                            for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                                listener.onClassificationSearchByAsset(HttpURLConnection.HTTP_OK, "", tags);
                            }
                            Log.d(ClassificationService.class.getName() + ".searchByAsset", tags.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Tags> call, Throwable throwable) {
                        for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                            listener.onClassificationSearchByAsset(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(ClassificationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void searchByTags(Tags tags) {
        HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchByTags(tags).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Assets> call, Response<Assets> response) {
                        Assets assets = response.body();
                        if (assets == null || assets.isEmpty()) {
                            for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                                listener.onClassificationSearchByTags(HttpURLConnection.HTTP_NO_CONTENT, "", new Assets());
                            }
                            Log.d(ClassificationService.class.getName() + ".searchByTags", "locations is null");
                        } else {
                            for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                                listener.onClassificationSearchByTags(HttpURLConnection.HTTP_OK, "", assets);
                            }
                            Log.d(ClassificationService.class.getName() + ".searchByTags", assets.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Assets> call, Throwable throwable) {
                        for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                            listener.onClassificationSearchByTags(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(ClassificationService.class.getName(), throwable.getMessage());
                    }
                });
    }


    public void update(Classification classification) {
        HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .update(classification).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                            listener.onClassificationUpdate(HttpURLConnection.HTTP_OK, "", classification);
                        }
                        Log.d(ClassificationService.class.getName() + ".update", classification.toString());
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
