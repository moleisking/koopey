package com.koopey.view.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;

import com.koopey.model.Location;

import com.koopey.model.Transaction;
import com.koopey.model.Transactions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.TransactionService;
import com.koopey.view.MainActivity;

/**
 * Created by Scott on 06/04/2017.
 */
public class TransactionEditFragment extends Fragment implements  View.OnClickListener {
    private EditText txtName, txtValue, txtTotal, txtQuantity;
    private FloatingActionButton btnCreate;
    private Spinner lstCurrency;
    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;
    private Transaction transaction ;
    TransactionService transactionService;
    private Transactions transactions;
    private Location location;

    private AuthenticationUser authenticationUser;



    @Override
    public void onClick(View v) {
        try {
            if (v.getId() == btnCreate.getId()) {
                //Build product object
                this.transaction.getUsers().addBuyer(authenticationUser.getUserBasicWithAvatar());
                //this.transaction.users.addSeller(location.user.getUserBasicWithAvatar());
                this.transaction.setName( txtName.getText().toString());
                this.transaction.setItemValue( Double.valueOf(txtValue.getText().toString()));
                this.transaction.setQuantity( Integer.valueOf(txtQuantity.getText().toString()));
                this.transaction.setTotalValue( Double.valueOf(txtTotal.getText().toString()));
                this.transaction.setCurrency (lstCurrency.getSelectedItem().toString());
                //Post new data
                if (!this.transaction.isEmpty()) {
                    transactionService.createTransaction(transaction);

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
transactionService  = new TransactionService(getContext());
        this.txtName = (EditText) getActivity().findViewById(R.id.txtName);
        this.txtValue = (EditText) getActivity().findViewById(R.id.txtValue);
        this.txtTotal = (EditText) getActivity().findViewById(R.id.txtTotal);
        this.txtQuantity = (EditText) getActivity().findViewById(R.id.txtQuantity);
        this.lstCurrency = (Spinner) getActivity().findViewById(R.id.lstCurrency);
        this.btnCreate = (FloatingActionButton) getActivity().findViewById(R.id.btnCreate);
        this.btnCreate.setOnClickListener(this);
        authenticationUser = ((MainActivity) getActivity()).getAuthenticationUser();
        this.populateCurrencies();

        if (getActivity().getIntent().hasExtra("location")) {
            this.location = (Location) getActivity().getIntent().getSerializableExtra("location");
        }

        if (SerializeHelper.hasFile(this.getActivity(), Transactions.TRANSACTIONS_FILE_NAME)) {
            this.transactions = (Transactions) SerializeHelper.loadObject(this.getActivity(), Transactions.TRANSACTIONS_FILE_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction_create, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        this.btnCreate.setVisibility(View.VISIBLE);
    }



    private void populateCurrencies() {
        this.currencyCodeAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_codes, android.R.layout.simple_spinner_item);
        this.currencySymbolAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_symbols, android.R.layout.simple_spinner_item);
        this.currencySymbolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.lstCurrency.setAdapter(currencySymbolAdapter);
        lstCurrency.setSelection(currencyCodeAdapter.getPosition(transaction.getCurrency()));
    }


}