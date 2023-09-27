package com.koopey.service;

import android.content.Context;
import android.util.Log;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Locations;
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
        void onClassificationCreate(int code, String message,String classificationId);
        void onClassificationDelete(int code, String message,Classification classification);
        void onClassificationUpdate(int code, String message,Classification classification);
        void onClassificationRead(int code, String message,Classification classification);
    }

    public interface ClassificationSearchListener {
        void onClassificationSearchByLocation(String locationId);

        void onClassificationSearchByTags(Tags tags);
    }

    AuthenticationService authenticationService;
    AuthenticationUser authenticationUser;
    private Context context;
    String jwt;

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
            HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                    .readClassification(classificationId).enqueue(new Callback<Classification>() {
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
   HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .createClassification(classification).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String classificationId = response.body();
                if (classificationId == null || classificationId.isEmpty()) {
                    Log.i(ClassificationService.class.getName(), "classification is null");
                } else {
                    for (ClassificationService.ClassificationCrudListener listener : classificationCrudListeners) {
                        listener.onClassificationCreate(HttpURLConnection.HTTP_BAD_REQUEST, "", classificationId);
                    }
                    SerializeHelper.saveObject(context, classification);
                    Log.i(ClassificationService.class.getName(), classification.toString());
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

    public void deleteClassification(Classification classification) {
        HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .deleteClassification(classification).enqueue(new Callback<Void>() {
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
      HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
              .searchClassificationByTags(tags).enqueue(new Callback<Locations>() {
            @Override
            public void onResponse(Call<Locations> call, Response<Locations> response) {
                Locations locations = response.body();
                if (locations == null || locations.isEmpty()) {
                    Log.i(ClassificationService.class.getName(), "locations is null");
                } else {
                    for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                        listener.onClassificationSearchByTags(tags);
                    }
                    SerializeHelper.saveObject(context, locations);
                    Log.i(ClassificationService.class.getName(), locations.toString());
                }
            }

            @Override
            public void onFailure(Call<Locations> call, Throwable throwable) {
                for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                    listener.onClassificationSearchByTags(null);
                }
                Log.e(ClassificationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void searchClassificationByLocations(String locationId) {
        HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchClassificationByLocation(locationId).enqueue(new Callback<Tags>() {
            @Override
            public void onResponse(Call<Tags> call, Response<Tags> response) {
                Tags tags = response.body();
                if (tags == null || tags.isEmpty()) {
                    Log.i(ClassificationService.class.getName(), "classification is null");
                } else {
                    for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                        listener.onClassificationSearchByLocation(locationId);
                    }
                }
            }

            @Override
            public void onFailure(Call<Tags> call, Throwable throwable) {
                for (ClassificationService.ClassificationSearchListener listener : classificationSearchListeners) {
                    listener.onClassificationSearchByLocation(null);
                }
                Log.e(ClassificationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void updateClassification(Classification classification) {
 HttpServiceGenerator.createService(IClassificationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateClassification(classification).enqueue(new Callback<Void>() {
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
