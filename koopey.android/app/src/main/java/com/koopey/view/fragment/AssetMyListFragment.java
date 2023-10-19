package com.koopey.view.fragment;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.adapter.AssetAdapter;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Asset;
import com.koopey.model.Assets;

public class AssetMyListFragment extends ListFragment implements View.OnClickListener {

    private Assets assets = new Assets();
    private FloatingActionButton btnCreate;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            this.assets = (Assets) getActivity().getIntent().getSerializableExtra("myAssets");
        } else if (SerializeHelper.hasFile(this.getActivity(), Assets.MY_ASSETS_FILE_NAME)) {
            this.assets = (Assets) SerializeHelper.loadObject(this.getActivity(), Assets.MY_ASSETS_FILE_NAME);
        } else {
            this.assets = new Assets();
        }

        AssetAdapter assetAdapter = new AssetAdapter(this.getActivity(), this.assets);
        this.setListAdapter(assetAdapter);
    }

    @Override
    public void onClick(View v) {
        Log.d(AssetMyListFragment.class.getSimpleName() , "onClick()");
        Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_asset_edit);
    }
}
