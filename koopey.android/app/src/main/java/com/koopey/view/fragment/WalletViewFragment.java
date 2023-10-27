package com.koopey.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.koopey.R;
import com.koopey.helper.QRCodeHelper;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Wallet;
import com.koopey.view.MainActivity;

public class WalletViewFragment extends Fragment  {
    private TextView  txtCurrency, txtDescription, txtName, txtValue;
    private ImageView imgQRCode;
    private Wallet wallet = new Wallet();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).hideKeyboard();
        if(this.getActivity().getIntent().hasExtra("wallet")) {
            wallet = (Wallet) this.getActivity().getIntent().getSerializableExtra("wallet");
        } else if (SerializeHelper.hasFile(this.getActivity(), Wallet.WALLET_FILE_NAME)) {
            wallet = (Wallet) SerializeHelper.loadObject(this.getActivity(), Wallet.WALLET_FILE_NAME);
        } else {
            wallet = new Wallet();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wallet_view, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        imgQRCode.setImageBitmap(QRCodeHelper.StringToQRCodeBitmap(wallet.getId()));
        txtCurrency.setText(wallet.getCurrency());
        txtDescription.setText(wallet.getDescription());
        txtName.setText(wallet.getName());
        txtValue.setText(wallet.getValueAsString());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgQRCode = getActivity().findViewById(R.id.imgQRCode);
        txtCurrency = getActivity().findViewById(R.id.txtCurrency);
        txtDescription = getActivity().findViewById(R.id.txtDescription);
        txtName = getActivity().findViewById(R.id.txtName);
        txtValue = getActivity().findViewById(R.id.txtValue);
    }

}
