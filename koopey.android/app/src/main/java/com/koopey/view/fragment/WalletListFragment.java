package com.koopey.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.helper.SerializeHelper;

import com.koopey.adapter.WalletAdapter;
import com.koopey.model.Bitcoin;
import com.koopey.model.Ethereum;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.Wallet;
import com.koopey.model.Wallets;
import com.koopey.service.WalletService;
import com.koopey.view.MainActivity;

import java.net.HttpURLConnection;

public class WalletListFragment extends ListFragment implements View.OnClickListener, WalletService.WalletSearchListener {

    private AuthenticationUser authenticationUser;
    private Wallets wallets = new Wallets();
    private WalletAdapter walletAdapter;
    private WalletService walletService;
    private FloatingActionButton btnCreate;

    @Override
    public void onClick(View v) {
        if (v.getId() == this.btnCreate.getId()) {
            getActivity().getIntent().putExtra("wallet",  Wallet.builder()
                    .currency(authenticationUser.getCurrency())
                    .ownerId(authenticationUser.getId())
                    .type("create").build());
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_wallet_edit);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationUser = ((MainActivity) getActivity()).getAuthenticationUser();
        ((MainActivity) getActivity()).hideKeyboard();
        walletService = new WalletService(getContext());
        walletService.setOnWalletSearchListener(this);

        if (this.getActivity().getIntent().hasExtra("wallets")) {
            this.wallets = (Wallets) this.getActivity().getIntent().getSerializableExtra("wallets");
            this.wallets.getTokoWallet().setOwnerId(this.authenticationUser.getId());
        } else if (SerializeHelper.hasFile(this.getActivity(), Wallets.WALLETS_FILE_NAME)) {
            this.wallets = (Wallets) SerializeHelper.loadObject(this.getActivity(), Wallets.WALLETS_FILE_NAME);
        } else {
            this.wallets = new Wallets();
            walletService.search();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.wallet_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Wallet wallet = this.wallets.get(position);
        if (wallet != null) {
            this.getActivity().getIntent().putExtra("wallet", wallet);
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_wallet_edit);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCreate = getActivity().findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);

        this.walletAdapter = new WalletAdapter(this.getActivity(), this.wallets, true, true);
        this.setListAdapter(walletAdapter);
    }

    @Override
    public void onWalletSearch(int code, String message, Wallets wallets) {
        if (code == HttpURLConnection.HTTP_OK) {
            this.wallets = wallets;
        } else if (code == HttpURLConnection.HTTP_NO_CONTENT) {
            Toast.makeText(this.getActivity(), getResources().getString(R.string.error_empty), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }

}