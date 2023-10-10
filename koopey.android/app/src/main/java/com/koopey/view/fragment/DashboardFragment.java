package com.koopey.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
;
import com.koopey.model.Messages;
import com.koopey.service.MessageService;
import com.koopey.service.UserService;
import com.koopey.service.WalletService;

public class DashboardFragment extends Fragment implements View.OnClickListener {

    private Messages messages;
    private MessageService messageService;
    private RatingBar starAverage;
    private Switch btnAvailable, btnTrack;
    private TextView txtUnread, txtUnsent, txtPositive, txtNegative;
    private UserService userService;
    private WalletListFragment frgWallets;
    private WalletService walletService;

    @Override
    public void onClick(View v) {
        if (v.getId() == btnAvailable.getId()) {
            userService.updateAvailable(this.btnAvailable.isChecked());
        } else if (v.getId() == btnTrack.getId()) {
            userService.updateTrack(this.btnTrack.isChecked());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userService = new UserService(getContext());
        messageService = new MessageService(getContext());
        walletService = new WalletService(getContext());

        if (SerializeHelper.hasFile(this.getActivity(), Messages.MESSAGES_FILE_NAME)) {
            this.messages = (Messages) SerializeHelper.loadObject(this.getActivity(), Messages.MESSAGES_FILE_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dashboard, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtPositive = (TextView) getActivity().findViewById(R.id.txtPositive);
        txtNegative = (TextView) getActivity().findViewById(R.id.txtNegative);
        txtUnread = (TextView) getActivity().findViewById(R.id.txtUnread);
        txtUnsent = (TextView) getActivity().findViewById(R.id.txtUnsent);
        starAverage = (RatingBar) getActivity().findViewById(R.id.starAverage);
        btnAvailable = (Switch) getActivity().findViewById(R.id.btnAvailable);
        btnTrack = (Switch) getActivity().findViewById(R.id.btnTrack);

        btnAvailable.setOnClickListener(this);
        btnTrack.setOnClickListener(this);
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





}