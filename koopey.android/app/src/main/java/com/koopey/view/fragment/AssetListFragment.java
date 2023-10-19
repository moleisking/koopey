package com.koopey.view.fragment;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.navigation.Navigation;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.adapter.AssetAdapter;
import com.koopey.model.Asset;
import com.koopey.model.Assets;

public class AssetListFragment extends ListFragment {

    private Assets assets = new Assets();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getAssets();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.asset_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
      //  super.onViewCreated(view, savedInstanceState);

      //  populateAssets();
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        if (this.assets != null && this.assets.size() > 0) {
            Asset asset = this.assets.get(position);
            getActivity().getIntent().putExtra("asset", asset);
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_asset_view);
        }
    }

    protected void getAssets() {
        if (getActivity().getIntent().hasExtra("assets")) {
            this.assets = (Assets) getActivity().getIntent().getSerializableExtra("assets");
        } else if (SerializeHelper.hasFile(this.getActivity(), assets.ASSET_SEARCH_RESULTS_FILE_NAME)) {
            this.assets = (Assets) SerializeHelper.loadObject(this.getActivity(), Assets.ASSET_SEARCH_RESULTS_FILE_NAME);
        } else {
            this.assets = new Assets();
        }

        AssetAdapter assetAdapter = new AssetAdapter(this.getActivity(), this.assets);
        this.setListAdapter(assetAdapter);
    }
}
