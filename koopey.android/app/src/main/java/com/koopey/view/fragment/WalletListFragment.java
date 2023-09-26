package com.koopey.view.fragment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.ListFragment;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;

import com.koopey.adapter.WalletAdapter;
import com.koopey.model.Alert;
import com.koopey.model.Bitcoin;
import com.koopey.model.Ethereum;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.Wallet;
import com.koopey.model.Wallets;
import com.koopey.service.AuthenticationService;
import com.koopey.view.PrivateActivity;

public class WalletListFragment extends ListFragment implements View.OnTouchListener {

    private final int WALLET_LIST_FRAGMENT = 369;
    private Bitcoin bitcoin = new Bitcoin();
    private Ethereum ethereum = new Ethereum();
    private FragmentManager fragmentManager;

    AuthenticationService authenticationService;
    private AuthenticationUser authenticationUser ;
    private WalletDialogFragment walletDialogFragment = new WalletDialogFragment();
    private WalletAdapter walletAdapter;
    private Wallets wallets = new Wallets();
    private boolean showScrollbars = true;
    private boolean showValues = true;
    private boolean showImages = true;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PrivateActivity) getActivity()).hideKeyboard();
authenticationService = new AuthenticationService(getContext());
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();

        if (this.getActivity().getIntent().hasExtra("wallets")) {
            this.wallets = (Wallets) this.getActivity().getIntent().getSerializableExtra("wallets");
            this.wallets.getTokoWallet().setOwnerId( this.authenticationUser.getId());
            this.populateWallets();
        } else if (SerializeHelper.hasFile(this.getActivity(), Wallets.WALLETS_FILE_NAME)) {
            this.wallets = (Wallets) SerializeHelper.loadObject(this.getActivity(), Wallets.WALLETS_FILE_NAME);
            this.populateWallets();
        } else {
            this.wallets = new Wallets();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_wallets, container, false);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onInflate(Activity activity, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(activity, attrs, savedInstanceState);
        TypedArray a = activity.obtainStyledAttributes(attrs, R.styleable.ParentChildListFragment);
        this.showScrollbars = a.getBoolean(R.styleable.ParentChildListFragment_showScrollbars, true);
        this.showImages = a.getBoolean(R.styleable.ParentChildListFragment_showImages, true);
        this.showValues = a.getBoolean(R.styleable.ParentChildListFragment_showValues, true);
        a.recycle();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Wallet wallet = this.wallets.get(position);
      //  ((PrivateActivity) getActivity()).showWalletReadFragment(wallet);
    }



    @Override
    public void onStart() {
        super.onStart();
        Log.d(WalletListFragment.class.getName(), "");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // Disallow the touch request for parent scroll on touch of child view
        v.getParent().requestDisallowInterceptTouchEvent(true);
        return false;
    }

    private void populateWallets() {
        if (getResources().getBoolean(R.bool.transactions)) {
            if (this.wallets != null && !this.wallets.isEmpty()) {
                this.walletAdapter = new WalletAdapter(this.getActivity(), this.wallets, this.showImages, this.showValues);
                this.setListAdapter(walletAdapter);
                if (this.showValues) {
                  //  this.postBitcoinBalance();
                  //  this.postEthereumBalance();
                }
                if (this.showScrollbars) {
                    ((PrivateActivity) getActivity()).setTitle(getResources().getString(R.string.label_wallets));
                    ((PrivateActivity) getActivity()).hideKeyboard();
                } else {
                    setListViewHeightBasedOnChildren(this.getListView());
                }

            }
        }
    }

    public void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
                view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, AbsListView.LayoutParams.WRAP_CONTENT));

            view.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }





    public void setVisibility(int visibility) {
        try {
            this.getListView().setVisibility(visibility);
        } catch (Exception ex) {
            Log.d(WalletListFragment.class.getName(), ex.getMessage());
        }
    }

    public void setWallets(Wallets wallets) {
        if (wallets != null) {
            this.wallets = wallets;
            this.populateWallets();
        }
    }


}