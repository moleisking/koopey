package com.koopey.service;

import android.content.Context;
import android.util.Log;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.authentication.RegisterUser;
import com.koopey.model.authentication.Token;
import com.koopey.model.User;
import com.koopey.service.impl.IAuthenticationService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthenticationService {

    public interface LoginListener {
        void onPostLogin(Token token);
    }

    public interface RegisterListener {
        void onPostRegister(User user);
    }

    private Context context;

    private List<LoginListener> loginListeners = new ArrayList<>();
    private List<RegisterListener> registerListeners = new ArrayList<>();

    public AuthenticationService(Context context) {
        this.context = context;
    }

    public User getLocalAuthenticationUserFromFile() {

        User user = new User();
        if (SerializeHelper.hasFile(context, User.USER_FILE_NAME)) {
            return (User) SerializeHelper.loadObject(context, User.USER_FILE_NAME);
        } else {
            return user;
        }
    }

    public Token getLocalTokenFromFile() {

        Token token = new Token();
        if (SerializeHelper.hasFile(context, Token.TOKEN_FILE_NAME)) {
            return (Token) SerializeHelper.loadObject(context, Token.TOKEN_FILE_NAME);
        } else {
            return token;
        }
    }

    public boolean hasAuthenticationFile() {
        User user = getLocalAuthenticationUserFromFile();
        return !user.alias.isEmpty();
    }

    public void getLoginResponse(AuthenticationUser authenticationUser) {

        IAuthenticationService service
                = HttpServiceGenerator.createService(IAuthenticationService.class, context.getResources().getString(R.string.backend_url));

        Call<Token> callAsync = service.login(authenticationUser);
        callAsync.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                Token token = response.body();
                if (token.token == null || token.token.isEmpty()) {
                    Log.i(AuthenticationService.class.getName(), "token is null");
                } else {
                    for (LoginListener listener : loginListeners) {
                        listener.onPostLogin(token);
                    }
                    SerializeHelper.saveObject(context, token);
                    Log.i(AuthenticationService.class.getName(), token.token.toString());
                }
            }

            @Override
            public void onFailure(Call<Token> call, Throwable throwable) {
                for (LoginListener listener : loginListeners) {
                    listener.onPostLogin(new Token());
                }
                Log.e(AuthenticationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void getRegisterResponse(RegisterUser registerUser) {

        IAuthenticationService service
                = HttpServiceGenerator.createService(IAuthenticationService.class, context.getResources().getString(R.string.backend_url));
        Call<User> callAsync = service.register(registerUser);

        callAsync.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user = response.body();
                if (user.alias == null || user.alias.isEmpty()) {
                    Log.i(AuthenticationService.class.getName(), "token is null");
                } else {
                    for (RegisterListener listener : registerListeners) {
                        listener.onPostRegister(user);
                    }
                    SerializeHelper.saveObject(context, user);
                    Log.i(AuthenticationService.class.getName(), user.alias.toString());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable throwable) {
                for (RegisterListener listener : registerListeners) {
                    listener.onPostRegister(null);
                }
                Log.e(AuthenticationService.class.getName(), throwable.getMessage());
            }
        });
    }

    public void setOnLoginListener(LoginListener loginListener) {
        loginListeners.add(loginListener);
    }

    public void setOnRegisterListener(RegisterListener registerListener) {
        registerListeners.add(registerListener);
    }

}
