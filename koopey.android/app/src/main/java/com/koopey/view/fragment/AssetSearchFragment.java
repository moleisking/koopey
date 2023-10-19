package com.koopey.view.fragment;

import android.os.Bundle;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.adapter.TagAdapter;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.SerializeHelper;

import com.koopey.model.Assets;
import com.koopey.model.Location;
import com.koopey.model.Locations;
import com.koopey.model.Search;
import com.koopey.model.Tags;
import com.koopey.model.Users;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AssetService;
import com.koopey.service.LocationService;
import com.koopey.service.AuthenticationService;
import com.koopey.service.PositionService;
import com.koopey.service.TagService;
import com.koopey.view.MainActivity;
import com.koopey.view.component.TagTokenAutoCompleteView;

public class AssetSearchFragment extends Fragment implements View.OnClickListener,
        PositionService.PositionListener {

    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;
    private AuthenticationUser authenticationUser;
    private AuthenticationService authenticationservice;
    private FloatingActionButton btnSearch;
    private PositionService gps;
    private TagTokenAutoCompleteView lstTags;
    private Tags tags;
    private Assets assets;

    private AssetService assetService;
    private Users users;
    private LatLng currentLatLng;
    private LocationService locationService;
    private TagService tagService;
    private EditText txtMin, txtMax;
    private TagAdapter tagAdapter;
    private Spinner lstCurrency;
    private Search search = new Search();

    @Override
    public void onClick(View v) {
        search.setCurrency(CurrencyHelper.currencySymbolToCode(lstCurrency.getSelectedItem().toString()));
        search.setRadius(getResources().getInteger(R.integer.default_radius));
        search.setMin(Integer.valueOf(this.txtMin.getText().toString()));
        search.setMax(Integer.valueOf(this.txtMax.getText().toString()));
        search.setLatitude(this.currentLatLng.latitude);
        search.setLongitude(this.currentLatLng.longitude);
        search.setType("Products");
        //  this.search.tags.setTagList(lstTags.getObjects());
        locationService.searchLocation(search);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).hideKeyboard();

        assetService = new AssetService(getContext());
        authenticationservice = new AuthenticationService(getContext());
        locationService = new LocationService(getContext());
        tagService = new TagService(getContext());

        authenticationUser = authenticationservice.getLocalAuthenticationUserFromFile();


        //Define tags
        if (SerializeHelper.hasFile(this.getActivity(), tags.TAGS_FILE_NAME)) {
            tags = (Tags) SerializeHelper.loadObject(this.getActivity(), Tags.TAGS_FILE_NAME);
            tagAdapter = new TagAdapter(this.getActivity(), this.tags, authenticationUser.getLanguage());
        } else {
            tagService.getLocalTagsFromFile();
        }
        //Start GPS
        currentLatLng = new LatLng(0.0d, 0.0d);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.asset_search, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getActivity().getIntent().hasExtra("search")){
            getActivity().getIntent().removeExtra("search");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSearch = getActivity().findViewById(R.id.btnSearch);
        lstCurrency = getActivity().findViewById(R.id.lstCurrency);
        lstTags = getActivity().findViewById(R.id.lstTags);
        txtMin = getActivity().findViewById(R.id.txtMin);
        txtMax = getActivity().findViewById(R.id.txtMax);

        //txtSearch = (TextView)container.findViewById(R.id.txtName);
        //radRadius = (RadioGroup) container.findViewById(R.id.radRadius);

        btnSearch.setOnClickListener(this);
        populateCurrencies();
        lstTags.setAdapter(tagAdapter);
        lstTags.setTokenLimit(15);
    }

    private void populateCurrencies() {
        currencyCodeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.currency_codes, android.R.layout.simple_spinner_item);
        currencySymbolAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.currency_symbols, android.R.layout.simple_spinner_item);
        currencySymbolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lstCurrency.setAdapter(currencySymbolAdapter);
        lstCurrency.setSelection(currencyCodeAdapter.getPosition(authenticationUser.getCurrency()));
    }

    @Override
    public void onPositionRequestSuccess(Double altitude, Double latitude, Double longitude) {

    }

    @Override
    public void onPositionRequestFail(String errorMessage) {

    }

    @Override
    public void onPositionRequestPermission() {

    }
}
