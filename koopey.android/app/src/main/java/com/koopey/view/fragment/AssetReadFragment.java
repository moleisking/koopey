package com.koopey.view.fragment;


import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.koopey.R;
import com.koopey.adapter.TagAdapter;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.ImageHelper;


import com.koopey.model.Alert;
import com.koopey.model.Asset;
import com.koopey.model.Tag;
import com.koopey.model.Tags;
import com.koopey.model.Transaction;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.AuthenticationService;
import com.koopey.view.PrivateActivity;
import com.koopey.view.component.TagTokenAutoCompleteView;

/**
 * Created by Scott on 18/01/2017.
 */
public class AssetReadFragment extends Fragment implements  View.OnClickListener {

    private Asset asset ;
    private AuthenticationUser authenticationUser ;


    private CheckBox txtSold;
    private FloatingActionButton btnMessage, btnPurchase, btnUpdate, btnDelete;
    private ImageView imgAsset, imgAvatar;
    private RatingBar ratAverage;
    private TagAdapter tagAdapter;
    private Tags tags = new Tags();
    private TagTokenAutoCompleteView lstTags;
    private TextView txtAlias, txtCurrency, txtDescription, txtDistance, txtName, txtTitle, txtValue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AuthenticationService authenticationService = new AuthenticationService(getContext());
        this.authenticationUser = authenticationService.getLocalAuthenticationUserFromFile();

        if (getActivity().getIntent().hasExtra("asset")) {
            this.asset = (Asset) getActivity().getIntent().getSerializableExtra("asset");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_asset_read, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Set views
        this.btnDelete = (FloatingActionButton) getActivity().findViewById(R.id.btnDelete);
        this.btnMessage = (FloatingActionButton) getActivity().findViewById(R.id.btnMessage);
        this.btnPurchase = (FloatingActionButton) getActivity().findViewById(R.id.btnPurchase);
        this.btnUpdate = (FloatingActionButton) getActivity().findViewById(R.id.btnUpdate);
        this.imgAsset = (ImageView) getActivity().findViewById(R.id.imgAsset);
        this.imgAvatar = (ImageView) getActivity().findViewById(R.id.imgAvatar);
        // this.lstTags = (TagTokenAutoCompleteView) getActivity().findViewById(R.id.lstTags);
        this.ratAverage = (RatingBar) getActivity().findViewById(R.id.ratAverage);
        this.txtAlias = (TextView) getActivity().findViewById(R.id.txtAlias);
        this.txtCurrency = (TextView) getActivity().findViewById(R.id.txtCurrency);
        this.txtDescription = (TextView) getActivity().findViewById(R.id.txtDescription);
        this.txtDistance = (TextView) getActivity().findViewById(R.id.txtDistance);
        this.txtName = (TextView) getActivity().findViewById(R.id.txtName);
        this.txtTitle = (TextView) getActivity().findViewById(R.id.txtTitle);
        this.txtValue = (TextView) getActivity().findViewById(R.id.txtValue);

        //Set listeners
        this.btnDelete.setOnClickListener(this);
        this.btnMessage.setOnClickListener(this);
        this.btnPurchase.setOnClickListener(this);
        this.btnUpdate.setOnClickListener(this);
        this.imgAsset.setOnClickListener(this);
        this.imgAvatar.setOnClickListener(this);

        //Show of hide components
        this.populateTags();
        this.populateAsset();
        this.setVisibility();


        ((PrivateActivity) getActivity()).hideKeyboard();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnDelete.getId()) {
            this.showDeleteDialog();
        } else        if (v.getId() == btnMessage.getId()) {
            //Send user to main then to message fragment
            this.getActivity().getIntent().putExtra("Asset", asset);
            ((PrivateActivity) getActivity()).showMessageListFragment();
        } else     if (v.getId() == btnDelete.getId()) {
       //            ((PrivateActivity) getActivity()).showMessageListFragment();
        } else if (v.getId() == btnPurchase.getId()) {
            Transaction myTransaction ;
          //  ((PrivateActivity) getActivity()).showTransactionCreateFragment(myTransaction);
        } else if (v.getId() == btnUpdate.getId()) {
           // ((PrivateActivity) getActivity()).showAssetUpdateFragment(this.asset);
        } else if (v.getId() == imgAsset.getId()) {
            this.showImageListFragment();
        } else if (v.getId() == this.imgAvatar.getId()) {
          //  ((PrivateActivity) getActivity()).showUserReadFragment(this.asset.user);
        }
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

            for (Tag t : this.asset.tags) {
             //   this.lstTags.addObject(t);
            }

            if (this.asset.images.size() > 0) {
                this.imgAsset.setImageBitmap(asset.images.get(0).getBitmap());
            } else {
                this.imgAsset.setImageBitmap(ImageHelper.UriToBitmap(getResources().getString(R.string.default_user_image)));
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
        this.getActivity().getIntent().putExtra("images", asset.images);
        this.getActivity().getIntent().putExtra("showCreateButton", false);
        this.getActivity().getIntent().putExtra("showUpdateButton", false);
        this.getActivity().getIntent().putExtra("showDeleteButton", false);
        this.getFragmentManager().beginTransaction()
                .replace(R.id.toolbar_main_frame, new ImageListFragment())
                .addToBackStack("fragment_images")
                .commit();
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
