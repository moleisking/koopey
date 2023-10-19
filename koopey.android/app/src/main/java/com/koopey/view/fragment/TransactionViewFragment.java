package com.koopey.view.fragment;

import android.graphics.Color;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

/*import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;*/
import com.koopey.R;
import com.koopey.helper.CurrencyHelper;
import com.koopey.helper.DateTimeHelper;
import com.koopey.model.Transaction;

public class TransactionViewFragment extends Fragment {
    private TextView txtName, txtReference, txtValue, txtTotal, txtQuantity, txtCurrency1, txtCurrency2, txtStart, txtEnd, txtState;
    private ImageView imgSecret;
    private Transaction transaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.txtCurrency1 = getActivity().findViewById(R.id.txtCurrency1);
        this.txtCurrency2 = getActivity().findViewById(R.id.txtCurrency2);
        this.txtEnd = getActivity().findViewById(R.id.txtEnd);
        this.txtState = getActivity().findViewById(R.id.txtState);
        this.txtStart = getActivity().findViewById(R.id.txtStart);
        this.txtName = getActivity().findViewById(R.id.txtName);
        this.txtReference = getActivity().findViewById(R.id.txtReference);
        this.txtValue = getActivity().findViewById(R.id.txtValue);
        this.txtTotal = getActivity().findViewById(R.id.txtTotal);
        this.txtQuantity = getActivity().findViewById(R.id.txtQuantity);
        this.imgSecret = getActivity().findViewById(R.id.imgSecret);
        this.populateTransaction();


        if (getActivity().getIntent().hasExtra("transaction")) {
            this.transaction = (Transaction) getActivity().getIntent().getSerializableExtra("transaction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transaction_view, container, false);
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
            if (this.transaction != null) {
                this.txtName.setText(this.transaction.getName());
                this.txtReference.setText(this.transaction.getReference());
                this.txtValue.setText(String.valueOf(this.transaction.getValue()));
                this.txtTotal.setText(String.valueOf(this.transaction.getTotal()));
                this.txtQuantity.setText(String.valueOf(this.transaction.getQuantity()));
                this.txtState.setText(this.transaction.getType());
                this.txtStart.setText(DateTimeHelper.epochToString(this.transaction.getStart(),this.transaction.getTimeZone() ));
                this.txtEnd.setText(DateTimeHelper.epochToString(this.transaction.getEnd(),this.transaction.getTimeZone() ));
                this.txtCurrency1.setText(CurrencyHelper.currencyCodeToSymbol(this.transaction.getCurrency()));
                this.txtCurrency2.setText(CurrencyHelper.currencyCodeToSymbol(this.transaction.getCurrency()));
                if (this.transaction.isReceipt()){
                    this.txtState.setTextColor(Color.GREEN);
                }else  if (this.transaction.isQuote()) {
                    this.txtState.setTextColor(Color.RED);
                }else  if (this.transaction.isInvoice()) {
                    this.txtState.setTextColor(getActivity().getResources().getColor(R.color.color_orange));
                }
               this.trySetSecret();
            }
        } catch (Exception ex) {
            Log.d(TransactionViewFragment.class.getName(), ex.getMessage());
        }
    }
}
