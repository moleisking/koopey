package com.koopey.view.fragment;


import android.app.Activity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.controller.GetJSON;


import com.koopey.model.Alert;
import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.service.AssetService;
import com.koopey.view.PrivateActivity;


/**
 * Created by Scott on 10/02/2017.
 */
public class MyAssetListFragment extends AssetListFragment implements View.OnClickListener, AssetService.AssetSearchListener {

    AssetService assetService;

    @Override
    public void onClick(View v) {
        if (v.getId() == btnCreate.getId()) {
           // ((PrivateActivity) getActivity()).showAssetCreateFragment();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetService = new AssetService(getContext());
        this.btnCreate = (FloatingActionButton) getActivity().findViewById(R.id.btnCreate);
        this.btnCreate.setVisibility(View.VISIBLE);
        this.btnCreate.setOnClickListener(this);
        this.syncAssets();
    }



    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

            if (this.assets != null && this.assets.size() >0) {
                Asset asset = (Asset) assets.get(position);
                //AssetReadFragment will hide or show the update button
                //((PrivateActivity) getActivity()).showAssetReadFragment(asset);
            }
    }

    @Override
    public void syncAssets() {
        if (SerializeHelper.hasFile(this.getActivity(), Assets.MY_ASSETS_FILE_NAME)) {
            this.assets = (Assets) SerializeHelper.loadObject(this.getActivity(), Assets.MY_ASSETS_FILE_NAME);
            this.populateAssets();
           // this.assets = assetService.searchAssetsByBuyerOrSeller();

        } else {
            assetService.searchAssetsByBuyerOrSeller();

        }
    }

    private void saveAssets() {
        this.assets.fileType = Assets.MY_ASSETS_FILE_NAME;
        SerializeHelper.saveObject(this.getActivity(), this.assets);
    }


    @Override
    public void onAssetsByBuyer(Assets assets) {

    }

    @Override
    public void onAssetsByBuyerOrSeller(Assets assets) {
        this.assets = assets;
    }

    @Override
    public void onAssetsBySeller(Assets assets) {

    }

    @Override
    public void onAssetSearch(Assets assets) {

    }
}
