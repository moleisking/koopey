package com.koopey.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Asset;
import com.koopey.model.Assets;

public class AssetMyListFragment extends AssetListFragment implements View.OnClickListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getAssets();
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
        super.onViewCreated(view, savedInstanceState);
        btnCreate = (FloatingActionButton) getActivity().findViewById(R.id.btnCreate);
        btnCreate.setVisibility(View.VISIBLE);
        btnCreate.setOnClickListener(this);
        populateAssets();
    }

    @Override
    protected void getAssets() {
        if (getActivity().getIntent().hasExtra("myAssets")) {
            this.assets = (Assets) getActivity().getIntent().getSerializableExtra("myAssets");
            this.populateAssets();
        } else if (SerializeHelper.hasFile(this.getActivity(), Assets.MY_ASSETS_FILE_NAME)) {
            this.assets = (Assets) SerializeHelper.loadObject(this.getActivity(), Assets.MY_ASSETS_FILE_NAME);
            this.populateAssets();
        } else {
            this.assets = new Assets();
        }
    }

    @Override
    public void onClick(View v) {
        Log.d(AssetMyListFragment.class.getSimpleName() , "onClick()");
        Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_asset_edit);
    }
}
