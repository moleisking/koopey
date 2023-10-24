package com.koopey.view.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.navigation.Navigation;

import java.util.Date;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.adapter.AssetAdapter;
import com.koopey.adapter.TransactionAdapter;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Transaction;
import com.koopey.model.Transactions;
import com.koopey.model.User;
import com.koopey.model.Users;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AuthenticationService;
import com.koopey.service.TransactionService;
import com.koopey.view.MainActivity;

public class TransactionListFragment extends ListFragment
        implements View.OnClickListener, TransactionService.TransactionSearchByBuyerOrSellerListener {

    private AuthenticationUser authenticationUser;
    private Transactions transactions;
    private TransactionAdapter transactionAdapter;
    private TransactionService transactionService;
    private FloatingActionButton btnCreate, btnSearch;

    @Override
    public void onClick(View v) {
        if (v.getId() == btnCreate.getId()) {
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_transaction_edit);
        } else if (v.getId() == btnSearch.getId()) {
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_transaction_search);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationUser =  ((MainActivity)getActivity()).getAuthenticationUser() ;
        transactionService = new TransactionService(getContext());
        getTransactions();
        ((MainActivity) getActivity()).hideKeyboard();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_list, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity().getIntent().hasExtra("transactions")) {
            getActivity().getIntent().removeExtra("transactions");
        }
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
            Transaction transaction = transactions.get(position);
            if (transaction.getBuyerId().equals(authenticationUser.getId())||
                    transaction.getSellerId().equals(authenticationUser.getId())) {
                getActivity().getIntent().putExtra("transaction", transaction);
                Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_transaction_view);
            }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       // super.onViewCreated(view, savedInstanceState);
        btnCreate= getActivity().findViewById(R.id.btnCreate);
        btnSearch = getActivity().findViewById(R.id.btnSearch);

        btnCreate.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
    }

    public void getTransactions() {
        if (getActivity().getIntent().hasExtra("transactions")) {
            transactions = (Transactions) getActivity().getIntent().getSerializableExtra("transactions");
            setListAdapter();
        } else if (SerializeHelper.hasFile(this.getActivity(), Transactions.TRANSACTIONS_FILE_NAME)) {
            transactions = (Transactions) SerializeHelper.loadObject(this.getActivity(), Transactions.TRANSACTIONS_FILE_NAME);
            setListAdapter();
        } else {
            transactionService.setOnTransactionSearchByBuyerOrSellerListener(this);
            transactionService.searchTransactionByBuyerOrSeller();
        }


    }

    @Override
    public void onTransactionSearchByBuyer(int code, String message, Transactions transactions) {

    }

    @Override
    public void onTransactionSearchByBuyerOrSeller(int code, String message, Transactions transactions) {
        this.transactions = transactions;
        setListAdapter();
    }

    @Override
    public void onTransactionSearchBySeller(int code, String message, Transactions transactions) {

    }

    private void setListAdapter(){
        if (transactions != null && !transactions.isEmpty() ){
            transactionAdapter = new TransactionAdapter(this.getActivity(), transactions);
            this.setListAdapter(transactionAdapter);
        }
    }


   /* private void startQRCodeScan() {
        try {
            Intent intent = new Intent("com.google.zxing.client.android.SCAN");
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, TRANSACTION_LIST_FRAGMENT);
        } catch (Exception e) {
            Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
            startActivity(marketIntent);
            Log.d(LOG_HEADER + ":ER", e.getMessage());
        }
    }*/
}
