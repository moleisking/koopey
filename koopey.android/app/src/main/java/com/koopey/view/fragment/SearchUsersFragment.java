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
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.maps.model.LatLng;

import com.koopey.R;
import com.koopey.adapter.TagAdapter;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Locations;
import com.koopey.model.Location;
import com.koopey.model.Search;
import com.koopey.model.Tags;
import com.koopey.model.Users;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.view.MainActivity;

//import org.florescu.android;

//import android.support.v4.app.Fragment;

public class SearchUsersFragment extends Fragment implements     SeekBar.OnSeekBarChangeListener,  View.OnClickListener  {


    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;

    public AuthenticationUser authenticationUser;
    private MultiAutoCompleteTextView lstTags;

    private Locations products;
    private Users users;

    private EditText txtMin, txtMax;
    private TagAdapter tagAdapter;
    private FloatingActionButton btnSearch;

    private Location location;
    private RadioGroup radGrpPeriod;
    private RadioButton optHour, optDay, optWeek, optMonth;
    private Spinner lstCurrency;
    private Search search = new Search();
    private SeekBar seeRadius;
    private TextView txtRadius;
    private int radius = 0;

    @Override
    public void onClick(View v) {
        this.buildSearch();
        //this.postSearch();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.lstTags = (MultiAutoCompleteTextView) getActivity().findViewById(R.id.lstTags);
        this.lstCurrency = (Spinner) getActivity().findViewById(R.id.lstCurrency);
        this.txtMin = (EditText) getActivity().findViewById(R.id.txtMin);
        this.txtMax = (EditText) getActivity().findViewById(R.id.txtMax);
        this.btnSearch = (FloatingActionButton) getActivity().findViewById(R.id.btnSearch);
        this.radGrpPeriod = (RadioGroup) getActivity().findViewById(R.id.radGrpPeriod);
        this.optHour = (RadioButton) getActivity().findViewById(R.id.optHour);
        this.optDay = (RadioButton) getActivity().findViewById(R.id.optDay);
        this.optWeek = (RadioButton) getActivity().findViewById(R.id.optWeek);
        this.optMonth = (RadioButton) getActivity().findViewById(R.id.optMonth);
        this.seeRadius= (SeekBar) getActivity().findViewById(R.id.seeRadius);
        this.txtRadius= (TextView) getActivity().findViewById(R.id.txtRadius);
        this.btnSearch.setOnClickListener(this);
        //txtMin.setMaxValue(5000);
        //txtMin.setMinValue(0);
        //txtMin.setValue(0);
        //txtMax.setMaxValue(5000);
        //txtMax.setMinValue(0);
        //txtMax.setValue(500);
        //Load data into fields
        this.populateCurrencies();
        //this.populateTags();
        if (SerializeHelper.hasFile(this.getActivity(), Tags.TAGS_FILE_NAME)) {
            Tags tags = ((MainActivity) getActivity()).getTags();
            AuthenticationUser authenticationUser = ((MainActivity) getActivity()).getAuthenticationUser();

            this.tagAdapter = new TagAdapter(this.getActivity(), tags, authenticationUser.getLanguage());
            //    this.lstTags.allowDuplicates(false);
            //    this.lstTags.setAdapter(this.tagAdapter);
            //    this.lstTags.setTokenLimit(15);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_search_tag, container, false);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
        radius = progresValue;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) { }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        this.txtRadius.setText(" " + radius + "/" + seekBar.getMax() + getResources().getString(R.string.default_period));
    }

    private void populateCurrencies() {
        this.currencyCodeAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_codes, android.R.layout.simple_spinner_item);
        this.currencySymbolAdapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.currency_symbols, android.R.layout.simple_spinner_item);
        this.currencySymbolAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.lstCurrency.setAdapter(currencySymbolAdapter);
    }

    private void buildSearch() {
        this.search.setCurrency( CurrencyHelper.currencySymbolToCode(lstCurrency.getSelectedItem().toString()));
        this.search.setRadius( getResources().getInteger(R.integer.default_radius));
        this.search.setMin( Integer.valueOf(this.txtMin.getText().toString()));
        this.search.setMax( Integer.valueOf(this.txtMax.getText().toString()));
        this.search.setMeasure(this.authenticationUser.getMeasure());
        this.search.setType( "users");
   //     this.search.tags.setTagList(lstTags.getObjects());
        if (this.radGrpPeriod.getCheckedRadioButtonId() == this.optHour.getId()) {
            this.search.setPeriod( "hour");
        } else if (this.radGrpPeriod.getCheckedRadioButtonId() == this.optDay.getId()) {
            this.search.setPeriod("day");
        } else if (this.radGrpPeriod.getCheckedRadioButtonId() == this.optWeek.getId()) {
            this.search.setPeriod( "week");
        } else if (this.radGrpPeriod.getCheckedRadioButtonId() == this.optMonth.getId()) {
            this.search.setPeriod( "month");
        }
    }

   /* @Override
    public void onLocation(Location location) {
        this.search.setLatitude (location.getLatitude());//40.4101013; //
        this.search.setLongitude(location.getLongitude());//-3.705122299999971;//
    }*/
}
