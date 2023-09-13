package com.koopey.service;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.koopey.R;
import com.koopey.controller.GetJSON;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Alert;
import com.koopey.model.Tag;
import com.koopey.model.Tags;
import com.koopey.model.User;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.authentication.Token;
import com.koopey.service.impl.IAuthenticationService;
import com.koopey.service.impl.ITagService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TagService {

    public interface TagListener {
        void getTagsSuccess(Tags tags);
        void getTagsFail();
    }

    AuthenticationService authenticationService;
    private Context context;

    private List<TagService.TagListener> tagListeners = new ArrayList<>();

    public TagService(Context context) {
        super();
        this.context = context;


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
        return tags.size() <= 0 ? false :  true;
    }
    public void getTagsResponse() {
        authenticationService = new AuthenticationService(context);
        Token token = authenticationService.getLocalTokenFromFile();
        ITagService service
                = HttpServiceGenerator.createService(ITagService.class, context.getResources().getString(R.string.backend_url),token.token);

        Call<Tags> callAsync = service.getTags();
        callAsync.enqueue(new Callback<Tags>() {
            @Override
            public void onResponse(Call<Tags> call, Response<Tags> response) {
                Tags tags = response.body();
                if (tags == null || tags.size() <= 0) {
                    Log.i(TagService.class.getName(), "tags is null");
                } else {
                    for (TagService.TagListener listener : tagListeners) {
                        listener.getTagsSuccess(tags);
                    }
                    SerializeHelper.saveObject(context, tags);
                    Log.i(TagService.class.getName(), tags.toString());
                }
            }

            @Override
            public void onFailure(Call<Tags> call, Throwable throwable) {
                for (TagService.TagListener listener : tagListeners) {
                    listener.getTagsFail();
                }
                Log.e(TagService.class.getName(), throwable.getMessage());
            }
        });
    }

   /* @Override
    public void onGetResponse(String output) {
        try {
            String header = (output.length() >= 20) ? output.substring(0, 19).toLowerCase() : output;
            if (header.contains("alert")) {
                Alert alert = new Alert();
                alert.parseJSON(output);
                if (alert.isSuccess()) {
                    Log.i(DataService.class.getName(), "Login success");
                    Toast.makeText(activity, activity.getResources().getString(R.string.info_authentication), Toast.LENGTH_LONG).show();
                } else if (alert.isError()) {
                    Toast.makeText(activity, activity.getResources().getString(R.string.error_authentication), Toast.LENGTH_LONG).show();
                }
            } else if (header.contains("tags")) {
                Tags tags = new Tags();
                tags.parseJSON(output);
                SerializeHelper.saveObject(activity, tags);
            }
        } catch (Exception ex) {
            Log.d(DataService.class.getName(), ex.getMessage());
        }
    }*/

}
