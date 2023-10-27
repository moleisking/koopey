package com.koopey.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.TimeUnit;

import com.koopey.R;
import com.koopey.model.Advert;

public class AdvertFragment extends Fragment implements RadioGroup.OnCheckedChangeListener {

    public OnAdvertChangeListener delegate = null;
    RadioGroup.OnCheckedChangeListener radGrplistener;
    private EditText txtValue;
    private RadioGroup optsPeriod;
    private RadioButton optNone, optDay, optWeek, optMonth;
    private Advert advert;
    private Context context;
    private LinearLayout layoutAdvert;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.advert = Advert.builder().build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.advert, container, false);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        try {
            if (checkedId == optNone.getId()) {
                this.txtValue.setText("0");
                this.advert.setType("none");
                this.advert.setStart( System.currentTimeMillis());
                this.advert.setEnd(System.currentTimeMillis());
                this.delegate.updateAdvertEvent(advert);
            } else if (checkedId == optDay.getId()) {
                this.advert.setType("day");
                this.advert.setStart( System.currentTimeMillis());
                this.advert.setEnd(this.advert.getStart() + TimeUnit.DAYS.toMillis(1));
                this.txtValue.setText(String.valueOf(getResources().getInteger(R.integer.advert_day_value)));
                this.delegate.updateAdvertEvent(advert);
            } else if (checkedId == optWeek.getId()) {
                this.advert.setType("week");
                this.advert.setStart(System.currentTimeMillis());
                this.advert.setEnd(this.advert.getStart() + TimeUnit.DAYS.toMillis(7));
                this.txtValue.setText(String.valueOf(getResources().getInteger(R.integer.advert_week_value)));
                this.delegate.updateAdvertEvent(advert);
            } else if (checkedId == optMonth.getId()) {
                this.advert.setType("month");
                this.advert.setStart( System.currentTimeMillis());
                this.advert.setEnd(this.advert.getStart() + TimeUnit.DAYS.toMillis(30));
                this.txtValue.setText(String.valueOf(getResources().getInteger(R.integer.advert_month_value)));
                this.delegate.updateAdvertEvent(advert);
            }
        } catch (Exception ex) {
            Log.d(AdvertFragment.class.getSimpleName(), ex.getMessage());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.layoutAdvert = getActivity().findViewById(R.id.layoutAdvert);
        this.txtValue = getActivity().findViewById(R.id.txtValue);
        this.optsPeriod = getActivity().findViewById(R.id.optsPeriod);
        this.optNone = getActivity().findViewById(R.id.optNone);
        this.optDay = getActivity().findViewById(R.id.optDay);
        this.optWeek = getActivity().findViewById(R.id.optWeek);
        this.optMonth = getActivity().findViewById(R.id.optMonth);

        this.optsPeriod.setOnCheckedChangeListener(this);
    }

    public Advert getAdvert() {
        return advert;
    }

    public void setAdvert(Advert advert) {
        this.advert = advert;
    }

    public void setOnAdvertChangeListener(OnAdvertChangeListener delegate) {
        this.delegate = delegate;
    }

    public interface OnAdvertChangeListener {
        void updateAdvertEvent(Advert advert);
    }
}
