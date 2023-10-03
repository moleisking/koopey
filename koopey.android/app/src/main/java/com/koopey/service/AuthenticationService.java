package com.koopey.service;

import android.content.Context;
import android.util.Log;
import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.authentication.ChangePassword;
import com.koopey.model.authentication.ForgotPassword;
import com.koopey.model.authentication.LoginUser;
import com.koopey.model.authentication.RegisterUser;
import com.koopey.service.impl.IAuthenticationService;

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

    private Context context;

    private List<LoginListener> loginListeners = new ArrayList<>();
    private List<RegisterListener> registerListeners = new ArrayList<>();

    private List<PasswordForgotListener> passwordForgotListeners = new ArrayList<>();

    private List<PasswordChangeListener> passwordChangeListeners = new ArrayList<>();

    public AuthenticationService(Context context) {
        this.context = context;
    }

    public AuthenticationUser getLocalAuthenticationUserFromFile() {
        if (SerializeHelper.hasFile(context, AuthenticationUser.AUTH_USER_FILE_NAME)) {
            return (AuthenticationUser) SerializeHelper.loadObject(context, AuthenticationUser.AUTH_USER_FILE_NAME);
        } else {
            return null;
        }
    }

    public boolean hasAuthenticationUserFile() {
        if (SerializeHelper.hasFile(context, AuthenticationUser.AUTH_USER_FILE_NAME)) {
            return true;
        } else {
            return false;
        }
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
        if (hasAuthenticationUserFile()) {
            SerializeHelper.deleteObject(context, AuthenticationUser.AUTH_USER_FILE_NAME);
        }
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
        HttpServiceGenerator.createService(IAuthenticationService.class, context.getResources().getString(R.string.backend_url))
                .changePassword(changePassword).enqueue(new Callback<Void>() {
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
                        Log.e(AuthenticationService.class.getName(),  throwable.getMessage());
                    }
                });
    }

    public void forgotPassword(String email) {
        HttpServiceGenerator.createService(IAuthenticationService.class, context.getResources().getString(R.string.backend_url))
                .forgotPassword(email).enqueue(new Callback<Void>() {
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

    public void setOnLoginListener(LoginListener loginListener) {
        loginListeners.add(loginListener);
    }

    public void setOnRegisterListener(RegisterListener registerListener) {
        registerListeners.add(registerListener);
    }

    public void setOnPasswordChangeListener(PasswordChangeListener passwordChangeListener) {
        passwordChangeListeners.add(passwordChangeListener);
    }

    public void setOnPasswordForgottenListener(PasswordForgotListener passwordForgotListener) {
        passwordForgotListeners.add(passwordForgotListener);
    }

}
