package com.koopey.view.fragment;


import android.app.Fragment;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RatingBar;

import com.koopey.R;

import com.koopey.view.PrivateActivity;

/**
 * Created by Scott on 03/02/2017.
 */
public class ReviewReadFragment extends Fragment  {


    private EditText txtComment;
    private RatingBar ratReview;

    private FloatingActionButton btnThumbUp, btnThumbDown;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.txtComment = (EditText)getActivity().findViewById(R.id.txtComment);
        this.btnThumbUp = (FloatingActionButton)getActivity().findViewById(R.id.btnThumbUp);
        this.btnThumbDown = (FloatingActionButton)getActivity().findViewById(R.id.btnThumbDown);
        //ratReview = (RatingBar) getActivity().findViewById(R.id.ratReview);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)    {
        super.onCreate(savedInstanceState);

        ((PrivateActivity) getActivity()).setTitle(getResources().getString(R.string.label_review));
        ((PrivateActivity) getActivity()).hideKeyboard();

    /*    this.authUser = ((PrivateActivity)getActivity()).getAuthUserFromFile();
        if (this.getActivity().getIntent().hasExtra("review")) {
            this.review = (Review) getActivity().getIntent().getSerializableExtra("review");
        } else if (SerializeHelper.hasFile(this.getActivity(), Review.REVIEW_FILE_NAME)) {
            this.review = (Review) SerializeHelper.loadObject(this.getActivity(), Review.REVIEW_FILE_NAME);
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)    {
        return inflater.inflate(R.layout.fragment_review_read, container, false);
    }



    @Override
    public void onStart(){
        super.onStart();
       /* if (this.review != null){
            this.txtComment.setText(this.review.comment);
            if(this.review.value == 0){
                this.btnThumbDown.setVisibility(View.VISIBLE);
                this.btnThumbUp.setVisibility(View.GONE);
            } else if(this.review.value >= 1){
                this.btnThumbDown.setVisibility(View.GONE);
                this.btnThumbUp.setVisibility(View.VISIBLE);
            }
        }*/
    }
}
