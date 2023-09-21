package com.koopey.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.controller.PostJSON;
import com.koopey.model.Alert;
;
import com.koopey.model.Messages;
import com.koopey.service.MessageService;
import com.koopey.service.UserService;
import com.koopey.service.WalletService;
import com.koopey.view.PrivateActivity;

/**
 * Created by Scott on 21/07/2017.
 */
public class DashboardFragment extends Fragment implements  View.OnClickListener {

    private TextView txtUnread, txtUnsent, txtPositive, txtNegative;
    private RatingBar starAverage;
    private Switch btnAvailable, btnTrack;
    private Messages messages;
    private WalletListFragment frgWallets;

    UserService userService;

    MessageService messageService;

    WalletService walletService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
userService = new UserService(getContext());
messageService = new MessageService();
walletService = new WalletService();


        if (SerializeHelper.hasFile(this.getActivity(), Messages.MESSAGES_FILE_NAME)) {
            this.messages = (Messages) SerializeHelper.loadObject(this.getActivity(), Messages.MESSAGES_FILE_NAME);
        }

        this.txtPositive = (TextView) getActivity().findViewById(R.id.txtPositive);
        this.txtNegative = (TextView) getActivity().findViewById(R.id.txtNegative);
        this.txtUnread = (TextView) getActivity().findViewById(R.id.txtUnread);
        this.txtUnsent = (TextView) getActivity().findViewById(R.id.txtUnsent);
        this.starAverage = (RatingBar) getActivity().findViewById(R.id.starAverage);
        this.btnAvailable = (Switch) getActivity().findViewById(R.id.btnAvailable);
        this.btnTrack = (Switch) getActivity().findViewById(R.id.btnTrack);
        this.btnAvailable.setOnClickListener(this);
        this.btnTrack.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dashboard, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
      /*  if (this.authUser != null) {
            this.txtPositive.setText(String.valueOf(this.authUser.reviews.getPositive()));
            this.txtNegative.setText(String.valueOf(this.myUser.reviews.getNegative()));
            this.starAverage.setNumStars(this.myUser.reviews.getAverage());
        }*/
        if (this.messages != null) {
            this.txtUnread.setText(String.valueOf(this.messages.countUnread()));
            this.txtUnsent.setText(String.valueOf(this.messages.countUnsent()));
        }
        if (getResources().getBoolean(R.bool.transactions)) {
//            this.frgWallets.setVisibility(View.VISIBLE);
        } else {
           // this.frgWallets.setVisibility(View.INVISIBLE);
        }
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == btnAvailable.getId()) {
          userService.updateUserAvailable(this.btnAvailable.isChecked());
        } else if (v.getId() == btnTrack.getId()) {
            userService.updateUserTrack(this.btnTrack.isChecked());
        }
    }


}