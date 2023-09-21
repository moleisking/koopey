package com.koopey.view.fragment;


import android.app.Activity;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.koopey.R;
import com.koopey.controller.PostJSON;
import com.koopey.model.Asset;
import com.koopey.model.User;
import com.koopey.view.PrivateActivity;

/**
 * Created by Scott on 03/02/2017.
 */
public class ReviewCreateFragment extends Fragment implements  View.OnClickListener {

    private EditText txtComment;
    // private RatingBar ratReview;
    private User user;
    private Asset asset;
    private FloatingActionButton btnThumbUp, btnThumbDown, btnCancel;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.txtComment = (EditText) getActivity().findViewById(R.id.txtComment);
        this.btnThumbUp = (FloatingActionButton) getActivity().findViewById(R.id.btnThumbUp);
        this.btnThumbDown = (FloatingActionButton) getActivity().findViewById(R.id.btnThumbDown);
        this.btnCancel = (FloatingActionButton) getActivity().findViewById(R.id.btnCancel);
        //ratReview = (RatingBar) getActivity().findViewById(R.id.ratReview);
    }



    @Override
    public void onClick(View v) {
      /*  try {
            this.review = new Review();
            this.review.judgeId = this.myUser.id;
            this.review.comment = this.txtComment.getText().toString();
            if (v.getId() == btnThumbUp.getId()) {
                this.review.value = 1;
                this.postCreateReview();
            } else if (v.getId() == btnThumbDown.getId()) {
                this.review.value = 0;
                this.postCreateReview();
            } else if (v.getId() == btnCancel.getId()) {
                ((PrivateActivity) getActivity()).showUserReadFragment(user);
            }
        } catch (Exception ex) {
            Log.d(LOG_HEADER + ":ER", ex.getMessage());
        }*/
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      /*  this.myUser = ((PrivateActivity) getActivity()).getAuthUserFromFile();

        if (this.getActivity().getIntent().hasExtra("user")) {
            this.user = (User) getActivity().getIntent().getSerializableExtra("user");
            this.review.userId = this.user.id;
        }

        if (this.getActivity().getIntent().hasExtra("asset")) {
            this.asset = (Asset) getActivity().getIntent().getSerializableExtra("asset");
            this.review.productId = this.asset.id;
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_review_create, container, false);
    }


   /* private void postReadReview() {
         if (this.asset != null) {
            PostJSON asyncTask = new PostJSON(this.getActivity());
            asyncTask.delegate = this;
            asyncTask.execute(getResources().getString(R.string.post_review_create), this.review.toString(), this.myUser.getToken());
        }
    }*/

    private void postCreateReview() {
        if (this.asset != null) {
            PostJSON asyncTask = new PostJSON(this.getActivity());
           // asyncTask.delegate = this;
         //   asyncTask.execute(getResources().getString(R.string.post_review_create), this.review.toString(), this.myUser.getToken());
        }
    }

    public interface OnReviewCreateFragmentListener {
       // void onReviewCreate(Review review);

    }
}
