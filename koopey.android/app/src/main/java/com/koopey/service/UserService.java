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

        void onUserLocation(int code, String message);

        void onUserTerm(int code, String message);

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

    public void read(String userId) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                authenticationUser.getToken(), authenticationUser.getLanguage()).read(userId).enqueue(new Callback<>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                for (UserService.UserReadListener listener : userReadListeners) {
                    listener.onUserRead(HttpURLConnection.HTTP_OK, "", user);
                }
                Log.d(UserService.class.getName(), user.getAlias());
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                for (UserService.UserReadListener listener : userReadListeners) {
                    listener.onUserRead(HttpURLConnection.HTTP_BAD_REQUEST, "", null);
                }
                Log.e(UserService.class.getName(), throwable.getMessage());
            }
        });
    }


    public void search(Search search) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .search(search).enqueue(new Callback<Users>() {
                    @Override
                    public void onResponse(Call<Users> call, Response<Users> response) {
                        Users users = response.body();
                        for (UserService.UserSearchListener listener : userSearchListeners) {
                            listener.onUserSearch(HttpURLConnection.HTTP_OK, "", users);
                        }
                        SerializeHelper.saveObject(context, users);
                        Log.i(UserService.class.getName(), String.valueOf(users.size()));
                    }

                    @Override
                    public void onFailure(Call<Users> call, Throwable throwable) {
                        for (UserService.UserSearchListener listener : userSearchListeners) {
                            listener.onUserSearch(HttpURLConnection.HTTP_BAD_REQUEST, "", null);
                        }
                        Log.e(UserService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void updateAvailable(Boolean available) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateAvailable(available).enqueue(new Callback<Void>() {
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

    public void updateCurrency(String currency) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateCurrency(currency).enqueue(new Callback<>() {
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

    public void updateLanguage(String language) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateCurrency(language).enqueue(new Callback<>() {
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

    public void updateLocation(Double altitude, Double latitude, Double longitude) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateLocation(altitude, latitude, longitude).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserLocation(HttpURLConnection.HTTP_OK, "");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserLocation(HttpURLConnection.HTTP_BAD_REQUEST, "");
                        }
                    }
                });
    }

    public void updateMeasure(String measure) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateMeasure(measure).enqueue(new Callback<>() {
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

    public void updateTrack(Boolean track) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateTrack(track).enqueue(new Callback<>() {
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

    public void updateNotifyByDevice(Boolean device) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateNotifyByDevice(device).enqueue(new Callback<>() {
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

    public void updateNotifyByEmail(Boolean email) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateNotifyByEmail(email).enqueue(new Callback<>() {
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

    public void updateTerm(Boolean term) {
        HttpServiceGenerator.createService(IUserService.class, context.getResources().getString(R.string.backend_url),
                        authenticationUser.getToken(), authenticationUser.getLanguage())
                .updateTerm(term).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserTerm(HttpURLConnection.HTTP_OK, "");
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (UserService.UserConfigurationListener listener : userConfigurationListeners) {
                            listener.onUserTerm(HttpURLConnection.HTTP_BAD_REQUEST, "");
                        }
                    }
                });
    }

}
