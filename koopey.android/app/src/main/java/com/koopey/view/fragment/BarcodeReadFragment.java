package com.koopey.view.fragment;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

/*import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;*/
import com.koopey.R;
import com.koopey.view.PrivateActivity;

/**
 * Created by Scott on 06/04/2017.
 */
public class BarcodeReadFragment extends Fragment {
    private final String LOG_HEADER = "BARCODE:READ";
    private ImageView imgSecret;
    private String barcode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((PrivateActivity) getActivity()).setTitle(getResources().getString(R.string.label_barcode));
        ((PrivateActivity) getActivity()).hideKeyboard();

        if (getActivity().getIntent().hasExtra("barcode")) {
            this.barcode = this.getActivity().getIntent().getStringExtra("barcode");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_barcode_read, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.imgSecret = (ImageView) getActivity().findViewById(R.id.imgSecret);
        this.populateBarcode();
    }

    private void  populateBarcode(){
        if (this.barcode != null) {
            try {
                if (  !this.barcode.equals("") && (this.barcode.length() > 0)) {
                 /*   QRCodeWriter qrCodeWriter = new QRCodeWriter();
                    BitMatrix bitMatrix = qrCodeWriter.encode(this.barcode, BarcodeFormat.QR_CODE, 1024, 1024);
                    this.imgSecret.setImageBitmap(ImageHelper.BitmapFromBitMatrix(bitMatrix));*/
                } else {
                    this.imgSecret.setImageDrawable(this.getActivity().getResources().getDrawable(R.drawable.ic_cancel_black_24dp));
                }
            } catch (Exception e) {
                Log.d(LOG_HEADER + ":ER", e.getMessage());
            }
        }
    }
}
