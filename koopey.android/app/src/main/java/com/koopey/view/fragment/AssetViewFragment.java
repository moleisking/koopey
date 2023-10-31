package com.koopey.view.fragment;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.koopey.R;
import com.koopey.adapter.TagAdapter;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.ImageHelper;

import com.koopey.model.Asset;
import com.koopey.model.Assets;
import com.koopey.model.Message;
import com.koopey.model.Tags;
import com.koopey.model.Transaction;
import com.koopey.model.User;
import com.koopey.model.authentication.AuthenticationUser;
import com.koopey.service.ClassificationService;
import com.koopey.service.UserService;
import com.koopey.view.MainActivity;
import com.koopey.view.component.TagTokenAutoCompleteView;

import java.net.HttpURLConnection;

public class AssetViewFragment extends Fragment implements View.OnClickListener, ClassificationService.ClassificationSearchListener,
        UserService.UserReadListener {

    private Asset asset;
    private AuthenticationUser authenticationUser;
    private ClassificationService classificationService;
    private CheckBox txtSold;
    private FloatingActionButton btnMessage, btnPurchase, btnUpdate, btnDelete;
    private ImageView imgFirst, imgSecond, imgThird, imgFourth, imgAvatar;
    private RatingBar ratAverage;
    private TagAdapter tagAdapter;
    private TagTokenAutoCompleteView lstTags;
    private TextView txtAlias, txtCurrency, txtDescription, txtDistance, txtName, txtTitle, txtValue;

    private UserService userService;

    private void bindAdapter() {
        this.tagAdapter = new TagAdapter(this.getActivity(), asset.getTags(), this.authenticationUser.getLanguage());
        this.lstTags.setAdapter(tagAdapter);
    }

    @Override
    public void onClassificationSearchByTags(int code, String message, Assets assets) {

    }

    @Override
    public void onClassificationSearchByAsset(int code, String message, Tags tags) {
        if (code == HttpURLConnection.HTTP_OK) {
            asset.setTags(tags);
            bindAdapter();
        } else if (code == HttpURLConnection.HTTP_NO_CONTENT) {
            Toast.makeText(this.getActivity(), getResources().getString(R.string.error_empty), Toast.LENGTH_LONG).show();
        } else if (code == HttpURLConnection.HTTP_BAD_REQUEST) {
            Toast.makeText(this.getActivity(), getResources().getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == btnMessage.getId()) {
            this.getActivity().getIntent().putExtra("message", Message.builder()
                    .senderId(asset.getSellerId())
                    .language(authenticationUser.getLanguage())
                    .description(asset.getName()).build());
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_messages);
        } else if (v.getId() == btnPurchase.getId()) {
            getActivity().getIntent().putExtra("transaction", Transaction.builder().assetId(asset.getId()).build());
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_transaction_edit);
        } else if (v.getId() == imgAvatar.getId()) {
            Navigation.findNavController(this.getActivity(), R.id.fragment_public).navigate(R.id.navigation_user_view);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationUser = ((MainActivity) getActivity()).getAuthenticationUser();
        ((MainActivity) getActivity()).hideKeyboard();
        classificationService = new ClassificationService(getContext());
        classificationService.setOnClassificationSearchListener(this);
        userService = new UserService(getContext());
        userService.read(asset.getSellerId());

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
    public void onStart() {
        super.onStart();
        if (this.asset != null) {
            this.txtTitle.setText(this.asset.getName());
            this.txtDescription.setText(this.asset.getDescription());
            this.txtValue.setText(asset.getValueAsString());
            this.txtCurrency.setText(CurrencyHelper.currencyCodeToSymbol(this.asset.getCurrency()));

            if (!this.asset.getFirstImage().isEmpty()) {
                this.imgFirst.setImageBitmap(asset.getFirstImageAsBitmap());
            } else {
                this.imgFirst.setImageBitmap(ImageHelper.UriToBitmap(getResources().getString(R.string.default_user_image)));
            }

            if (!this.asset.hasSecondImage()) {
                this.imgSecond.setImageBitmap(asset.getSecondImageAsBitmap());
            } else {
                this.imgSecond.setImageBitmap(ImageHelper.UriToBitmap(getResources().getString(R.string.default_user_image)));
            }

            if (!this.asset.hasThirdImage()) {
                this.imgThird.setImageBitmap(asset.getThirdImageAsBitmap());
            } else {
                this.imgThird.setImageBitmap(ImageHelper.UriToBitmap(getResources().getString(R.string.default_user_image)));
            }

            if (!this.asset.hasFourthImage()) {
                this.imgFourth.setImageBitmap(asset.getFourthImageAsBitmap());
            } else {
                this.imgFourth.setImageBitmap(ImageHelper.UriToBitmap(getResources().getString(R.string.default_user_image)));
            }

            if (ImageHelper.isImageUri(this.asset.getSeller().getAvatar())) {
                this.imgAvatar.setImageBitmap(ImageHelper.IconBitmap(this.asset.getSeller().getAvatar()));
            } else {
                this.imgAvatar.setImageBitmap(ImageHelper.IconBitmap(getResources().getString(R.string.default_user_image)));
            }
        }
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
        lstTags = (TagTokenAutoCompleteView) getActivity().findViewById(R.id.lstTags);
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
    }

    @Override
    public void onUserRead(int code, String message, User user) {
        if (code == HttpURLConnection.HTTP_OK) {
            asset.setSeller(user);
            ;
            imgAvatar.setImageBitmap(ImageHelper.IconBitmap(user.getAvatar()));
            txtAlias.setText(user.getAlias());
            Toast.makeText(this.getActivity(), getResources().getString(R.string.label_search), Toast.LENGTH_LONG).show();
        } else if (code == HttpURLConnection.HTTP_NO_CONTENT) {
            Toast.makeText(this.getActivity(), getResources().getString(R.string.error_empty), Toast.LENGTH_LONG).show();
        } else if (code == HttpURLConnection.HTTP_BAD_REQUEST) {
            Toast.makeText(this.getActivity(), getResources().getString(R.string.error_connection), Toast.LENGTH_LONG).show();
        }
    }
}
