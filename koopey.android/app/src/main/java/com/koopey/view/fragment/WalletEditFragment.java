package com.koopey.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.QRCodeHelper;
import com.koopey.model.Wallet;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.WalletService;
import com.koopey.view.MainActivity;

import java.net.HttpURLConnection;

public class WalletEditFragment extends Fragment implements View.OnClickListener, WalletService.WalletCrudListener {

    private AuthenticationUser authenticationUser;
    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;
    private EditText txtDescription, txtName, txtValue;
    private FloatingActionButton btnDelete, btnSave;
    private ImageView imgQrcode;
    private Spinner lstCurrency;
    private Wallet wallet = new Wallet();
    private WalletService walletService;

    private boolean checkForm() {
        if (txtName.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_name) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtValue.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_value) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == this.btnDelete.getId()) {
            walletService.delete(wallet);
        } else if (v.getId() == this.btnSave.getId() && checkForm()) {
            wallet.setCurrency(CurrencyHelper.currencySymbolToCode(lstCurrency.getSelectedItem().toString()));
            wallet.setDescription(txtDescription.getText().toString());
            wallet.setOwnerId(authenticationUser.getId());
            wallet.setName(txtName.getText().toString());
            wallet.setValue(Double.valueOf(txtValue.getText().toString()));
            if (wallet.getType().equals("create")) {
                wallet.setType("");
                walletService.create(wallet);
            } else {
                walletService.update(wallet);
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationUser = ((MainActivity) getActivity()).getAuthenticationUser();
        currencyCodeAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_codes, android.R.layout.simple_spinner_item);
        currencySymbolAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_symbols, android.R.layout.simple_spinner_item);
        walletService = new WalletService(getContext());
        walletService.setOnWalletCrudListener(this);

        if (this.getActivity().getIntent().hasExtra("wallet")) {
            wallet = (Wallet) this.getActivity().getIntent().getSerializableExtra("wallet");
        } else {
            wallet = Wallet.builder()
                    .currency(authenticationUser.getCurrency())
                    .ownerId(authenticationUser.getId())
                    .value(0.0d).build();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wallet_edit, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity().getIntent().hasExtra("wallet")) {
            getActivity().getIntent().removeExtra("wallet");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.wallet != null) {
            txtDescription.setText(wallet.getDescription());
            txtName.setText(wallet.getName());
            txtValue.setText(Double.toString(wallet.getValue()));
            lstCurrency.setSelection(currencyCodeAdapter.getPosition(wallet.getCurrency()));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDelete = getActivity().findViewById(R.id.btnDelete);
        btnSave = getActivity().findViewById(R.id.btnSave);

        lstCurrency = getActivity().findViewById(R.id.lstCurrency);
        imgQrcode = getActivity().findViewById(R.id.imgQRCode);
        txtDescription = getActivity().findViewById(R.id.txtDescription);
        txtName = getActivity().findViewById(R.id.txtName);
        txtValue = getActivity().findViewById(R.id.txtValue);

        btnSave.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        currencySymbolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstCurrency.setAdapter(currencySymbolAdapter);
        lstCurrency.setSelection(currencyCodeAdapter.getPosition(authenticationUser.getCurrency()));
        imgQrcode.setImageBitmap(QRCodeHelper.StringToQRCodeBitmap(wallet.getId()));
    }

    @Override
    public void onWalletCreate(int code, String message, String walletId) {
        if (code == HttpURLConnection.HTTP_OK) {
            wallet.setId(walletId);
            Toast.makeText(this.getActivity(), "Create", Toast.LENGTH_LONG).show();
        } else if (code == HttpURLConnection.HTTP_BAD_REQUEST) {
            Toast.makeText(this.getActivity(), "Create fail", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), "Create else", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onWalletDelete(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Delete", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), "Delete fail", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onWalletRead(int code, String message, Wallet wallet) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Read", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), "Read fail", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onWalletUpdate(int code, String message) {
        if (code == HttpURLConnection.HTTP_OK) {
            Toast.makeText(this.getActivity(), "Update", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), "Update fail", Toast.LENGTH_LONG).show();
        }
    }
}
