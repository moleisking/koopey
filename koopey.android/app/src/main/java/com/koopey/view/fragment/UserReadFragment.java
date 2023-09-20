package com.koopey.view.fragment;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
/*
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;*/

import com.koopey.R;
import com.koopey.helper.DistanceHelper;
import com.koopey.helper.ImageHelper;
import com.koopey.helper.SerializeHelper;
import com.koopey.controller.PostJSON;
import com.koopey.model.Alert;
import com.koopey.model.authentication.AuthenticationUser;

import com.koopey.model.User;
import com.koopey.service.AuthenticationService;
import com.koopey.service.UserService;
import com.koopey.view.PrivateActivity;

/*Note: No calls to server through ResponseAPI for profile. User object passed from ResultsFragment. UserAccount userId to post review though messages.*/
public class UserReadFragment extends Fragment implements  View.OnClickListener {

    private TextView txtAlias, txtName, txtDescription, txtAddress, txtDistance;
    private ImageView imgUser;

    AuthenticationService authenticationService;
    private AuthenticationUser authenticationUser ;

    UserService userService;
    private User user ;
    private FloatingActionButton btnMessage, btnUpdate;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.imgUser = (ImageView) getActivity().findViewById(R.id.imgUser);
        this.txtAlias = (TextView) getActivity().findViewById(R.id.txtAlias);
        this.txtName = (TextView) getActivity().findViewById(R.id.txtName);
        this.txtDescription = (TextView) getActivity().findViewById(R.id.txtDescription);
        this.txtDistance = (TextView) getActivity().findViewById(R.id.txtDistance);
        this.btnMessage = (FloatingActionButton) getActivity().findViewById(R.id.btnMessage);
        this.btnUpdate = (FloatingActionButton) getActivity().findViewById(R.id.btnUpdate);
        this.imgUser.setOnClickListener(this);
        this.btnMessage.setOnClickListener(this);
        this.btnUpdate.setOnClickListener(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authenticationService = new AuthenticationService(getContext());
        //Define myUser
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();

        userService = new UserService(getContext());

        //Define user object, which is passed from SearchFragment
        if (getActivity().getIntent().hasExtra("user")) {
            this.user = (User) getActivity().getIntent().getSerializableExtra("user");
            if (this.user.getType().equals("basic")) {
              //  userService.read();
            }
        } else if (SerializeHelper.hasFile(this.getActivity(), User.USER_FILE_NAME)) {
            this.user = (User) SerializeHelper.loadObject(this.getActivity(), User.USER_FILE_NAME);
        } else {
          //  this.user = new User();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_read, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.populateUser();
        this.setVisibility();

        if (this.authenticationUser.equals(this.user)) {
            ((PrivateActivity) getActivity()).setTitle(getResources().getString(R.string.label_my_user));
        } else {
            ((PrivateActivity) getActivity()).setTitle(getResources().getString(R.string.label_user));
        }
    }



    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == btnMessage.getId()) {
                //Show message fragment with current user
                this.getActivity().getIntent().putExtra("user", this.user);
                ((PrivateActivity) getActivity()).showMessageListFragment();
            } else if (v.getId() == btnUpdate.getId()) {
              //  ((PrivateActivity) getActivity()).showUserUpdateFragment();
            }
        } catch (Exception ex) {
            Log.d(UserReadFragment.class.getName(), ex.getMessage());
        }
    }

    public void setVisibility() {
        //Alias
        if (getResources().getBoolean(R.bool.alias)) {
            txtAlias.setVisibility(View.VISIBLE);
        } else {
            txtAlias.setVisibility(View.GONE);
        }
        //Button
        if (getResources().getBoolean(R.bool.alias)) {
            txtAlias.setVisibility(View.VISIBLE);
        } else {
            txtAlias.setVisibility(View.GONE);
        }
        //Name
        if (getResources().getBoolean(R.bool.name) && !getResources().getBoolean(R.bool.alias)) {
            txtName.setVisibility(View.VISIBLE);
        } else {
            txtName.setVisibility(View.GONE);
        }
        //Update
        if (this.authenticationUser.getId().equals(this.user.getId())) {
            btnUpdate.setVisibility(View.VISIBLE);
        } else {
            btnUpdate.setVisibility(View.GONE);
        }
    }

    public void populateUser() {
        if (this.user != null) {
            this.txtAlias.setText(this.user.getAlias());
            this.txtDescription.setText(this.user.getDescription());
            this.txtDistance.setText(DistanceHelper.DistanceToKilometers(this.user.getDistance()));
            //Set user or default image
            try {
                this.imgUser.setImageBitmap(ImageHelper.UriToBitmap(this.user.getAvatar()));
            } catch (Exception ex) {
            }
        } else {
            Log.d(UserReadFragment.class.getName(), "No user found");
        }
    }


}
