package com.koopey.view.fragment;


import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import androidx.fragment.app.Fragment;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;

import com.koopey.model.Search;
import com.koopey.model.Tags;
import com.koopey.model.Users;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AuthenticationService;
import com.koopey.service.TagService;
import com.koopey.service.UserService;
import com.koopey.view.PrivateActivity;

//import org.florescu.android;

//import android.support.v4.app.Fragment;

public class UserSearchFragment extends Fragment implements  View.OnClickListener, UserService.UserSearchListener {

    private MultiAutoCompleteTextView lstTags;
    private Tags tags;
    private Search search = new Search();
    private Users users ;
    private AuthenticationUser authenticationUser ;
    private EditText txtAlias, txtName;
    private FloatingActionButton btnSearch;

    private UserService userService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PrivateActivity) getActivity()).hideKeyboard();

        userService = new UserService(getContext());

        this.lstTags = (MultiAutoCompleteTextView) getActivity().findViewById(R.id.lstTags);
        this.txtName = (EditText) getActivity().findViewById(R.id.txtName);
        this.txtAlias = (EditText) getActivity().findViewById(R.id.txtAlias);
        this.btnSearch = (FloatingActionButton) getActivity().findViewById(R.id.btnSearch);
        this.btnSearch.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_search_name, container, false);
    }

    @Override
    public void onClick(View v) {
        search.setAlias(this.txtAlias.getText().toString());
        search.setAlias(this.txtName.getText().toString());
        search.setType("Users");
        userService.searchUser(search);
    }


    @Override
    public void onUserSearch(int code, String message, Users users) {
        this.getActivity().getIntent().putExtra("users", this.users );
    }
}
