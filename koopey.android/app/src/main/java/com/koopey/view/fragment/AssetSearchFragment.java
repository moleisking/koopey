package com.koopey.view.fragment;


import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.koopey.R;
import com.koopey.adapter.TagAdapter;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.SerializeHelper;

import com.koopey.model.Location;
import com.koopey.model.Locations;
import com.koopey.model.Search;
import com.koopey.model.Tags;
import com.koopey.model.Users;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.LocationService;
import com.koopey.service.AuthenticationService;
import com.koopey.service.PositionService;
import com.koopey.service.TagService;
import com.koopey.view.MainActivity;
import com.koopey.view.component.TagTokenAutoCompleteView;

public class AssetSearchFragment extends Fragment implements    View.OnClickListener, LocationService.LocationSearchListener {

    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;
    private FloatingActionButton btnSearch;
    private PositionService gps;
    private TagTokenAutoCompleteView lstTags;
    private Tags tags;
    private Locations products;
    private Users users;
    private LatLng currentLatLng;
    private AuthenticationUser authenticationUser ;

    private AuthenticationService authenticationservice ;

    private LocationService locationService;

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
        this.search.setCurrency (CurrencyHelper.currencySymbolToCode(lstCurrency.getSelectedItem().toString()));
        this.search.setRadius (getResources().getInteger(R.integer.default_radius));
        this.search.setMin(Integer.valueOf(this.txtMin.getText().toString()));
        this.search.setMax ( Integer.valueOf(this.txtMax.getText().toString()));
        this.search.setLatitude ( this.currentLatLng.latitude);
        this.search.setLongitude ( this.currentLatLng.longitude);
        this.search.setType ("Products");
      //  this.search.tags.setTagList(lstTags.getObjects());
        locationService.searchLocation(search);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ((MainActivity) getActivity()).hideKeyboard();

        authenticationservice = new AuthenticationService(getContext());
        locationService = new LocationService(getContext());
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_search, container, false);
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
    public void onLocationSearchByBuyerAndDestination(Locations locations) {

    }

    @Override
    public void onLocationSearchByBuyerAndSource(Locations locations) {

    }

    @Override
    public void onLocationSearchByDestinationAndSeller(Locations locations) {

    }

    @Override
    public void onLocationSearchBySellerAndSource(Locations locations) {

    }

    @Override
    public void onLocationSearch(Locations locations) {
        Toast.makeText(this.getActivity(), getResources().getString(R.string.info_complete), Toast.LENGTH_SHORT).show();
products = locations;
    }

    @Override
    public void onLocationSearchByGeocode(Location location) {

    }

    @Override
    public void onLocationSearchByPlace(Location location) {

    }

    @Override
    public void onLocationSearchByRangeInKilometers(Locations locations) {

    }

    @Override
    public void onLocationSearchByRangeInMiles(Locations locations) {

    }
}
