package com.koopey.view.fragment;


import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koopey.R;

import com.koopey.model.Search;
import com.koopey.model.Transactions;
import com.koopey.service.TransactionService;

import java.util.Date;

public class TransactionSearchFragment extends Fragment implements  View.OnClickListener , TransactionService.TransactionSearchListener {

    private EditText txtId ;
    private DatePicker txtEnd, txtStart;
    private FloatingActionButton btnSearch;
    private Search search = new Search();
    private Transactions transactions ;
    private TransactionService transactionService;

    @Override
    public void onClick(View v) {
        if (v.getId() == this.btnSearch.getId()) {
            search.setId (this.txtId.getText().toString());
            search.setStart(new Date(txtStart.getYear(), txtStart.getMonth(), txtStart.getDayOfMonth()).getTime());
            search.setEnd(new Date(txtEnd.getYear(), txtEnd.getMonth(), txtEnd.getDayOfMonth()).getTime());
            search.setType("Users");
            getActivity().getIntent().putExtra("search", search );
            transactionService.search(search);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        transactionService = new TransactionService(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_search, container, false);
    }

    @Override
    public void onTransactionSearch(int code, String message, Transactions transactions) {
        this.getActivity().getIntent().putExtra("transactions", this.transactions );
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtId = getActivity().findViewById(R.id.txtId);
        txtStart = getActivity().findViewById(R.id.txtStart);
        txtEnd = getActivity().findViewById(R.id.txtEnd);
        btnSearch = getActivity().findViewById(R.id.btnSearch);

        btnSearch.setOnClickListener(this);
    }
}
