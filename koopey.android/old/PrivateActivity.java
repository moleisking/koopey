package com.koopey.view;

import com.google.android.material.navigation.NavigationView;
import com.koopey.R;
//import com.koopey.databinding.ActivityMainBinding;

import android.Manifest;
import android.annotation.TargetApi;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import com.koopey.helper.ImageHelper;
import com.koopey.helper.SerializeHelper;
import com.koopey.controller.LocationReceiver;
import com.koopey.model.Assets;
import com.koopey.model.Location;
import com.koopey.model.Tags;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AuthenticationService;
import com.koopey.service.PositionService;
import com.koopey.service.MessageService;
import com.koopey.controller.MessageReceiver;
import com.koopey.model.Alert;
import com.koopey.model.Locations;
import com.koopey.model.Bitcoin;
import com.koopey.model.Ethereum;
import com.koopey.model.Image;
import com.koopey.model.Images;
import com.koopey.model.Messages;
import com.koopey.model.Transaction;
import com.koopey.model.User;
import com.koopey.model.Users;

import com.koopey.service.TagService;
import com.koopey.view.fragment.AboutFragment;
import com.koopey.view.fragment.BarcodeReadFragment;
import com.koopey.view.fragment.CalendarFragment;
import com.koopey.view.fragment.ConfigurationFragment;
import com.koopey.view.fragment.ConversationListFragment;
import com.koopey.view.fragment.ImageListFragment;
import com.koopey.view.fragment.ImageReadFragment;
import com.koopey.view.fragment.ImageUpdateFragment;
import com.koopey.view.fragment.LocationEditFragment;
import com.koopey.view.fragment.LocationListFragment;
import com.koopey.view.fragment.LocationViewFragment;
import com.koopey.view.fragment.MessageListFragment;
import com.koopey.view.fragment.SearchUsersFragment;
import com.koopey.view.fragment.TagCreateFragment;
import com.koopey.view.fragment.TransactionEditFragment;
import com.koopey.view.fragment.TransactionListFragment;
import com.koopey.view.fragment.TransactionViewFragment;
import com.koopey.view.fragment.UserListFragment;
import com.koopey.view.fragment.UserMapFragment;
import com.koopey.view.fragment.UserViewFragment;
import com.koopey.view.fragment.UserSearchFragment;
import com.koopey.view.fragment.WalletListFragment;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        ImageListFragment.OnImageListFragmentListener, NavigationView.OnNavigationItemSelectedListener,
        MessageService.OnMessageListener /*, View.OnTouchListener*/ {

    public interface GPSListener {
        void onLocation(Location location);
    }

    private List<MainActivity.GPSListener> gpsListeners = new ArrayList<>();
   // private AppBarConfiguration appBarConfiguration;
   // private ActivityMainBinding binding;
   private ActionBarDrawerToggle actionBarDrawerToggle;
    private AppBarConfiguration appBarConfiguration;
    private AuthenticationService authenticationService;

    public AuthenticationUser authenticationUser;
    private DrawerLayout drawerLayout;
    private static final int PERMISSION_REQUEST = 1004;
       private Toolbar toolbar;
    private NavController navigationController;
    private NavigationView navigationView;
    private View headerLayout;
    private ImageView imgAvatar;
    private TextView txtAliasOrName, txtDescription;

    private PositionService gps;
    private Alert alert;
    private Bitcoin bitcoin;
    private Ethereum ethereum;
    private Point touch;
    //private GestureDetector gestureDetector;
    public Tags tags;
    public TagService tagService;

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_private);

        drawerLayout = findViewById(R.id.drawer_layout_public);
        navigationView = findViewById(R.id.drawer_toggle);
        navigationView.setNavigationItemSelectedListener(this);

        toolbar = findViewById(R.id.toolbar_public);
        setSupportActionBar(toolbar);

        navigationController = Navigation.findNavController(this, R.id.fragment_public);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        appBarConfiguration =
                new AppBarConfiguration.Builder(
                        R.id.navigation_results, R.id.navigation_my_locations, R.id.navigation_configuration, R.id.navigation_about)
                        .setOpenableLayout(drawerLayout)
                        .build();

        NavigationUI.setupWithNavController(toolbar, navigationController, appBarConfiguration);
        NavigationUI.setupActionBarWithNavController(this, navigationController, appBarConfiguration);

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(false);




        try {




            //Define views
            this.navigationView = (NavigationView) findViewById(R.id.drawer_layout_private);
            this.navigationView.setNavigationItemSelectedListener(this);
            this.headerLayout = navigationView.getHeaderView(0);
            this.imgAvatar = (ImageView) headerLayout.findViewById(R.id.nav_head_imgAvatar);
            this.txtAliasOrName = (TextView) headerLayout.findViewById(R.id.nav_head_txtAliasOrName);
            this.txtDescription = (TextView) headerLayout.findViewById(R.id.nav_head_txtDescription);

            headerLayout.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    showMyUserReadFragment();
                    hideDrawer();
                }
            });

            authenticationService = new AuthenticationService(this);
          //  this.authUser = authenticationService.getRemoteAuthenticationUser();

            //Load user passed from login via saved file
            if (authenticationService.hasAuthenticationUserFile()) {

            } else {
             //   this.navigationView.inflateMenu(R.menu.menu_unauthenticated_drawer);
               // showLoginActivity();
            }


            //Set default values
            if (getResources().getBoolean(R.bool.alias)) {
                this.txtAliasOrName.setText(authenticationUser.getUsername());
            } else {
                this.txtAliasOrName.setText(authenticationUser.getName());
            }
            this.txtDescription.setText(authenticationUser.getDescription());
            try {
                if (this.authenticationUser.getAvatar() != null) {
                    this.imgAvatar.setImageBitmap(ImageHelper.IconBitmap(this.authenticationUser.getAvatar()));
                } else {
                    this.imgAvatar.setImageDrawable(getResources().getDrawable(R.drawable.default_user));
                }
            } catch (Exception ex) {
                Log.d(MainActivity.class.getName(), "Avatar image not found");
            }

            //Set business model
            this.setVisibility();

            //Request permissions
            if (!this.hasPermissions()) {
                this.requestPermissions();
            }

            this.hideKeyboard();
            //Initialize gesture listener
            //CustomGestureDetector customGestureDetector = new CustomGestureDetector();
            //gestureDetector = new GestureDetector(this, customGestureDetector);
        } catch (Exception ex) {
            Log.d(MainActivity.class.getName(), ex.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_popup_private, menu);
        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        hideKeyboard();
        Log.i(MainActivity.class.getName(),item.getTitle().toString());
        NavController navController = Navigation.findNavController(this, R.id.fragment_public);
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //Set default fragment to show
        showPreviousResults();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST) {
            if (grantResults.length == 5 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[3] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[4] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[5] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[6] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[7] == PackageManager.PERMISSION_GRANTED) {
                Log.d(MainActivity.class.getName(), "onRequestPermissionsResult success");
            } else {
                Log.d(MainActivity.class.getName(), "onRequestPermissionsResult error");
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(MainActivity.class.getName(), "onRestart");
        startNotificationService();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(MainActivity.class.getName(), "onStart");
        startNotificationService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(MainActivity.class.getName(), "onStop");
        stopNotificationService();
    }

    private void showEmail(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, R.string.smtp_address);
        intent.putExtra(Intent.EXTRA_SUBJECT, R.string.smtp_subject);
        intent.putExtra(Intent.EXTRA_TEXT, R.string.smtp_content);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "Choose an Email client :"));
    }


    public AuthenticationUser getAuthenticationUser(){
        authenticationService = new AuthenticationService(this);
        return authenticationService.getLocalAuthenticationUserFromFile();
    }

    public Tags getTags(){
        tagService = new TagService(this);
        tags = tagService.getLocalTagsFromFile();
        return tags;
    }

    public void createImageListFragmentEvent(Image image) {
        Log.d(MainActivity.class.getName(), "createImageListFragmentEvent(Image image)");
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.toolbar_private);
        if (fragment != null) {
            if (fragment instanceof LocationEditFragment) {
               // ((LocationEditFragment) fragment).createImageListFragmentEvent(image);
            }
        }
    }

    public void deleteImageListFragmentEvent(Image image) {
        Log.d(MainActivity.class.getName(), "deleteImageListFragmentEvent(Image image)");
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.toolbar_private);
        if (fragment != null) {
            if (fragment instanceof LocationEditFragment) {
          //      ((LocationEditFragment) fragment).deleteImageListFragmentEvent(image);
            } else if (fragment instanceof LocationEditFragment) {
             //   ((LocationEditFragment) fragment).deleteImageListFragmentEvent(image);
            }
        }
    }

    public void exit() {
        this.finish();
        System.exit(0);
    }

    public void updateImageListFragmentEvent(Image image) {
        Log.d(MainActivity.class.getName(), "updateImageListFragmentEvent(Image image)");
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.toolbar_private);
        if (fragment != null) {
            if (fragment instanceof LocationEditFragment) {
              //((LocationEditFragment)fragment).updateImageListFragmentEvent(image);
            } else if (fragment instanceof LocationEditFragment) {
              //  ((LocationEditFragment) fragment).updateImageListFragmentEvent(image);
            }
        }
    }

    public void updateMessages(Messages messages) {
        Log.d(MainActivity.class.getName(), "updateMessages");

    }

    public void startNotificationService() {
        //Start message and location service
        MessageReceiver.startAlarm(getApplicationContext());
        LocationReceiver.startAlarm(getApplicationContext());
    }

    public void stopNotificationService() {
        //Stop message and location service
        MessageReceiver.stopAlarm(getApplicationContext());
        LocationReceiver.stopAlarm(getApplicationContext());
    }



    private void setVisibility() {
       /* //Products
        if (this.getResources().getBoolean(R.bool.products)) {
            navigationView.getMenu().findItem(R.id.nav_product_search).setVisible(true);
        } else {
            navigationView.getMenu().findItem(R.id.nav_product_search).setVisible(false);
        }
        //Services
        if (this.getResources().getBoolean(R.bool.services)) {
            navigationView.getMenu().findItem(R.id.nav_service_search).setVisible(true);
        } else {
            navigationView.getMenu().findItem(R.id.nav_service_search).setVisible(false);
        }
        //Transactions
        if (this.getResources().getBoolean(R.bool.transactions)) {
            navigationView.getMenu().findItem(R.id.nav_transactions).setVisible(true);
        } else {
            navigationView.getMenu().findItem(R.id.nav_transactions).setVisible(false);
        }*/
    }

    public void showAboutFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new AboutFragment())
                .addToBackStack("fragment_about")
                .commit();
    }

    protected void showBarcodeReadFragment(String barcode) {
        this.getIntent().putExtra("barcode", barcode);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new BarcodeReadFragment())
                .addToBackStack("fragment_barcode_read")
                .commit();
    }

    public void showBarcodeScannerFragment(Transaction transaction) {
      /*  this.getIntent().putExtra("transaction", transaction);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new BarcodeScannerFragment())
                .addToBackStack("fragment_barcode_scanner")
                .commit();*/
    }

    public void showCalendarFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new CalendarFragment())
                .addToBackStack("fragment_calendar")
                .commit();
    }

    public void showConversationListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new ConversationListFragment())
                .addToBackStack("fragment_conversations")
                .commit();
    }

    protected void showDashBoardFragment() {
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new DashboardFragment())
                .addToBackStack("fragment_dashboard")
                .commit();*/
    }



    public void showImageListFragment(Images images) {
        this.getIntent().putExtra("images", images);
        this.getIntent().putExtra("showCreateButton", true);
        this.getIntent().putExtra("showUpdateButton", true);
        this.getIntent().putExtra("showDeleteButton", true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new ImageListFragment())
                .addToBackStack("fragment_images")
                .commit();
    }

    public void showImageReadFragment(Image image) {
        this.getIntent().putExtra("image", image);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new ImageReadFragment())
                .addToBackStack("fragment_image_read")
                .commit();
    }

    public void showImageUpdateFragment(Images images) {
        this.getIntent().putExtra("images", images);
      
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new ImageUpdateFragment())
                .addToBackStack("fragment_image_update")
                .commit();
    }

    public void showLoginActivity() {
        Intent intent = new Intent(this, PublicActivity.class);
        startActivity(intent);
        this.finish();
    }

    public void showMessageListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new MessageListFragment())
                .addToBackStack("fragment_messages")
                .commit();
    }

   /* public void showMyLocationReadFragment(Location location) {
        //NOTE: Fragment will handle update permissions
        this.getIntent().putExtra("MyProduct", location);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, new LocationReadFragment())
                .addToBackStack("fragment_location_read")
                .commit();
        this.setTitle(getResources().getString(R.string.label_my_location));
    }*/

    public void showMyLocationListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new LocationListFragment())
                .addToBackStack("fragment_my_locations")
                .commit();
    }

    public void showMyUserReadFragment() {
        //NOTE:Fragment will handle update permissions
        User user = authenticationUser.getUser();
        this.getIntent().putExtra("user", user);
        this.getIntent().putExtra("showUpdateButton", true);
        this.getIntent().putExtra("showDeleteButton", true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new UserViewFragment())
                .addToBackStack("fragment_user_read")
                .commit();
        this.setTitle(getResources().getString(R.string.label_my_user));
    }

    public void showPreviousResults() {
        if (SerializeHelper.hasFile(this, Assets.ASSET_SEARCH_RESULTS_FILE_NAME)) {
            Locations locations = (Locations) SerializeHelper.loadObject(this, Assets.ASSET_SEARCH_RESULTS_FILE_NAME);
            if (locations == null || locations.isEmpty()) {
                this.showDashBoardFragment();
            } else {
                //this.showLocationListFragment();
            }
        } else if (SerializeHelper.hasFile(this, Users.USERS_FILE_NAME)) {
            Users users = (Users) SerializeHelper.loadObject(this, Users.USERS_FILE_NAME);
            if (users == null || users.isEmpty()) {
                this.showDashBoardFragment();
            } else {
               // this.showUserListFragment();
            }
        } else {
            this.showDashBoardFragment();
        }
    }


    public void showLocationCreateFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new LocationEditFragment())
                .addToBackStack("fragment_location_create")
                .commit();
    }

    public void showLocationListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new LocationListFragment())
                .addToBackStack("fragment_locations")
                .commit();
    }

    public void showLocationUpdateFragment(Location location) {
        //Note* Location object sent by list fragment
        this.getIntent().putExtra("location", location);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new LocationEditFragment())
                .addToBackStack("fragment_location_update")
                .commit();
    }

    public void showLocationReadFragment(Location location) {
        this.getIntent().putExtra("location", location);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new LocationViewFragment())
                .addToBackStack("fragment_location_read")
                .commit();
    }





    public void showTagCreateFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new TagCreateFragment())
                .addToBackStack("fragment_tag_create")
                .commit();
    }



    public void showTransactionCreateFragment(Transaction transaction) {
        this.getIntent().putExtra("transaction", transaction);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new TransactionEditFragment())
                .addToBackStack("fragment_transaction_create")
                .commit();
    }

    public void showTransactionReadFragment(Transaction transaction) {
        this.getIntent().putExtra("transaction", transaction);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new TransactionViewFragment())
                .addToBackStack("fragment_transaction_read")
                .commit();
    }

    public void showTransactionUpdateFragment(Transaction transaction) {
        this.showTransactionUpdateFragment(transaction, null);
    }

    public void showTransactionUpdateFragment(Transaction transaction, String barcode) {
        if ((barcode != null) && !barcode.equals("")) {
            this.getIntent().putExtra("barcode", barcode);
        }
        if (transaction != null) {
            this.getIntent().putExtra("transaction", transaction);
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new TransactionEditFragment())
                .addToBackStack("fragment_transaction_update")
                .commit();
    }

    public void showTransactionListFragment() {
        this.showTransactionListFragment(null);
    }

    public void showTransactionListFragment(Date date) {
        //Pass user object to profile fragment
        if (date != null) {
            this.getIntent().putExtra("date", date.getTime());
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new TransactionListFragment())
                .addToBackStack("fragment_transactions")
                .commit();
    }

    public void showTransactionSearchFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new SearchUsersFragment())
                .addToBackStack("fragment_transaction_search")
                .commit();
    }

    public void showUserListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new UserListFragment())
                .addToBackStack("fragment_users")
                .commit();
    }

    public void showUserMapFragment() {
        //Users users
        //this.getIntent().putExtra("users", users);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new UserMapFragment())
                .addToBackStack("fragment_user_map")
                .commit();
        this.setTitle(getResources().getString(R.string.label_map));
    }

    public void showUserNameSearchFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new UserSearchFragment())
                .addToBackStack("fragment_user_name_search")
                .commit();
    }

    public void showUserTagSearchFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new SearchUsersFragment())
                .addToBackStack("fragment_user_tag_search")
                .commit();
    }

    public void showUserReadFragment(User user) {
        //Note* User object sent by list fragment
        this.getIntent().putExtra("user", user);
        this.getIntent().putExtra("showUpdateButton", false);
        this.getIntent().putExtra("showDeleteButton", false);
        //Load profile fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new UserViewFragment())
                .addToBackStack("fragment_user_read")
                .commit();
        this.setTitle(getResources().getString(R.string.label_user));
    }

    public void showWalletListFragment() {
       // this.getIntent().putExtra("wallets", this.authenticationUser.wallets);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.toolbar_private, new WalletListFragment())
                .addToBackStack("fragment_wallets")
                .commit();
    }



    private void showWhatsApp() {
        Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
        whatsappIntent.setType("text/plain");
        whatsappIntent.setPackage("com.whatsapp");
        whatsappIntent.putExtra(Intent.EXTRA_TEXT, "The text you wanted to share");
        try {
            this.startActivity(whatsappIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT);
        }
    }


