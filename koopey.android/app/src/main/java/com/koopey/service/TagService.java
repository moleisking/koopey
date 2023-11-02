package com.koopey.service;

import android.content.Context;
import android.util.Log;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Tag;
import com.koopey.model.Tags;
import com.koopey.model.Wallet;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.impl.ITagService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TagService {

    public interface TagCrudListener {
        void onTagCreate(int code, String message, String tagId);

        void onTagDelete(int code, String message);

        void onTagRead(int code, String message, Tag tag);

        void onTagUpdate(int code, String message);
    }

    public interface TagSearchListener {
        void onTagSearch(int code, String message,Tags tags);
    }

    private AuthenticationService authenticationService;
    private AuthenticationUser authenticationUser;
    private Context context;
    private List<TagService.TagCrudListener> tagCrudListeners = new ArrayList<>();
    private List<TagService.TagSearchListener> tagSearchListeners = new ArrayList<>();

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

    public void setOnTagCrudListener(TagService.TagCrudListener listener) {
        tagCrudListeners.add(listener);
    }
    public void setOnTagSearchListener(TagService.TagSearchListener listener) {
        tagSearchListeners.add(listener);
    }

    public void create(Tag tag) {
        HttpServiceGenerator.createService(ITagService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .create(tag).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        String tagId = response.body();
                            for (TagService.TagCrudListener listener : tagCrudListeners) {
                                listener.onTagCreate(HttpURLConnection.HTTP_OK, "",tagId);
                            }
                            Log.d(TagService.class.getName(), tagId.toString());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable throwable) {
                        for (TagService.TagCrudListener listener : tagCrudListeners) {
                            listener.onTagCreate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(),"");
                        }
                        Log.e(TagService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void delete(Tag tag) {
        HttpServiceGenerator.createService(ITagService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .delete(tag).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                            for (TagService.TagCrudListener listener : tagCrudListeners) {
                                listener.onTagDelete(HttpURLConnection.HTTP_OK, "");
                            }
                            Log.d(TagService.class.getName(), tag.toString());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (TagService.TagCrudListener listener : tagCrudListeners) {
                            listener.onTagDelete(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(TagService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void read(String tagId) {
        HttpServiceGenerator.createService(ITagService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .read(tagId).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Tag> call, Response<Tag> response) {
                        Tag tag = response.body();
                        if (tag == null || tag.isEmpty()) {
                            for (TagService.TagCrudListener listener : tagCrudListeners) {
                                listener.onTagRead(HttpURLConnection.HTTP_NO_CONTENT, "",Tag.builder().build());
                            }
                            Log.d(TagService.class.getName(), "tags is null");
                        } else {
                            for (TagService.TagCrudListener listener : tagCrudListeners) {
                                listener.onTagRead(HttpURLConnection.HTTP_OK, "",tag);
                            }
                            Log.d(TagService.class.getName(), tag.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Tag> call, Throwable throwable) {
                        for (TagService.TagCrudListener listener : tagCrudListeners) {
                            listener.onTagRead(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(),null);
                        }
                        Log.e(TagService.class.getName(), throwable.getMessage());
                    }
                });
    }
    public void search() {
        HttpServiceGenerator.createService(ITagService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .search().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Tags> call, Response<Tags> response) {
                        Tags tags = response.body();
                        if (tags == null || tags.size() <= 0) {
                            for (TagService.TagSearchListener listener : tagSearchListeners) {
                                listener.onTagSearch(HttpURLConnection.HTTP_NO_CONTENT, "",new Tags());
                            }
                            Log.d(TagService.class.getName(), "tags is null");
                        } else {
                            for (TagService.TagSearchListener listener : tagSearchListeners) {
                                listener.onTagSearch(HttpURLConnection.HTTP_OK, "",tags);
                            }
                            SerializeHelper.saveObject(context, tags);
                            Log.d(TagService.class.getName(), tags.toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Tags> call, Throwable throwable) {
                        for (TagService.TagSearchListener listener : tagSearchListeners) {
                            listener.onTagSearch(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(),new Tags());
                        }
                        Log.e(TagService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void update(Tag tag) {
        HttpServiceGenerator.createService(ITagService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .update(tag).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                            for (TagService.TagCrudListener listener : tagCrudListeners) {
                                listener.onTagUpdate(HttpURLConnection.HTTP_OK, "");
                            }
                            Log.d(TagService.class.getName(), tag.toString());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (TagService.TagCrudListener listener : tagCrudListeners) {
                            listener.onTagUpdate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(TagService.class.getName(), throwable.getMessage());
                    }
                });
    }


}
