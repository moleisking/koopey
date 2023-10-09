package com.koopey.view.fragment;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Assets;

public class AssetMyListFragment extends AssetListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.syncAssets();
    }

    @Override
    protected void syncAssets() {
        if (getActivity().getIntent().hasExtra("assets")) {
            this.assets = (Assets)getActivity().getIntent().getSerializableExtra("assets");
            this.populateAssets();
        } else if (SerializeHelper.hasFile(this.getActivity(), Assets.MY_ASSETS_FILE_NAME)) {
            this.assets = (Assets) SerializeHelper.loadObject(this.getActivity(), Assets.MY_ASSETS_FILE_NAME);
            this.populateAssets();
        } else {
            this.assets =  new Assets();
        }
    }
}
