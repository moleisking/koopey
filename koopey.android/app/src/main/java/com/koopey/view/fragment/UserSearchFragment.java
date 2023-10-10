package com.koopey.view.fragment;


import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.koopey.R;

import com.koopey.model.Search;
import com.koopey.model.Tags;
import com.koopey.model.Users;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.UserService;
import com.koopey.view.MainActivity;

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
        ((MainActivity) getActivity()).hideKeyboard();
        userService = new UserService(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.lstTags = (MultiAutoCompleteTextView) getActivity().findViewById(R.id.lstTags);
        this.txtName = (EditText) getActivity().findViewById(R.id.txtName);
        this.txtAlias = (EditText) getActivity().findViewById(R.id.txtAlias);
        this.btnSearch = (FloatingActionButton) getActivity().findViewById(R.id.btnSearch);
        this.btnSearch.setOnClickListener(this);
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
        Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_users);
    }
}
