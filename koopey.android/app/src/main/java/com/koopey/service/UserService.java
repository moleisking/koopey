package com.koopey.service;

import android.content.Context;
import android.util.Log;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Search;
import com.koopey.model.User;
import com.koopey.model.Users;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.impl.IUserService;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserService {

    public interface UserReadListener {
        void onUserRead(int code, String message, User user);
    }

    public interface UserSearchListener {
        void onUserSearch(int code, String message, Users users);
    }

    public interface UserConfigurationListener {
        void onUserAvailable(int code, String message);

        void onUserCurrency(int code, String message);

        void onUserLanguage(int code, String message);

        void onUserTrack(int code, String message);

        void onUserNotifyByDevice(int code, String message);

        void onUserNotifyByEmail(int code, String message);
    }

    private AuthenticationService authenticationService;

    private AuthenticationUser authenticationUser;
    private Context context;

    private List<UserService.UserReadListener> userReadListeners = new ArrayList<>();
    private List<UserService.UserSearchListener> userSearchListeners = new ArrayList<>();

    private List<UserService.UserConfigurationListener> userConfigurationListeners = new ArrayList<>();

    public UserService(Context context) {
        super();
        this.context = context;
        authenticationService = new AuthenticationService(context);
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
    }

    public void setOnUserReadListener(UserReadListener userReadListener) {
        userReadListeners.add(userReadListener);
    }

    public void setOnUserSearchListener(UserSearchListener userSearchListener) {
        userSearchListeners.add(userSearchListener);
    }


    public Users loadUserSearchResultsFromFile() {
        Users users = new Users();
        if (SerializeHelper.hasFile(context, Users.SEARCH_RESULTS_FILE_NAME)) {
            users = (Users) SerializeHelper.loadObject(context, Users.SEARCH_RESULTS_FILE_NAME);
        }
        return users;
    }

    public boolean hasUsersFile() {
        Users users = loadUserSearchResultsFromFile();
        return users.size() <= 0 ? false : true;
    }

    public void searchUser(Search search) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .searchUser(search).enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        Users users = response.body();
                        for (UserService.UserSearchListener listener : userSearchListeners) {
                            listener.onUserSearch(HttpURLConnection.HTTP_OK, "", users);
                        }
                        SerializeHelper.saveObject(context, users);
                        Log.i(LocationService.class.getName(), String.valueOf(users.size()));
                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable throwable) {
                        for (UserService.UserSearchListener listener : userSearchListeners) {
                            listener.onUserSearch(HttpURLConnection.HTTP_BAD_REQUEST, "", null);
                        }
                        Log.e(LocationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void updateUserAvailable(Boolean available) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateUserAvailable(available).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserAvailable(HttpURLConnection.HTTP_OK, "");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserAvailable(HttpURLConnection.HTTP_BAD_REQUEST, "");
                        }
                    }
                });
    }

    public void updateUserCurrency(String currency) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateUserCurrency(currency).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserCurrency(HttpURLConnection.HTTP_OK, "");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserCurrency(HttpURLConnection.HTTP_BAD_REQUEST, "");
                        }
                    }
                });
    }

    public void updateUserLanguage(String language) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateUserCurrency(language).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserCurrency(HttpURLConnection.HTTP_OK, "");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserCurrency(HttpURLConnection.HTTP_BAD_REQUEST, "");
                        }
                    }
                });
    }

    public void updateUserMeasure(String measure) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateUserMeasure(measure).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserCurrency(HttpURLConnection.HTTP_OK, "");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserCurrency(HttpURLConnection.HTTP_BAD_REQUEST, "");
                        }
                    }
                });
    }

    public void updateUserTrack(Boolean track) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateUserTrack(track).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserTrack(HttpURLConnection.HTTP_OK, "");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserTrack(HttpURLConnection.HTTP_BAD_REQUEST, "");
                        }
                    }
                });
    }

    public void updateUserNotifyByDevice(Boolean device) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateUserNotifyByDevice(device).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserNotifyByDevice(HttpURLConnection.HTTP_OK, "");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserNotifyByDevice(HttpURLConnection.HTTP_BAD_REQUEST, "");
                        }
                    }
                });
    }

    public void updateUserNotifyByEmail(Boolean email) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateUserNotifyByEmail(email).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserNotifyByEmail(HttpURLConnection.HTTP_OK, "");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserNotifyByEmail(HttpURLConnection.HTTP_BAD_REQUEST, "");
                        }
                    }
                });
    }

}
