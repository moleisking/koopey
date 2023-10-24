package com.koopey.view.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.slider.Slider;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import com.koopey.R;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.ImageHelper;
import com.koopey.helper.QRCodeHelper;
import com.koopey.helper.SerializeHelper;

import com.koopey.model.Location;

import com.koopey.model.Transaction;
import com.koopey.model.Transactions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.TransactionService;
import com.koopey.view.MainActivity;

public class TransactionEditFragment extends Fragment implements View.OnClickListener {
    private ArrayAdapter<CharSequence> transactionValueAdapter;
    private ArrayAdapter<CharSequence> transactionNameAdapter;
    private AuthenticationUser authenticationUser;
    private EditText  txtDescription, txtName, txtValue, txtTotal, txtQuantity;
    private FloatingActionButton btnSave;
    private ImageView imgSecret;
    private Spinner lstType;
    private TextView txtCurrency1,txtCurrency2;
    private Transaction transaction;
    private TransactionService transactionService;
    private Transactions transactions;

    private Slider barGrade;
    private Location location;

    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == btnSave.getId()) {
                transaction.getUsers().addBuyer(authenticationUser.getUserBasicWithAvatar());
                //this.transaction.users.addSeller(location.user.getUserBasicWithAvatar());
                transaction.setName(txtName.getText().toString());
                transaction.setValue(Double.valueOf(txtValue.getText().toString()));
                transaction.setQuantity(Integer.valueOf(txtQuantity.getText().toString()));
                transaction.setTotal(Double.valueOf(txtTotal.getText().toString()));
                transaction.setType(lstType.getSelectedItem().toString());
                transaction.setGrade(Math.round( barGrade.getValue()));
                //Post new data
                if (!this.transaction.isEmpty()) {
                    transactionService.create(transaction);

                    //Add location to local MyLocations file
                    this.transactions.add(transaction);
                    SerializeHelper.saveObject(this.getActivity(), transactions);
                    ((MainActivity) getActivity()).showMyLocationListFragment();
                    Toast.makeText(this.getActivity(), getResources().getString(R.string.label_create), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this.getActivity(), getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
                }
            }
        } catch (Exception ex) {
            Log.d(TransactionEditFragment.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authenticationUser = ((MainActivity) getActivity()).getAuthenticationUser();
        transactionService = new TransactionService(getContext());
        transactionNameAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.transaction_names, android.R.layout.simple_spinner_item);
        transactionNameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionValueAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.transaction_values, android.R.layout.simple_spinner_item);

        if (getActivity().getIntent().hasExtra("transaction")) {
            transaction = (Transaction) getActivity().getIntent().getSerializableExtra("transaction");
        } else if (SerializeHelper.hasFile(this.getActivity(), Transactions.TRANSACTIONS_FILE_NAME)) {
            this.transaction = (Transaction) SerializeHelper.loadObject(this.getActivity(), Transaction.TRANSACTION_FILE_NAME);
        } else {
            transaction = Transaction.builder().sellerId(authenticationUser.getId()).quantity(0).build();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_edit, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().getIntent().hasExtra("transaction")) {
            getActivity().getIntent().removeExtra("transaction");
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        btnSave.setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        barGrade = getActivity().findViewById(R.id.barGrade);
        btnSave = getActivity().findViewById(R.id.btnSave);
        imgSecret = getActivity().findViewById(R.id.imgSecret);
        txtCurrency1 = getActivity().findViewById(R.id.txtCurrency1);
        txtCurrency2 = getActivity().findViewById(R.id.txtCurrency2);
        txtDescription = getActivity().findViewById(R.id.txtDescription);
        txtName = getActivity().findViewById(R.id.txtName);
        txtValue = getActivity().findViewById(R.id.txtValue);
        txtTotal = getActivity().findViewById(R.id.txtTotal);
        txtQuantity = getActivity().findViewById(R.id.txtQuantity);
        lstType = getActivity().findViewById(R.id.lstType);

        btnSave.setOnClickListener(this);
        imgSecret.setImageBitmap(QRCodeHelper.StringToQRCodeBitmap(transaction.getId()));
        lstType.setAdapter(transactionNameAdapter);
        lstType.setSelection(transactionValueAdapter.getPosition(transaction.getCurrency()));
        txtCurrency1.setText(CurrencyHelper.currencyCodeToSymbol( authenticationUser.getCurrency()));
        txtCurrency2.setText(CurrencyHelper.currencyCodeToSymbol(authenticationUser.getCurrency()));
    }

}
