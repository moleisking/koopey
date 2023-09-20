package com.koopey.view.fragment;

import android.app.Activity;

import android.app.FragmentManager;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.koopey.R;
import com.koopey.model.User;
import com.koopey.view.PrivateActivity;
import com.koopey.view.PublicActivity;


public class AboutFragment extends Fragment implements View.OnClickListener {


    private TextView txtName, txtDescription, txtAddress;
    private ImageView img;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.txtName = (TextView)getActivity().findViewById(R.id.lblName);
        this.txtDescription = (TextView)getActivity().findViewById(R.id.lblDescription);
        this.txtAddress = (TextView)getActivity().findViewById(R.id.lblAddress);
        this.img = (ImageView)getActivity().findViewById(R.id.img);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
       // ((PublicActivity) getActivity()).setTitle(getResources().getString(R.string.label_about));
        ((PublicActivity) getActivity()).hideKeyboard();
    }

    @Override
    public void onClick(View v) {
       /* User user ;
        user.id = "0";
        user.username = "Administrator";
        user.name = "Administrator";
        user.email =  "moleisking@gmail.com";
        this.getActivity().getIntent().putExtra("User", user );
        ((PrivateActivity)getActivity()).showMessageListFragment();
        Toast.makeText(this.getActivity(), "Message", Toast.LENGTH_LONG).show();*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)    {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}
