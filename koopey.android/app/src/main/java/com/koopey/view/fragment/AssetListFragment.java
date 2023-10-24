package com.koopey.view.fragment;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;
import androidx.navigation.Navigation;

import com.koopey.R;
import com.koopey.helper.SerializeHelper;
import com.koopey.adapter.AssetAdapter;
import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.Search;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AssetService;
import com.koopey.view.MainActivity;

import java.net.HttpURLConnection;

public class AssetListFragment extends ListFragment implements AssetService.AssetSearchListener {

    private Assets assets = new Assets();
    private AssetAdapter assetAdapter;
    private AssetService assetService;
    private AuthenticationUser authenticationUser;

    private Search search;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationUser = ((MainActivity) getActivity()).getAuthenticationUser();
        assetService = new AssetService(getContext());
        assetService.setOnAssetSearchListener(this);

        search = Search.builder()
                .currency(authenticationUser.getCurrency())
                .latitude(authenticationUser.getLatitude())
                .longitude(authenticationUser.getLongitude()).build();
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
            assets = new Assets();
            assetService.searchAsset(search);
        }

        AssetAdapter assetAdapter = new AssetAdapter(this.getActivity(), this.assets);
        this.setListAdapter(assetAdapter);
    }

    @Override
    public void onAssetSearch(int code, String message,Assets assets) {
       if (code==HttpURLConnection.HTTP_OK) {
           this.assets = assets;
          // assetAdapter.
           Toast.makeText(this.getActivity(), getResources().getString(R.string.label_search), Toast.LENGTH_LONG).show();
       } else if (code==HttpURLConnection.HTTP_NO_CONTENT) {
           Toast.makeText(this.getActivity(), getResources().getString(R.string.error_empty), Toast.LENGTH_LONG).show();
       } else {
           Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
       }
    }
}
