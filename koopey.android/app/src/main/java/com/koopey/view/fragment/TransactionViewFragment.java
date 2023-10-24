package com.koopey.view.fragment;

import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/*import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;*/
import com.koopey.R;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.DateTimeHelper;
import com.koopey.model.Transaction;

public class TransactionViewFragment extends Fragment {
    private TextView txtCurrency1, txtCurrency2, txtDescription, txtEnd, txtName,   txtStart,  txtTotal,    txtType, txtQuantity, txtValue;
    private ImageView imgSecret;
    private Transaction transaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getActivity().getIntent().hasExtra("transaction")) {
            transaction = (Transaction) getActivity().getIntent().getSerializableExtra("transaction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_view, container, false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getActivity().getIntent().hasExtra("transaction")) {
            getActivity().getIntent().removeExtra("transaction");
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtCurrency1 = getActivity().findViewById(R.id.txtCurrency1);
        txtCurrency2 = getActivity().findViewById(R.id.txtCurrency2);
        txtDescription = getActivity().findViewById(R.id.txtDescription);
        txtEnd = getActivity().findViewById(R.id.txtEnd);
        txtType = getActivity().findViewById(R.id.txtType);
        txtStart = getActivity().findViewById(R.id.txtStart);
        txtName = getActivity().findViewById(R.id.txtName);
        txtValue = getActivity().findViewById(R.id.txtValue);
        txtTotal = getActivity().findViewById(R.id.txtTotal);
        txtQuantity = getActivity().findViewById(R.id.txtQuantity);
        imgSecret = getActivity().findViewById(R.id.imgSecret);
        this.populateTransaction();
    }

    private void trySetSecret(){
        if (this.transaction != null) {
            try {
                if ( !this.transaction.isReceipt() && !this.transaction.getSecret().equals("") && (this.transaction.getSecret().length() > 0)) {
                  /*  QRCodeWriter qrCodeWriter = new QRCodeWriter();
                    BitMatrix bitMatrix = qrCodeWriter.encode(this.transaction.secret, BarcodeFormat.QR_CODE, 1024, 1024);
                    this.imgSecret.setImageBitmap(ImageHelper.BitmapFromBitMatrix(bitMatrix));*/
                } else {
                    this.imgSecret.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                Log.d(TransactionViewFragment.class.getName(), e.getMessage());
            }
        }
    }

    private void populateTransaction() {
        try {
            if (transaction != null) {
                txtName.setText(this.transaction.getName());
                txtDescription.setText(this.transaction.getDescription());
                txtValue.setText(String.valueOf(this.transaction.getValue()));
                txtTotal.setText(String.valueOf(this.transaction.getTotal()));
                txtQuantity.setText(String.valueOf(this.transaction.getQuantity()));
                txtType.setText(this.transaction.getType());
                txtStart.setText(DateTimeHelper.epochToString(this.transaction.getStart(),this.transaction.getTimeZone() ));
                txtEnd.setText(DateTimeHelper.epochToString(this.transaction.getEnd(),this.transaction.getTimeZone() ));
                txtCurrency1.setText(CurrencyHelper.currencyCodeToSymbol(this.transaction.getCurrency()));
                txtCurrency2.setText(CurrencyHelper.currencyCodeToSymbol(this.transaction.getCurrency()));
                if (transaction.isReceipt()){
                    txtType.setTextColor(Color.GREEN);
                }else  if (transaction.isQuote()) {
                    txtType.setTextColor(Color.RED);
                }else  if (this.transaction.isInvoice()) {
                    txtType.setTextColor(getActivity().getResources().getColor(R.color.color_orange));
                }
               this.trySetSecret();
            }
        } catch (Exception ex) {
            Log.d(TransactionViewFragment.class.getName(), ex.getMessage());
        }
    }
}
