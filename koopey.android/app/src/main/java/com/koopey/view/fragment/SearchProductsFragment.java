package com.koopey.view.fragment;


import android.app.Activity;

import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.model.LatLng;
import com.koopey.R;
import com.koopey.adapter.TagAdapter;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.SerializeHelper;
import com.koopey.controller.GPSReceiver;
import com.koopey.controller.GetJSON;
import com.koopey.controller.PostJSON;
import com.koopey.model.Alert;
import com.koopey.model.Assets;
import com.koopey.model.Search;
import com.koopey.model.Tags;
import com.koopey.model.Users;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AssetService;
import com.koopey.service.AuthenticationService;
import com.koopey.service.TagService;
import com.koopey.view.PrivateActivity;
import com.koopey.view.component.TagTokenAutoCompleteView;

public class SearchProductsFragment extends Fragment implements   GPSReceiver.OnGPSReceiverListener, View.OnClickListener, AssetService.AssetSearchListener {

    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;
    private FloatingActionButton btnSearch;
    private GPSReceiver gps;
    private TagTokenAutoCompleteView lstTags;
    private Tags tags;
    private Assets products;
    private Users users;
    private LatLng currentLatLng;
    private AuthenticationUser authenticationUser ;

    private AuthenticationService authenticationservice ;

    private AssetService assetService;

    TagService tagService;
    private EditText txtMin, txtMax;
    private TagAdapter tagAdapter;
    private Spinner lstCurrency;
    private Search search = new Search();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
              this.lstCurrency = (Spinner) getActivity().findViewById(R.id.lstCurrency);
     //   this.lstTags = (TagTokenAutoCompleteView) getActivity().findViewById(R.id.lstTags);
        this.txtMin = (EditText) getActivity().findViewById(R.id.txtMin);
        this.txtMax = (EditText) getActivity().findViewById(R.id.txtMax);
        this.btnSearch = (FloatingActionButton) getActivity().findViewById(R.id.btnSearch);
        //txtSearch = (TextView)container.findViewById(R.id.txtName);
        //radRadius = (RadioGroup) container.findViewById(R.id.radRadius);
        //Set object configurations
        this.btnSearch.setOnClickListener(this);
        this.populateCurrencies();
      //  this.lstTags.setLanguage(this.myUser.language);
      //  this.lstTags.setAdapter(tagAdapter);
       // this.lstTags.allowDuplicates(false);
    }

    @Override
    public void onClick(View v) {
        this.search.currency = CurrencyHelper.currencySymbolToCode(lstCurrency.getSelectedItem().toString());
        this.search.radius = getResources().getInteger(R.integer.default_radius);
        this.search.min = Integer.valueOf(this.txtMin.getText().toString());
        this.search.max = Integer.valueOf(this.txtMax.getText().toString());
        this.search.latitude = this.currentLatLng.latitude;
        this.search.longitude = this.currentLatLng.longitude;
        this.search.type = "Products";
      //  this.search.tags.setTagList(lstTags.getObjects());
        assetService.searchAsset(search);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((PrivateActivity) getActivity()).hideKeyboard();

        authenticationservice = new AuthenticationService(getContext());
        assetService = new AssetService(getContext());
        tagService = new TagService(getContext());

        authenticationUser = authenticationservice.getLocalAuthenticationUserFromFile();



        //Define tags
        if (SerializeHelper.hasFile(this.getActivity(), tags.TAGS_FILE_NAME)) {
            this.tags = (Tags) SerializeHelper.loadObject(this.getActivity(), Tags.TAGS_FILE_NAME);
            this.tagAdapter = new TagAdapter(this.getActivity(), this.tags, authenticationUser.getLanguage());
        } else {
            tagService.getLocalTagsFromFile();
        }
        //Start GPS
        currentLatLng = new LatLng(0.0d, 0.0d);
        gps = new GPSReceiver(this.getActivity());
        gps.delegate = this;
        gps.Start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_search, container, false);
    }

    @Override
    public void onGPSConnectionResolutionRequest(ConnectionResult connectionResult) {
        try {
            connectionResult.startResolutionForResult(this.getActivity(), GPSReceiver.OnGPSReceiverListener.CONNECTION_FAILURE_RESOLUTION_REQUEST);
        } catch (Exception ex) {
            Log.d(SearchProductsFragment.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void onGPSWarning(String message) {
        Toast.makeText(this.getActivity(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onGPSPositionResult(LatLng position) {
        this.currentLatLng = position;
        gps.Stop();
        Log.d(SearchProductsFragment.class.getName(), position.toString());
    }

    private void populateCurrencies() {
        this.currencyCodeAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_codes, android.R.layout.simple_spinner_item);
        this.currencySymbolAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_symbols, android.R.layout.simple_spinner_item);
        this.currencySymbolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.lstCurrency.setAdapter(currencySymbolAdapter);
    }


    @Override
    public void onAssetsByBuyer(Assets assets) {

    }

    @Override
    public void onAssetsByBuyerOrSeller(Assets assets) {

    }

    @Override
    public void onAssetsBySeller(Assets assets) {

    }

    @Override
    public void onAssetSearch(Assets assets) {
        Toast.makeText(this.getActivity(), getResources().getString(R.string.info_complete), Toast.LENGTH_SHORT).show();
products = assets;
    }
}
