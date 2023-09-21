package com.koopey.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.koopey.R;

public class AboutFragment extends Fragment {

    private TextView txtName, txtDescription, txtAddress;
    private ImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.txtName = (TextView)getActivity().findViewById(R.id.lblName);
        this.txtDescription = (TextView)getActivity().findViewById(R.id.lblDescription);
        this.txtAddress = (TextView)getActivity().findViewById(R.id.lblAddress);
        this.img = (ImageView)getActivity().findViewById(R.id.img);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)    {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }
}