/*
    protected void showSavedFiles() {
        String[] savedFiles = getApplicationContext().fileList();
        Log.d("Shared Files", "Print");
        for (int i = 0; i < savedFiles.length; i++) {
            Log.d("File", savedFiles[i]);
        }
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }*/

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

    public void showKeyboard() {
        View currentView = this.getCurrentFocus();
        if (currentView != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInputFromWindow(currentView.getWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        }
    }

    private void hideDrawer() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_private);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    public void setToolbarUser(String uri, String alias) {
        //Set default image if uri is empty
        if (uri == null || uri.length() == 0) {
            uri = this.getString(R.string.default_user_image);
        }
        //Get controls
        ImageView imgAvatar = (ImageView) this.findViewById(R.id.imgAvatar);
        TextView txtAliasOrName = (TextView) this.findViewById(R.id.txtAlias);
        //Set image
        //Image image = new Image();
        //image.uri = uri;
        //toolbar.setLogo(image.getRoundBitmap());
        //Set alias
        this.setTitle(alias);
    }

    @TargetApi(23)
    private boolean hasPermissions() {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((this.checkSelfPermission(ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(INTERNET) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)
                    && (this.checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                return true;
            } else {
                return false;
            }
        } else if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) &&
                    (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @TargetApi(23)
    private void requestPermissions() {
        this.requestPermissions(new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION, CAMERA, INTERNET,
                READ_EXTERNAL_STORAGE, READ_PHONE_STATE, WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST);
    }



    class CustomGestureDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() > e2.getX()) {
                // Swipe left (next)
                Log.d("CustomGestureDetector", "Swipe left");
            } else if (e1.getX() < e2.getX()) {
                // Swipe right (previous)
                Log.d("CustomGestureDetector", "Swipe right");
            }
            if (e1.getY() > e2.getY()) {
                // Swipe down
                Log.d("CustomGestureDetector", "Swipe down");
            } else if (e1.getY() < e2.getY()) {
                // Swipe up
                Log.d("CustomGestureDetector", "Swipe up");
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    }
}