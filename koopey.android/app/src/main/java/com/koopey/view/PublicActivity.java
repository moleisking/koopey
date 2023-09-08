package com.koopey.view;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.koopey.R;

import com.koopey.view.fragment.AboutFragment;
import com.koopey.view.fragment.LoginFragment;
import com.koopey.view.fragment.RegisterFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

/**
 * A login screen that offers login via email/password.
 */
/*, LoaderCallbacks<Cursor> ,*/
public class PublicActivity extends AppCompatActivity /*implements GetJSON.GetResponseListener, PostJSON.PostResponseListener, View.OnClickListener*/ {

   // private final String LOG_HEADER = "LOGIN:ACTIVITY:";
   /* private EditText txtEmail,  txtPassword;
    private Button btnLogin, btnRegister;
    private View mProgressView;
    private View mLoginFormView;
    private AuthUser authUser;
    private Tags tags;*/

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_login_frame, new LoginFragment())
                .addToBackStack("fragment_about")
                .commit();


          this.toolbar = (Toolbar) findViewById(R.id.toolbar_login);
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.k);
            toolbar.setTitle("Test123");
          /*  getSupportActionBar().setIcon(R.drawable.k);
            getSupportActionBar().setLogo(R.drawable.k);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.k);*/

           // toolbar.setOverflowIcon(ContextCompat.getDrawable(this, R.drawable.k));
           // this.toolbar.
            //Define views
           // this.mLoginFormView = findViewById(R.id.layLogin);
          //  this.mProgressView = findViewById(R.id.login_progress);
          /*  this.txtEmail = (EditText) findViewById(R.id.txtEmail);
            this.txtPassword = (EditText) findViewById(R.id.txtPassword);
            this.btnLogin = (Button) findViewById(R.id.btnLogin);
            this.btnRegister = (Button) findViewById(R.id.btnRegister);

            //Set Listeners
            this.btnLogin.setOnClickListener(this);
            this.btnRegister.setOnClickListener(this);


            txtEmail.setText("moleisking@gmail.com");
            txtPassword.setText("12345");
            //Download tags
            if (SerializeHelper.hasFile(this, Tags.TAGS_FILE_NAME)) {
                Log.d(LOG_HEADER, "Tag file found");
                tags = (Tags) SerializeHelper.loadObject(this, Tags.TAGS_FILE_NAME);
            } else {
                Log.d(LOG_HEADER, "No tag file found");
                tags = new Tags();
                getTags();
            }
            //Check if user has logged in previously
            if (SerializeHelper.hasFile(this, AuthUser.AUTH_USER_FILE_NAME)) {
                this.authUser = (AuthUser) SerializeHelper.loadObject(getApplicationContext(), AuthUser.AUTH_USER_FILE_NAME);

                if (this.authUser.hasToken()) {
                    //Already logged in go straight to main application
                    Log.d(LOG_HEADER, "MyUser file found");
                    showPrivateActivity();
                }
                if (this.authUser != null && this.authUser.getToken().equals("") && this.authUser.email.equals("")) {
                    //Check for corrupt file
                    Log.d(LOG_HEADER, "Found corrupt file");
                    deleteFile(AuthUser.AUTH_USER_FILE_NAME);
                }
            } else {
                this.authUser = new AuthUser();
            }*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_unauthenticated_drawer, menu);
        return true;
    }

    /*@Override
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
    public void onGetResponse(String output) {
        showProgress(false);
        try {
            String header = (output.length() >= 20) ? output.substring(0, 19).toLowerCase() : output;
            if (header.contains("alert")) {
                Alert alert = new Alert();
                alert.parseJSON(output);
                if (alert.isSuccess()) {
                    Toast.makeText(this, getResources().getString(R.string.info_authentication), Toast.LENGTH_LONG).show();
                } else if (alert.isError()) {
                    Toast.makeText(this, getResources().getString(R.string.error_authentication), Toast.LENGTH_LONG).show();
                }
            } else if (header.contains("assets")) {
                Assets assets = new Assets(Assets.MY_ASSETS_FILE_NAME);
                assets.parseJSON(output);
                SerializeHelper.saveObject(this, assets);
            } else if (header.contains("tags")) {
                Tags tags = new Tags();
                tags.parseJSON(output);
                SerializeHelper.saveObject(this, tags);
            } else if (header.contains("transactions")) {
                Transactions transactions = new Transactions();
                transactions.parseJSON(output);
                SerializeHelper.saveObject(this, transactions);
            } else if (header.contains("user")) {
                authUser = new AuthUser();
                authUser.parseJSON(output);
                authUser.print();
                Toast.makeText(this, getResources().getString(R.string.info_authentication), Toast.LENGTH_SHORT).show();
                SerializeHelper.saveObject(this, authUser);
                showPrivateActivity();
            }
        } catch (Exception ex) {
            Log.d(LOG_HEADER + ":ER", ex.getMessage());
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
                    Toast.makeText(this, getResources().getString(R.string.info_authentication), Toast.LENGTH_LONG).show();
                } else if (alert.isError()) {
                    Toast.makeText(this, getResources().getString(R.string.error_authentication), Toast.LENGTH_LONG).show();
                }
            } else if (header.contains("user")) {

                this.authUser = new AuthUser();
                this.authUser.parseJSON(output);
                this.authUser.print();
                Toast.makeText(this, getResources().getString(R.string.info_authentication), Toast.LENGTH_SHORT).show();
                SerializeHelper.saveObject(this, authUser);
                showPrivateActivity();
            }
        } catch (Exception ex) {
            Log.d(LOG_HEADER + ":ER", ex.getMessage());
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        //Shows the progress UI and hides the login form.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            this.mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            this.mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            this.mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            this.mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show and hide the relevant UI components.
            this.mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            this.mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
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

    protected void onRegisterClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }*/

    private void showPrivateActivity() {
        //Note* intent.putExtra("MyUser", myUser) creates TransactionTooLargeException
        Intent intent = new Intent(this, PrivateActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void showLoginFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_main_frame, new LoginFragment())
                .addToBackStack("fragment_login")
                .commit();
    }

    public void showRegisterFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_main_frame, new RegisterFragment())
                .addToBackStack("fragment_register")
                .commit();
    }

    public void showAboutFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_main_frame, new AboutFragment())
                .addToBackStack("fragment_about")
                .commit();
    }

    public void hideKeyboard() {
        //getWindow().setSoftInputMode(
        //        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        //);
        View currentView = this.getCurrentFocus();
        if (currentView != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(currentView.getWindowToken(), 0);
        }
    }

   /* protected void getMyAssets(AuthUser myUser) {
        GetJSON asyncTask = new GetJSON(this);
        asyncTask.delegate = this;
        asyncTask.execute(this.getString(R.string.get_assets_read_mine), "", myUser.getToken());
    }

    protected void getTransactions(AuthUser myUser) {
        GetJSON asyncTask = new GetJSON(this);
        asyncTask.delegate = this;
        asyncTask.execute(this.getString(R.string.get_transaction_read_many), "", myUser.getToken());
    }

    protected void getTags() {
        GetJSON asyncTask = new GetJSON(this);
        asyncTask.delegate = this;
        asyncTask.execute(this.getString(R.string.get_tags_read), "", "");
    }

    private void postAuthentication() {
        AuthUser myUser = new AuthUser();
        myUser.email = txtEmail.getText().toString().trim();
        myUser.password = txtPassword.getText().toString();
        PostJSON asyncTask = new PostJSON(this);
        asyncTask.delegate = this;
        asyncTask.execute(getResources().getString(R.string.post_auth_login), myUser.toString(), "");
    }*/

    protected void exit() {
        this.finish();
        System.exit(0);
    }
}