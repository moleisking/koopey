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
import com.koopey.model.Assets;
import com.koopey.model.Search;
import com.koopey.model.Tags;
import com.koopey.model.Users;
import com.koopey.view.PrivateActivity;
import com.koopey.view.component.PrivateFragment;

//import org.florescu.android;

//import android.support.v4.app.Fragment;

public class SearchUsersFragment extends PrivateFragment implements     SeekBar.OnSeekBarChangeListener,  View.OnClickListener  {


    private ArrayAdapter<CharSequence> currencyCodeAdapter;
    private ArrayAdapter<CharSequence> currencySymbolAdapter;
    private MultiAutoCompleteTextView lstTags;

    private Assets products;
    private Users users;

    private EditText txtMin, txtMax;
    private TagAdapter tagAdapter;
    private FloatingActionButton btnSearch;
    private RadioGroup radGrpPeriod;
    private RadioButton optHour, optDay, optWeek, optMonth;
    private Spinner lstCurrency;
    private Search search = new Search();
    private SeekBar seeRadius;
    private TextView txtRadius;
    private int radius = 0;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
    }



    @Override
    public void onClick(View v) {
        this.buildSearch();
        //this.postSearch();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (SerializeHelper.hasFile(this.getActivity(), Tags.TAGS_FILE_NAME)) {

            this.tagAdapter = new TagAdapter(this.getActivity(), tags, authenticationUser.language);
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
        this.search.currency = CurrencyHelper.currencySymbolToCode(lstCurrency.getSelectedItem().toString());
        this.search.radius = getResources().getInteger(R.integer.default_radius);
        this.search.min = Integer.valueOf(this.txtMin.getText().toString());
        this.search.max = Integer.valueOf(this.txtMax.getText().toString());
        this.search.latitude = this.currentLatLng.latitude;//40.4101013; //
        this.search.longitude = this.currentLatLng.longitude;//-3.705122299999971;//
        this.search.measure = this.authenticationUser.measure;
        this.search.type = "users";
   //     this.search.tags.setTagList(lstTags.getObjects());
        if (this.radGrpPeriod.getCheckedRadioButtonId() == this.optHour.getId()) {
            this.search.period = "hour";
        } else if (this.radGrpPeriod.getCheckedRadioButtonId() == this.optDay.getId()) {
            this.search.period = "day";
        } else if (this.radGrpPeriod.getCheckedRadioButtonId() == this.optWeek.getId()) {
            this.search.period = "week";
        } else if (this.radGrpPeriod.getCheckedRadioButtonId() == this.optMonth.getId()) {
            this.search.period = "month";
        }
    }

}
