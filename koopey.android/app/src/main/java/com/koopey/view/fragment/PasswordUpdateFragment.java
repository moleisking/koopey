package com.koopey.view.fragment;


import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


import com.koopey.R;
import com.koopey.helper.HashHelper;

import com.koopey.model.Alert;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.authentication.ChangePassword;
import com.koopey.service.AuthenticationService;
import com.koopey.view.PrivateActivity;

public class PasswordUpdateFragment extends Fragment implements View.OnClickListener {


    private final int PASSWORD_UPDATE_FRAGMENT = 502;
    private EditText  txtPasswordOld, txtPasswordNew;
       private FloatingActionButton btnUpdate;
    private AuthenticationUser authenticationUser;

    private AuthenticationService authenticationService;

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == btnUpdate.getId()) {
                ChangePassword changePassword =   new ChangePassword();
                if (!txtPasswordNew.getText().equals("")) {
                    changePassword.newPassword = txtPasswordNew.getText().toString();
                }
                if (!txtPasswordOld.getText().equals("")) {
                    changePassword.oldPassword = txtPasswordOld.getText().toString();
                }
                //authUser.hash = HashHelper.parseMD5(authUser.toString());
                //Post new data
                authenticationService.changePassword(changePassword);
            }
        } catch (Exception ex) {
            Log.d(PasswordUpdateFragment.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authenticationService = new AuthenticationService(getContext());
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();
        //Define views
        this.btnUpdate = (FloatingActionButton) getActivity().findViewById(R.id.btnUpdate);
        this.txtPasswordOld = (EditText) getActivity().findViewById(R.id.txtName);
        this.txtPasswordNew = (EditText) getActivity().findViewById(R.id.txtEmail);

        //Set listeners
        this.btnUpdate.setOnClickListener(this);

        //Populate controls
        //this.populateUser();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_password_update, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
    }



  /*  private void postPasswordChange() {
        if (this.authUser != null) {
            PostJSON asyncTask = new PostJSON(this.getActivity());
            asyncTask.delegate = this;
            asyncTask.execute(getResources().getString(R.string.post_auth_password_change), authUser.toString(), ((PrivateActivity) getActivity()).getAuthUserFromFile().getToken());
        }
    }

    private void postPasswordForgotten() {
        if (this.authUser != null) {
            PostJSON asyncTask = new PostJSON(this.getActivity());
            asyncTask.delegate = this;
            asyncTask.execute(getResources().getString(R.string.post_auth_password_forgotten), authUser.toString(), ((PrivateActivity) getActivity()).getAuthUserFromFile().getToken());
        }
    }*/
}