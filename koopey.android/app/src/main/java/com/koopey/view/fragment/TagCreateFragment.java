package com.koopey.view.fragment;



import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.koopey.R;

import com.koopey.model.Alert;
import com.koopey.model.Tag;

/**
 * Created by Scott on 07/10/2016.
 */
public class TagCreateFragment extends Fragment implements  View.OnClickListener {

    private TextView txtWord;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tag_create, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.txtWord = (TextView) getActivity().findViewById(R.id.txtName);
    }


    @Override
    public void onClick(View v) {
       /* Log.d("Tag:onSearchClick()", "Post");
        String url = getResources().getString(R.string.post_user_tag_search);
        Tag tag = new Tag();
        tag.en = txtWord.getText().toString();
        PostJSON asyncTask = new PostJSON();
        asyncTask.delegate = this;
        asyncTask.execute(url, tag.toString(), "");*/
    }

}
