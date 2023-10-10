package com.koopey.view.fragment;



import android.app.Activity;


import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.navigation.Navigation;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.adapter.UserAdapter;
import com.koopey.model.User;
import com.koopey.model.Users;
import com.koopey.view.MainActivity;

public class UserListFragment extends ListFragment {
    private final int USER_LIST_FRAGMENT = 305;
    private Users users;

    @Override
    public void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);
        if (getActivity().getIntent().hasExtra("users")) {
            this.users = (Users) getActivity().getIntent().getSerializableExtra("users");
        } else if(SerializeHelper.hasFile(this.getActivity() ,Users.USERS_FILE_NAME)) {
           this.users =  (Users) SerializeHelper.loadObject(this.getActivity() ,Users.USERS_FILE_NAME);
        } else {
            this.users = new Users();
        }


        if(this.users != null) {
            UserAdapter usersAdapter = new UserAdapter(this.getActivity(), this.users);
            this.setListAdapter(usersAdapter);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)    {
        return inflater.inflate(R.layout.user_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try        {
            if(this.users != null) {
                User user = this.users.get(position);
                Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_user_edit);
            }
        } catch (Exception ex){
            Log.d(UserListFragment.class.getSimpleName(), ex.getMessage());
        }
    }
}
