package com.koopey.service;

import android.content.Context;
import android.util.Log;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Tags;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.impl.ITagService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagService {

    public interface TagListener {
        void onTagSearch(int code, String message,Tags tags);
    }

    AuthenticationService authenticationService;
    AuthenticationUser authenticationUser;
    private Context context;

    private List<TagService.TagListener> tagListeners = new ArrayList<>();

    public TagService(Context context) {
        super();
        this.context = context;
        authenticationService = new AuthenticationService(context);
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
    }

    public Tags getLocalTagsFromFile() {
        Tags tags = new Tags();
        if (SerializeHelper.hasFile(context, Tags.TAGS_FILE_NAME)) {
            tags = (Tags) SerializeHelper.loadObject(context, Tags.TAGS_FILE_NAME);
        }
        return tags;
    }

    public boolean hasTagsFile() {
        Tags tags = getLocalTagsFromFile();
        return tags.size() <= 0 ? false : true;
    }

    public void setOnTagSearchListener(TagService.TagListener listener) {
        tagListeners.add(listener);
    }

    public void searchTags() {
        HttpServiceGenerator.createService(ITagService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchTags().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Tags> call, Response<Tags> response) {
                        Tags tags = response.body();
                        if (tags == null || tags.size() <= 0) {
                            for (TagService.TagListener listener : tagListeners) {
                                listener.onTagSearch(HttpURLConnection.HTTP_NO_CONTENT, "",new Tags());
                            }
                            Log.i(TagService.class.getName(), "tags is null");
                        } else {
                            for (TagService.TagListener listener : tagListeners) {
                                listener.onTagSearch(HttpURLConnection.HTTP_OK, "",tags);
                            }
                            SerializeHelper.saveObject(context, tags);
                            Log.i(TagService.class.getName(), tags.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Tags> call, Throwable throwable) {
                        for (TagService.TagListener listener : tagListeners) {
                            listener.onTagSearch(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(),new Tags());
                        }
                        Log.e(TagService.class.getName(), throwable.getMessage());
                    }
                });
    }

}
