package com.koopey.view.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koopey.R;
import com.koopey.adapter.TagAdapter;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.ImageHelper;
import com.koopey.helper.SerializeHelper;
import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.Classification;
import com.koopey.model.Image;
import com.koopey.model.Location;
import com.koopey.model.Tag;
import com.koopey.model.Tags;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.model.type.ImageType;
import com.koopey.model.type.MeasureType;
import com.koopey.service.AssetService;
import com.koopey.service.ClassificationService;
import com.koopey.service.GalleryService;
import com.koopey.service.PositionService;
import com.koopey.service.TagService;
import com.koopey.view.MainActivity;
import com.koopey.view.component.TagTokenAutoCompleteView;
import com.tokenautocomplete.TokenCompleteTextView;

import java.util.Date;

public class AssetEditFragment extends Fragment implements AssetService.AssetCrudListener,
        ClassificationService.ClassificationSearchListener, ClassificationService.ClassificationCrudListener,
        GalleryService.GalleryListener, TokenCompleteTextView.TokenListener,
        View.OnClickListener {
    private Asset asset;
    private AssetService assetService;
    private AuthenticationUser authenticationUser;
    private ClassificationService classificationService;
    private EditText txtDescription, txtHeight, txtLength, txtName, txtValue, txtWeight, txtWidth;
    private FloatingActionButton btnSave, btnDelete;
    private GalleryService galleryService;
    private TextView txtCurrency, txtHeightDimension, txtLengthDimension, txtWeightDimension, txtWidthDimension;
    private ImageView imgFirst, imgSecond, imgThird, imgFourth;

    public Tags tags;
    private TagTokenAutoCompleteView lstTags;
    private TagAdapter tagAdapter;
    private TagService tagService;

    private boolean checkForm() {
        if (asset.getFirstImage() == null || asset.getFirstImage().isBlank()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_image) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtDescription.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_description) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtHeight.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_height) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtLength.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_length) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtName.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_name) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtWeight.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_weight) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else if (txtWidth.getText().equals("")) {
            Toast.makeText(getActivity(), getResources().getString(R.string.label_width) + ". " +
                    getResources().getString(R.string.error_field_required), Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onAssetRead(int code, String message, String assetId) {
    }

    @Override
    public void onAssetUpdateAvailable(int code, String message) {
        Toast.makeText(this.getActivity(), getResources().getString(R.string.info_update), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAssetCreate(int code, String message, String assetId) {
        Toast.makeText(this.getActivity(), getResources().getString(R.string.info_create), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAssetDelete(int code, String message) {
        Toast.makeText(this.getActivity(), getResources().getString(R.string.info_delete), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAssetUpdate(int code, String message) {
        Toast.makeText(this.getActivity(), getResources().getString(R.string.info_update), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnDelete.getId()) {
            assetService.deleteAsset(asset);
        } else if (v.getId() == btnSave.getId() && checkForm()) {
            if (asset == null) {
                asset = Asset.builder().build();
            }
            asset.setName(txtName.getText().toString());
            asset.setDescription(txtDescription.getText().toString());
            asset.setValue(Integer.valueOf(txtValue.getText().toString()));
            asset.setHeight(Integer.valueOf(txtValue.getText().toString()));
            asset.setLength(Integer.valueOf(txtValue.getText().toString()));
            asset.setWeight(Integer.valueOf(txtValue.getText().toString()));
            asset.setMeasure(authenticationUser.getMeasure());
            asset.setLocation(Location.builder()
                    .latitude(authenticationUser.getLatitude())
                    .longitude(authenticationUser.getLongitude()).build());

            assetService.createAsset(this.asset);

            Toast.makeText(this.getActivity(), getResources().getString(R.string.label_save), Toast.LENGTH_LONG).show();
        } else if (v.getId() == imgFirst.getId()) {
            galleryService.selectImage(ImageType.ASSET_FIRST);
        } else if (v.getId() == imgSecond.getId()) {
            galleryService.selectImage(ImageType.ASSET_SECOND);
        } else if (v.getId() == imgThird.getId()) {
            galleryService.selectImage(ImageType.ASSET_THIRD);
        } else if (v.getId() == imgFourth.getId()) {
            galleryService.selectImage(ImageType.ASSET_FOURTH);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        assetService = new AssetService(getContext());
        authenticationUser = ((MainActivity) getActivity()).getAuthenticationUser();
        classificationService = new ClassificationService(getContext());
        galleryService = new GalleryService(requireActivity().getActivityResultRegistry(), getActivity());
        tagService = new TagService(getContext());

        if (getActivity().getIntent().hasExtra("asset")) {
            asset = (Asset) getActivity().getIntent().getSerializableExtra("asset");
            getActivity().getIntent().removeExtra("asset");
        } else {
            asset = Asset.builder().build();
        }

        classificationService.setOnClassificationSearchListener(this);
        classificationService.searchByAsset(asset.getId());
        getLifecycle().addObserver(galleryService);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.asset_edit, container, false);
    }

    @Override
    public void onImageLoadFromGallery(Bitmap bitmap, String imageType) {
        if (imageType.equals(ImageType.ASSET_FIRST)) {
            asset.setFirstImage(ImageHelper.BitmapToSmallUri(bitmap));
        } else if (imageType.equals(ImageType.ASSET_SECOND)) {
            asset.setSecondImage(ImageHelper.BitmapToSmallUri(bitmap));
        } else if (imageType.equals(ImageType.ASSET_THIRD)) {
            asset.setThirdImage(ImageHelper.BitmapToSmallUri(bitmap));
        } else if (imageType.equals(ImageType.ASSET_FOURTH)) {
            asset.setFourthImage(ImageHelper.BitmapToSmallUri(bitmap));
        }
    }

    @Override
    public void onImageGalleryError(String error) {
        Log.d(AssetEditFragment.class.getSimpleName() + ".onImageGalleryError()", error);
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgFirst = getActivity().findViewById(R.id.imgFirst);
        imgSecond = getActivity().findViewById(R.id.imgSecond);
        imgThird = getActivity().findViewById(R.id.imgThird);
        imgFourth = getActivity().findViewById(R.id.imgFourth);
        txtCurrency = getActivity().findViewById(R.id.txtCurrency);
        txtDescription = getActivity().findViewById(R.id.txtDescription);
        txtHeight = getActivity().findViewById(R.id.txtHeight);
        txtHeightDimension = getActivity().findViewById(R.id.txtHeightDimension);
        txtLength = getActivity().findViewById(R.id.txtLength);
        txtLengthDimension = getActivity().findViewById(R.id.txtLengthDimension);
        txtName = getActivity().findViewById(R.id.txtName);
        txtValue = getActivity().findViewById(R.id.txtValue);
        txtWeight = getActivity().findViewById(R.id.txtWeight);
        txtWeightDimension = getActivity().findViewById(R.id.txtWeightDimension);
        txtWidth = getActivity().findViewById(R.id.txtWidth);
        txtWidthDimension = getActivity().findViewById(R.id.txtWidthDimension);

        lstTags = (TagTokenAutoCompleteView) getActivity().findViewById(R.id.lstTags);
        btnDelete = getActivity().findViewById(R.id.btnDelete);
        btnSave = getActivity().findViewById(R.id.btnSave);

        if (authenticationUser.getMeasure().equals(MeasureType.Metric)) {
            txtHeightDimension.setText("cm");
            txtLengthDimension.setText("cm");
            txtWeightDimension.setText("kg");
            txtWidthDimension.setText("cm");
        } else {
            txtHeightDimension.setText("inch.");
            txtLengthDimension.setText("inch.");
            txtWeightDimension.setText("lb");
            txtWidthDimension.setText("inch.");
        }


        btnDelete.setOnClickListener(this);
        btnSave.setOnClickListener(this);


        txtCurrency.setText(CurrencyHelper.currencyCodeToSymbol( authenticationUser.getCurrency()));


        this.populateTags();
    }

    private void populateForm() {

    }

    private void populateTags() {
        if (asset != null) {
            classificationService.searchByAsset(asset.getId());
        }
        tags = ((MainActivity) getActivity()).getTags();
    }


    @Override
    public void onClassificationSearchByTags(Assets assets) {

    }

    @Override
    public void onClassificationSearchByAsset(Tags assetTags) {
        this.tagAdapter = new TagAdapter(getActivity(), tags, assetTags, authenticationUser.getLanguage());

        // this.lstTags.allowDuplicates(false);
        this.lstTags.setAdapter(tagAdapter);
        this.lstTags.setTokenLimit(15);
    }

    @Override
    public void onClassificationCreate(int code, String message, String classificationId) {
        Log.d(AssetEditFragment.class.getSimpleName() , "onClassificationCreate()");
    }

    @Override
    public void onClassificationDelete(int code, String message, Classification classification) {
        Log.d(AssetEditFragment.class.getSimpleName() , "onClassificationDelete()");
    }

    @Override
    public void onClassificationUpdate(int code, String message, Classification classification) {

    }

    @Override
    public void onClassificationRead(int code, String message, Classification classification) {

    }

    @Override
    public void onTokenAdded(Object o) {
        classificationService.create( Classification.builder().assetId(asset.getId()).tagId(((Tag)o).getId()).build());
    }

    @Override
    public void onTokenIgnored(Object o) {

    }

    @Override
    public void onTokenRemoved(Object o) {
        classificationService.delete( Classification.builder().assetId(asset.getId()).tagId(((Tag)o).getId()).build());
    }
}