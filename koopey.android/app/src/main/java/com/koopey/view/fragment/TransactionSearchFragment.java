package com.koopey.view.fragment;


import android.app.Activity;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.koopey.R;

import com.koopey.model.Search;
import com.koopey.model.Transactions;
import com.koopey.service.TransactionService;
import com.koopey.view.PrivateActivity;

import java.util.Date;

/**
 * Created by Scott on 12/12/2017.
 */

public class TransactionSearchFragment extends Fragment implements  View.OnClickListener , TransactionService.TransactionSearchListener {

    private EditText txtId ;
    private DatePicker txtEnd, txtStart;
    private FloatingActionButton btnSearch;
    private Search search = new Search();
    private Transactions transactions ;

    TransactionService transactionService;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transactionService = new TransactionService(getContext());
        this.txtId = (EditText) getActivity().findViewById(R.id.txtId);
        this.txtStart = (DatePicker) getActivity().findViewById(R.id.txtStart);
        this.txtEnd = (DatePicker) getActivity().findViewById(R.id.txtEnd);
        this.btnSearch = (FloatingActionButton) getActivity().findViewById(R.id.btnSearch);
        this.btnSearch.setOnClickListener(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction_search, container, false);
    }

    @Override
    public void onClick(View v) {
        // this.getActivity().getIntent().putExtra("transaction", this.transactions );
        if (v.getId() == this.btnSearch.getId()) {
            this.search.setId (this.txtId.getText().toString());
            this.search.setStart(new Date(txtStart.getYear(), txtStart.getMonth(), txtStart.getDayOfMonth()).getTime());
            this.search.setEnd(new Date(txtEnd.getYear(), txtEnd.getMonth(), txtEnd.getDayOfMonth()).getTime());
            this.search.setType("Users");
            transactionService.searchTransaction(search);

        }
    }


    @Override
    public void onTransactionSearchByLocation(int code, String message, Transactions transactions) {

    }

    @Override
    public void onTransactionSearchByBuyer(int code, String message, Transactions transactions) {

    }

    @Override
    public void onTransactionSearchByBuyerOrSeller(int code, String message, Transactions transactions) {

    }

    @Override
    public void onTransactionSearchByDestination(int code, String message, Transactions transactions) {

    }

    @Override
    public void onTransactionSearchBySeller(int code, String message, Transactions transactions) {

    }

    @Override
    public void onTransactionSearchBySource(int code, String message, Transactions transactions) {

    }

    @Override
    public void onTransactionSearch(int code, String message, Transactions transactions) {
        this.getActivity().getIntent().putExtra("transactions", this.transactions );
    }
}
