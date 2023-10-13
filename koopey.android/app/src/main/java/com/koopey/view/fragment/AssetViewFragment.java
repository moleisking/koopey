package com.koopey.view.fragment;


import android.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.koopey.R;
import com.koopey.adapter.TagAdapter;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.ImageHelper;


import com.koopey.model.Asset;
import com.koopey.model.Tag;
import com.koopey.model.Tags;
import com.koopey.model.Transaction;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AuthenticationService;
import com.koopey.service.ClassificationService;
import com.koopey.view.MainActivity;
import com.koopey.view.component.TagTokenAutoCompleteView;

public class AssetViewFragment extends Fragment implements  View.OnClickListener {

    private Asset asset ;
    private AuthenticationUser authenticationUser ;
    private CheckBox txtSold;

    private ClassificationService classificationService;
    private FloatingActionButton btnMessage, btnPurchase, btnUpdate, btnDelete;
    private ImageView imgFirst, imgSecond,imgThird,imgFourth,imgAvatar;
    private RatingBar ratAverage;
    private TagAdapter tagAdapter;
    private Tags tags = new Tags();
    private TagTokenAutoCompleteView lstTags;
    private TextView txtAlias, txtCurrency, txtDescription, txtDistance, txtName, txtTitle, txtValue;

    @Override
    public void onClick(View v) {
        if (v.getId() == btnDelete.getId()) {
            this.showDeleteDialog();
        } else        if (v.getId() == btnMessage.getId()) {
            //Send user to main then to message fragment
            this.getActivity().getIntent().putExtra("Asset", asset);
            ((MainActivity) getActivity()).showMessageListFragment();
        } else     if (v.getId() == btnDelete.getId()) {
            //            ((MainActivity) getActivity()).showMessageListFragment();
        } else if (v.getId() == btnPurchase.getId()) {
            Transaction myTransaction ;
            //  ((MainActivity) getActivity()).showTransactionCreateFragment(myTransaction);
        } else if (v.getId() == btnUpdate.getId()) {
            // ((MainActivity) getActivity()).showAssetUpdateFragment(this.asset);
        } else if (v.getId() == imgFirst.getId()) {
            //  this.showImageListFragment();
        } else if (v.getId() == this.imgAvatar.getId()) {
            //  ((MainActivity) getActivity()).showUserReadFragment(this.asset.user);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthenticationService authenticationService = new AuthenticationService(getContext());
        authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();

        if (getActivity().getIntent().hasExtra("asset")) {
            asset = (Asset) getActivity().getIntent().getSerializableExtra("asset");
            classificationService.searchByAsset(asset.getId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.asset_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnDelete = getActivity().findViewById(R.id.btnDelete);
        btnMessage = getActivity().findViewById(R.id.btnMessage);
        btnPurchase = getActivity().findViewById(R.id.btnPurchase);
        btnUpdate = getActivity().findViewById(R.id.btnUpdate);

        imgAvatar = getActivity().findViewById(R.id.imgAvatar);
        imgFirst = getActivity().findViewById(R.id.imgFirst);
        imgSecond = getActivity().findViewById(R.id.imgSecond);
        imgThird = getActivity().findViewById(R.id.imgThird);
        imgFourth = getActivity().findViewById(R.id.imgFourth);
        // this.lstTags = (TagTokenAutoCompleteView) getActivity().findViewById(R.id.lstTags);
        ratAverage = getActivity().findViewById(R.id.ratAverage);
        txtAlias = getActivity().findViewById(R.id.txtAlias);
        txtCurrency = getActivity().findViewById(R.id.txtCurrency);
        txtDescription = getActivity().findViewById(R.id.txtDescription);
        txtDistance = getActivity().findViewById(R.id.txtDistance);
        txtName = getActivity().findViewById(R.id.txtName);
        txtTitle = getActivity().findViewById(R.id.txtTitle);
        txtValue = getActivity().findViewById(R.id.txtValue);

        btnDelete.setOnClickListener(this);
        btnMessage.setOnClickListener(this);
        btnPurchase.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);

        imgAvatar.setOnClickListener(this);
        imgFirst.setOnClickListener(this);
        imgSecond.setOnClickListener(this);
        imgThird.setOnClickListener(this);
        imgFourth.setOnClickListener(this);

        //Show of hide components
        this.populateTags();
        this.populateAsset();
        this.setVisibility();


        ((MainActivity) getActivity()).hideKeyboard();
    }



    private void populateTags() {
        this.tagAdapter = new TagAdapter(this.getActivity(), this.tags, this.authenticationUser.getLanguage());
       // this.lstTags.setAdapter(tagAdapter);
        //this.lstTags.allowDuplicates(false);
    }

    public void populateAsset() {
        if (this.asset != null) {
           // this.txtAlias.setText(this.asset.user.alias);
          //  this.txtName.setText(this.asset.user.name);
            this.txtTitle.setText(this.asset.getName());
            this.txtDescription.setText(this.asset.getDescription());
            this.txtValue.setText(asset.getValueAsString());
            this.txtCurrency.setText(CurrencyHelper.currencyCodeToSymbol(this.asset.getCurrency()));

            for (Tag t : this.tags) {
             //   this.lstTags.addObject(t);
            }

            if (!this.asset.getFirstImage().isEmpty()) {
                this.imgFirst.setImageBitmap(asset.getFirstImageAsBitmap());
            } else {
                this.imgFirst.setImageBitmap(ImageHelper.UriToBitmap(getResources().getString(R.string.default_user_image)));
            }

           /* if (ImageHelper.isImageUri(this.asset.user.avatar)) {
                this.imgAvatar.setImageBitmap(ImageHelper.IconBitmap(this.asset.user.avatar));
            } else {
                this.imgAvatar.setImageBitmap(ImageHelper.IconBitmap(getResources().getString(R.string.default_user_image)));
            }*/
        }
    }



    public void setVisibility() {
        //Alias
        if (getResources().getBoolean(R.bool.alias) &&
                !getResources().getBoolean(R.bool.alias)) {
            this.txtAlias.setVisibility(View.VISIBLE);
        } else {
            this.txtAlias.setVisibility(View.GONE);
        }
        //Delete
       /* if (this.authenticationUser.id.equals(this.asset.user.id)) {
            this.btnDelete.setVisibility(View.VISIBLE);
        } else {
            this.btnDelete.setVisibility(View.GONE);
        }*/
        //Name
        if (getResources().getBoolean(R.bool.name)) {
            this.txtName.setVisibility(View.VISIBLE);
        } else {
            this.txtName.setVisibility(View.GONE);
        }
        //Transactions
        if (getResources().getBoolean(R.bool.transactions)) {
            this.btnPurchase.setVisibility(View.VISIBLE);
        } else {
            this.btnPurchase.setVisibility(View.GONE);
        }
        //Update
       /* if (this.authUser.id.equals(this.asset.user.id)) {
            this.btnUpdate.setVisibility(View.VISIBLE);
        } else {
            this.btnUpdate.setVisibility(View.GONE);
        }*/
    }

    public void showImageListFragment() {
      //  this.getActivity().getIntent().putExtra("images", asset.images);
      //  this.getActivity().getIntent().putExtra("showCreateButton", false);
      // this.getActivity().getIntent().putExtra("showUpdateButton", false);
       // this.getActivity().getIntent().putExtra("showDeleteButton", false);
       /* this.getFragmentManager().beginTransaction()
                .replace(R.id.toolbar_main_frame, new ImageListFragment())
                .addToBackStack("fragment_images")
                .commit();*/
    }

    private void showDeleteDialog() {
        new AlertDialog.Builder(getActivity())
                .setTitle(getResources().getString(R.string.label_delete))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                      // postAssetDelete();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }
}
