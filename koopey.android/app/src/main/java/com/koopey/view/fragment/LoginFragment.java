package com.koopey.view.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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
import com.koopey.controller.GetJSON;
import com.koopey.controller.PostJSON;
import com.koopey.model.Alert;
import com.koopey.model.Assets;
import com.koopey.model.AuthUser;
import com.koopey.model.Tags;
import com.koopey.model.Transactions;
import com.koopey.view.PrivateActivity;
import com.koopey.view.PublicActivity;

public class LoginFragment extends Fragment implements GetJSON.GetResponseListener, PostJSON.PostResponseListener, View.OnClickListener {
    private AuthUser authUser;
    private EditText txtEmail, txtPassword;
    private Button btnLogin, btnRegister;
    private Tags tags;
    private View viewProgress;
    private View viewLogin;


    private void downloadTags() {
        if (SerializeHelper.hasFile(this.getActivity(), Tags.TAGS_FILE_NAME)) {
            //  Log.d(LOG_HEADER, "Tag file found");
            tags = (Tags) SerializeHelper.loadObject(this.getActivity(), Tags.TAGS_FILE_NAME);
        } else {
            // Log.d(LOG_HEADER, "No tag file found");
            tags = new Tags();
            GetJSON asyncTask = new GetJSON(this.getActivity());
            asyncTask.delegate = this;
            asyncTask.execute(this.getString(R.string.get_tags_read), "", "");
        }
    }

    private void checkPreviousAuthentication() {
        if (SerializeHelper.hasFile(this.getActivity(), AuthUser.AUTH_USER_FILE_NAME)) {
            this.authUser = (AuthUser) SerializeHelper.loadObject(getActivity().getApplicationContext(), AuthUser.AUTH_USER_FILE_NAME);

            if (this.authUser.hasToken()) {
                //Already logged in go straight to main application
                Log.d(LoginFragment.class.getName(), "MyUser file found");
                showPrivateActivity();
            }
            if (this.authUser != null && this.authUser.getToken().equals("") && this.authUser.email.equals("")) {
                //Check for corrupt file
                Log.d(LoginFragment.class.getName(), "Found corrupt file");
                getActivity().getApplicationContext().deleteFile(AuthUser.AUTH_USER_FILE_NAME);
            }
        } else {
            this.authUser = new AuthUser();
        }
    }

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == this.btnLogin.getId()) {
                this.onLoginClick(v);
            } else if (v.getId() == this.btnRegister.getId()) {
                this.onRegisterClick(v);
            }
        } catch (Exception ex) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onGetResponse(String output) {
        showProgress(false);
        try {
            String header = (output.length() >= 20) ? output.substring(0, 19).toLowerCase() : output;
            if (header.contains("alert")) {
                Alert alert = new Alert();
                alert.parseJSON(output);
                if (alert.isSuccess()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.info_authentication), Toast.LENGTH_LONG).show();
                } else if (alert.isError()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_authentication), Toast.LENGTH_LONG).show();
                }
            } else if (header.contains("assets")) {
                Assets assets = new Assets(Assets.MY_ASSETS_FILE_NAME);
                assets.parseJSON(output);
                SerializeHelper.saveObject(getActivity(), assets);
            } else if (header.contains("tags")) {
                Tags tags = new Tags();
                tags.parseJSON(output);
                SerializeHelper.saveObject(getActivity(), tags);
            } else if (header.contains("transactions")) {
                Transactions transactions = new Transactions();
                transactions.parseJSON(output);
                SerializeHelper.saveObject(getActivity(), transactions);
            } else if (header.contains("user")) {
                authUser = new AuthUser();
                authUser.parseJSON(output);
                authUser.print();
                Toast.makeText(getActivity(), getResources().getString(R.string.info_authentication), Toast.LENGTH_SHORT).show();
                SerializeHelper.saveObject(getActivity(), authUser);
                showPrivateActivity();
            }
        } catch (Exception ex) {
            Log.d(LoginFragment.class.getName(), ex.getMessage());
        }
    }

    protected void onLoginClick(View view) {
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
            // Show a progress spinner, and kick off a background task to perform the user login attempt.
            showProgress(true);
            postAuthentication();
        }
    }

    @Override
    public void onPostResponse(String output) {
        showProgress(false);
        try {
            String header = (output.length() >= 20) ? output.substring(0, 19).toLowerCase() : output;
            if (header.contains("alert")) {
                Alert alert = new Alert();
                alert.parseJSON(output);
                if (alert.isSuccess()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.info_authentication), Toast.LENGTH_LONG).show();
                } else if (alert.isError()) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.error_authentication), Toast.LENGTH_LONG).show();
                }
            } else if (header.contains("user")) {

                this.authUser = new AuthUser();
                this.authUser.parseJSON(output);
                this.authUser.print();
                Toast.makeText(getActivity(), getResources().getString(R.string.info_authentication), Toast.LENGTH_SHORT).show();
                SerializeHelper.saveObject(getActivity(), authUser);
                showPrivateActivity();
            }
        } catch (Exception ex) {
            Log.d(LoginFragment.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState) {
        //  super.on.onCreate(savedInstanceState);

        this.btnLogin = (Button) getActivity().findViewById(R.id.btnLogin);
        this.btnRegister = (Button) getActivity().findViewById(R.id.btnRegister);
        this.txtEmail = (EditText) getActivity().findViewById(R.id.txtEmail);
        this.txtPassword = (EditText) getActivity().findViewById(R.id.txtPassword);
        this.viewLogin = getActivity().findViewById(R.id.layLogin);
        this.viewProgress = getActivity().findViewById(R.id.login_progress);

        this.btnLogin.setOnClickListener(this);
        this.btnRegister.setOnClickListener(this);

        txtEmail.setText("moleisking@gmail.com");
        txtPassword.setText("12345");

        downloadTags();
        checkPreviousAuthentication();
    }

    protected void onRegisterClick(View view) {
        ((PublicActivity) getActivity()).showRegisterFragment();
    }

    protected void exit() {
        getActivity().finish();
        System.exit(0);
    }

    private void postAuthentication() {
        AuthUser myUser = new AuthUser();
        myUser.email = txtEmail.getText().toString().trim();
        myUser.password = txtPassword.getText().toString();
        PostJSON asyncTask = new PostJSON(getActivity());
        asyncTask.delegate = this;
        asyncTask.execute(getResources().getString(R.string.post_auth_login), myUser.toString(), "");
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
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

    private void showPrivateActivity() {
        //Note* intent.putExtra("MyUser", myUser) creates TransactionTooLargeException
        Intent intent = new Intent(getActivity(), PrivateActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}
