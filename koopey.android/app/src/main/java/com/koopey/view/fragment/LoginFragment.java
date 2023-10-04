package com.koopey.view.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Tags;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.authentication.LoginUser;
import com.koopey.service.AuthenticationService;
import com.koopey.service.TagService;
import com.koopey.view.MainActivity;

import java.net.HttpURLConnection;

public class LoginFragment extends Fragment implements AuthenticationService.LoginListener, TagService.TagListener, View.OnClickListener {

    private AuthenticationService authenticationService;
    private Button btnLogin, btnRegister;
    private EditText txtEmail, txtPassword;
    private AuthenticationUser authenticationUser;
    private View viewLogin, viewProgress;

    private void checkPreviousAuthentication() {
        if (SerializeHelper.hasFile(this.getActivity(), AuthenticationUser.AUTH_USER_FILE_NAME)) {
            this.authenticationUser = (AuthenticationUser) SerializeHelper.loadObject(getActivity().getApplicationContext(), AuthenticationUser.AUTH_USER_FILE_NAME);
            if (!authenticationUser.isEmpty()) {
                Log.d(LoginFragment.class.getName(), "Token file found");
                showMainActivity();
            } else {
                Log.d(LoginFragment.class.getName(), "Corrupt token file");
                getActivity().getApplicationContext().deleteFile(AuthenticationUser.AUTH_USER_FILE_NAME);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == this.btnLogin.getId()) {
            this.onLoginClick(view);
        } else if (view.getId() == this.btnRegister.getId()) {
            this.onRegisterClick(view);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login, container, false);
    }

    protected void onLoginClick(View view) {
        Log.i(LoginFragment.class.getName(), "onLoginClick");
        // Reset errors text
        this.txtEmail.setError(null);
        this.txtPassword.setError(null);

        // Store values at the time of the login attempt.
        String email = txtEmail.getText().toString().toLowerCase();
        String password = txtPassword.getText().toString();

        if (password == null || password.trim().equals("") || (password.length() < 4)) {
            // Check for a valid password, if the user entered one.
            this.txtPassword.setError(getString(R.string.error_invalid_password));
            this.txtPassword.requestFocus();
        } else if (email == null || email.trim().equals("")) {
            // Error email is empty
            this.txtEmail.setError(getString(R.string.error_field_required));
            this.txtEmail.requestFocus();
        } else if (!email.contains("@") && !email.contains(".")) {
            // Error text is not email
            this.txtEmail.setError(getString(R.string.error_invalid_email));
            this.txtEmail.requestFocus();
        } else {
            showProgress(true);
            LoginUser loginUser = new LoginUser();
            loginUser.email = txtEmail.getText().toString().trim();
            loginUser.password = txtPassword.getText().toString().trim();
            authenticationService.login(loginUser);
        }
    }



    protected void onRegisterClick(View view) {
        Log.i(LoginFragment.class.getName(), "onRegisterClick");
        ((MainActivity) getActivity()).showRegisterFragment();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // super.onViewCreated(view,savedInstanceState);

        this.btnLogin = (Button) getActivity().findViewById(R.id.btnLogin);
        this.btnRegister = (Button) getActivity().findViewById(R.id.btnRegister);
        this.txtEmail = (EditText) getActivity().findViewById(R.id.txtEmail);
        this.txtPassword = (EditText) getActivity().findViewById(R.id.txtPassword);
        this.viewLogin = getActivity().findViewById(R.id.layout_login);
        this.viewProgress = getActivity().findViewById(R.id.login_progress);

        this.btnLogin.setOnClickListener(this);
        this.btnRegister.setOnClickListener(this);

        txtEmail.setText("test@koopey.com");
        txtPassword.setText("I have a secret!");

        authenticationService = new AuthenticationService(this.getContext());
        authenticationService.setOnLoginListener(this);
        // checkPreviousAuthentication();
    }

    private void showMainActivity() {
        getActivity().finish();
        getActivity().overridePendingTransition(0, 0);
        startActivity(getActivity().getIntent());
        getActivity().overridePendingTransition(0, 0);
    }

    private void showProgress(final boolean show) {
        //Shows the progress UI and hides the login form.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            this.viewLogin.setVisibility(show ? View.GONE : View.VISIBLE);
            this.viewLogin.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    viewLogin.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            this.viewLogin.setVisibility(show ? View.VISIBLE : View.GONE);
            this.viewLogin.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    viewProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show and hide the relevant UI components.
            this.viewProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            this.viewLogin.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public void onUserLogin(int code, String message, AuthenticationUser authenticationUser) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "success", Toast.LENGTH_LONG).show();
            TagService tagService = new TagService(this.getContext());
            tagService.searchTags();
            Log.i(LoginFragment.class.getName(), "get tags");
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
            Log.i(LoginFragment.class.getName(), "fail");
        }
    }

    @Override
    public void onTagSearch(Tags tags) {
        this.showProgress(false);
        Log.i(LoginFragment.class.getName(), "" + tags.size());
        this.showMainActivity();
    }
}
