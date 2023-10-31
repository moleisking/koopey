package com.koopey.service;

import android.content.Context;
import android.util.Log;
import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.Messages;
import com.koopey.model.Tags;
import com.koopey.model.Transactions;
import com.koopey.model.User;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.authentication.ChangePassword;
import com.koopey.model.authentication.ForgotPassword;
import com.koopey.model.authentication.LoginUser;
import com.koopey.model.authentication.RegisterUser;
import com.koopey.service.impl.IAuthenticationService;
import com.koopey.view.MainActivity;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationService {

    public interface LoginListener {
        void onUserLogin(int code, String message, AuthenticationUser authenticationUser);
    }

    public interface RegisterListener {
        void onUserRegister(int code,String message);
    }

    public interface PasswordChangeListener {
        void onPasswordChange(int code, String message);
    }

    public interface PasswordForgotListener {
        void onPasswordForgot(int code, String message);
    }

    public interface UserDeleteListener {
        void onUserDelete(int code,String message);
    }


    public interface UserSaveListener {
        void onUserUpdate(int code,String message);
    }

    public interface UserReadListener {
        void onUserRead(int code,String message, AuthenticationUser authenticationUser );
    }
    private Context context;
    private List<LoginListener> loginListeners = new ArrayList<>();
    private List<RegisterListener> registerListeners = new ArrayList<>();

    private List<PasswordForgotListener> passwordForgotListeners = new ArrayList<>();

    private List<PasswordChangeListener> passwordChangeListeners = new ArrayList<>();

    private List<UserDeleteListener> userDeleteListeners = new ArrayList<>();

    private List<UserSaveListener> userSaveListeners = new ArrayList<>();

    private List<UserReadListener> userReadListeners = new ArrayList<>();

    public AuthenticationService(Context context) {
        this.context = context;
    }

    public AuthenticationUser getLocalAuthenticationUserFromFile() {
        if (SerializeHelper.hasFile(context, AuthenticationUser.AUTH_USER_FILE_NAME)) {
            return (AuthenticationUser) SerializeHelper.loadObject(context, AuthenticationUser.AUTH_USER_FILE_NAME);
        } else {
           return null ;
        }
    }

    public boolean hasAuthenticationUserFile() {
        if (SerializeHelper.hasFile(context, AuthenticationUser.AUTH_USER_FILE_NAME)) {
            return true;
        } else {
            return false;
        }
    }

    public void delete() {
        AuthenticationUser  authenticationUser= getLocalAuthenticationUserFromFile();
        HttpServiceGenerator.createService(IAuthenticationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .delete().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (UserDeleteListener listener : userDeleteListeners) {
                            listener.onUserDelete(HttpURLConnection.HTTP_OK, "");
                        }
                        Log.d(AuthenticationService.class.getName(), "user deleted");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (UserDeleteListener listener : userDeleteListeners) {
                            listener.onUserDelete(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(AuthenticationService.class.getName(), throwable.getMessage());
                    }
                });
    }


    public void login(LoginUser loginUser) {

        HttpServiceGenerator.createService(IAuthenticationService.class, context.getResources().getString(R.string.backend_url))
                .login(loginUser).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<AuthenticationUser> call, Response<AuthenticationUser> response) {
                        AuthenticationUser authenticationUser = response.body();
                        SerializeHelper.saveObject(context, authenticationUser);
                        for (LoginListener listener : loginListeners) {
                            listener.onUserLogin(HttpURLConnection.HTTP_OK, "", authenticationUser);
                        }
                        Log.i(AuthenticationService.class.getName(), authenticationUser.getToken());
                    }

                    @Override
                    public void onFailure(Call<AuthenticationUser> call, Throwable throwable) {
                        for (LoginListener listener : loginListeners) {
                            listener.onUserLogin(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(AuthenticationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void logout() {
        if (SerializeHelper.hasFile(context, Assets.ASSET_SEARCH_RESULTS_FILE_NAME)) {
            SerializeHelper.deleteObject(context, Assets.ASSET_SEARCH_RESULTS_FILE_NAME);
        }
        if (SerializeHelper.hasFile(context, Assets.MY_ASSETS_FILE_NAME)) {
            SerializeHelper.deleteObject(context, Assets.MY_ASSETS_FILE_NAME);
        }
        if (SerializeHelper.hasFile(context, Assets.ASSET_WATCH_LIST_FILE_NAME)) {
            SerializeHelper.deleteObject(context, Assets.ASSET_WATCH_LIST_FILE_NAME);
        }
        if (hasAuthenticationUserFile()) {
            SerializeHelper.deleteObject(context, AuthenticationUser.AUTH_USER_FILE_NAME);
        }
        if (SerializeHelper.hasFile(context, Messages.MESSAGES_FILE_NAME)) {
            SerializeHelper.deleteObject(context, Messages.MESSAGES_FILE_NAME);
        }
        if (SerializeHelper.hasFile(context, Tags.TAGS_FILE_NAME)) {
            SerializeHelper.deleteObject(context, Tags.TAGS_FILE_NAME);
        }
        if (SerializeHelper.hasFile(context, Transactions.TRANSACTIONS_FILE_NAME)) {
            SerializeHelper.deleteObject(context, Transactions.TRANSACTIONS_FILE_NAME);
        }
        ((MainActivity) context).showLoginActivity();
    }

    public void register(RegisterUser registerUser) {
        HttpServiceGenerator.createService(IAuthenticationService.class, context.getResources().getString(R.string.backend_url))
                .register(registerUser).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (RegisterListener listener : registerListeners) {
                            listener.onUserRegister(HttpURLConnection.HTTP_OK, "");
                        }
                        Log.i(AuthenticationService.class.getName(), "Register success");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (RegisterListener listener : registerListeners) {
                            listener.onUserRegister(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(AuthenticationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void changePassword(ChangePassword changePassword) {
        AuthenticationUser  authenticationUser= getLocalAuthenticationUserFromFile();
        HttpServiceGenerator.createService(IAuthenticationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .changePassword(changePassword).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (PasswordChangeListener listener : passwordChangeListeners) {
                            listener.onPasswordChange(HttpURLConnection.HTTP_OK, "");
                        }
                        Log.i(AuthenticationService.class.getName(), "");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (PasswordChangeListener listener : passwordChangeListeners) {
                            listener.onPasswordChange(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(AuthenticationService.class.getName(), throwable.getMessage());
                    }
                });
    }

    public void forgotPassword(String email) {
        HttpServiceGenerator.createService(IAuthenticationService.class, context.getResources().getString(R.string.backend_url))
                .forgotPassword(email).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (PasswordForgotListener listener : passwordForgotListeners) {
                            listener.onPasswordForgot(HttpURLConnection.HTTP_OK, "");
                        }
                        Log.i(AuthenticationService.class.getName(), "Password forgotten success");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (PasswordForgotListener listener : passwordForgotListeners) {
                            listener.onPasswordForgot(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(AuthenticationService.class.getName(), "Password forgotten fail," + throwable.getMessage());
                    }
                });
    }

    public void update(User user) {
        AuthenticationUser  authenticationUser= getLocalAuthenticationUserFromFile();
        HttpServiceGenerator.createService(IAuthenticationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .update(user).enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        for (UserSaveListener listener : userSaveListeners) {
                            listener.onUserUpdate(HttpURLConnection.HTTP_OK, "");
                        }
                        Log.i(AuthenticationService.class.getName(), "User success");
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable throwable) {
                        for (UserSaveListener listener : userSaveListeners) {
                            listener.onUserUpdate(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage());
                        }
                        Log.e(AuthenticationService.class.getName(), "User fail," + throwable.getMessage());
                    }
                });
    }

    public void read(User user) {
        AuthenticationUser  authenticationUser= getLocalAuthenticationUserFromFile();
        HttpServiceGenerator.createService(IAuthenticationService.class, context.getResources().getString(R.string.backend_url), authenticationUser.getToken(), authenticationUser.getLanguage())
                .read().enqueue(new Callback<>() {
                    @Override
                    public void onResponse(Call<AuthenticationUser> call, Response<AuthenticationUser> response) {
                        AuthenticationUser authenticationUser = response.body();
                        for (UserReadListener listener : userReadListeners) {
                            listener.onUserRead(HttpURLConnection.HTTP_OK, "", authenticationUser);
                        }
                        Log.d(AuthenticationService.class.getName(), "authenticationUser success");
                    }

                    @Override
                    public void onFailure(Call<AuthenticationUser> call, Throwable throwable) {
                        for (UserReadListener listener : userReadListeners) {
                            listener.onUserRead(HttpURLConnection.HTTP_BAD_REQUEST, throwable.getMessage(), null);
                        }
                        Log.e(AuthenticationService.class.getName(), "User fail," + throwable.getMessage());
                    }
                });
    }

    public void setOnLoginListener(LoginListener listener) {
        loginListeners.add(listener);
    }

    public void setOnRegisterListener(RegisterListener listener) {
        registerListeners.add(listener);
    }

    public void setOnUserReadListener(UserReadListener listener) {
        userReadListeners.add(listener);
    }

    public void setOnUserSaveListener(UserSaveListener listener) {
        userSaveListeners.add(listener);
    }

    public void setOnPasswordChangeListener(PasswordChangeListener listener) {
        passwordChangeListeners.add(listener);
    }

    public void setOnPasswordForgottenListener(PasswordForgotListener listener) {
        passwordForgotListeners.add(listener);
    }

}
