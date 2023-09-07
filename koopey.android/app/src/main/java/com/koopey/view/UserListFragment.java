package com.koopey.view;

/**
 * Created by Scott on 12/08/2016.
 */
import android.app.Activity;


import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.ListFragment;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.adapter.UserAdapter;
import com.koopey.model.User;
import com.koopey.model.Users;

public class UserListFragment extends ListFragment {

    private final String LOG_HEADER = "USER:LIST";
    private final int USER_LIST_FRAGMENT = 305;
    private Users users;

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(this.users != null) {
            UserAdapter usersAdapter = new UserAdapter(this.getActivity(), this.users);
            this.setListAdapter(usersAdapter);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivity) getActivity()).setTitle(getResources().getString(R.string.label_users));
        ((MainActivity) getActivity()).hideKeyboard();
    }

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)    {
        return inflater.inflate(R.layout.fragment_users, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try        {
            if(this.users != null) {
                User user = this.users.get(position);
                ((MainActivity) getActivity()).showUserReadFragment(user);
            }
        } catch (Exception ex){
            Log.d(LOG_HEADER + ":ER", ex.getMessage());
        }
    }
}
