package com.koopey.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.adapter.AssetAdapter;
import com.koopey.adapter.TransactionAdapter;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.service.AssetService;

import java.net.HttpURLConnection;

public class AssetMyListFragment extends ListFragment implements View.OnClickListener, AssetService.AssetSearchBuyerOrSellerListener {

    private Assets assets = new Assets();
    private AssetAdapter assetAdapter;
    private AssetService assetService;
    private FloatingActionButton btnCreate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetService = new AssetService(getContext());
        assetService.setOnAssetSearchBuyerOrSellerListener(this);
        this.getAssets();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.asset_my_list, container, false);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (this.assets != null && this.assets.size() > 0) {
            Asset asset = this.assets.get(position);
            getActivity().getIntent().putExtra("myAsset", asset);
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_asset_edit);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //super.onViewCreated(view, savedInstanceState);
        btnCreate = getActivity().findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(this);
    }

    protected void getAssets() {
        if (getActivity().getIntent().hasExtra("myAssets")) {
            assets = (Assets) getActivity().getIntent().getSerializableExtra("myAssets");
            setListAdapter();
        } else if (SerializeHelper.hasFile(this.getActivity(), Assets.MY_ASSETS_FILE_NAME)) {
            assets = (Assets) SerializeHelper.loadObject(this.getActivity(), Assets.MY_ASSETS_FILE_NAME);
            setListAdapter();
        } else {
            assetService.searchByBuyerOrSeller();
        }
    }

    private void setListAdapter() {
        if (assets != null && !assets.isEmpty()) {
            assetAdapter = new AssetAdapter(this.getActivity(), assets);
            this.setListAdapter(assetAdapter);
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(AssetMyListFragment.class.getSimpleName(), "onClick()");
        Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_asset_edit);
    }

    @Override
    public void onAssetsByBuyer(int code, String message, Assets assets) {

    }

    @Override
    public void onAssetsByBuyerOrSeller(int code, String message, Assets assets) {
        if (code == HttpURLConnection.HTTP_OK) {
            this.assets = assets;
            setListAdapter();
            Toast.makeText(this.getActivity(), "Success", Toast.LENGTH_LONG).show();
        } else if (code == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_login);
        } else {
            Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
        }
    }
}
