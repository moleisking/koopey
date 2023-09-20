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
import com.koopey.view.PrivateActivity;
import com.koopey.view.component.PrivateFragment;

/**
 * Created by Scott on 06/04/2017.
 */
public class TransactionReadFragment extends PrivateFragment {
    private TextView txtName, txtReference, txtValue, txtTotal, txtQuantity, txtCurrency1, txtCurrency2, txtStart, txtEnd, txtState;
    private ImageView imgSecret;
    private Transaction transaction;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.txtCurrency1 = (TextView) getActivity().findViewById(R.id.txtCurrency1);
        this.txtCurrency2 = (TextView) getActivity().findViewById(R.id.txtCurrency2);
        this.txtEnd = (TextView) getActivity().findViewById(R.id.txtEnd);
        this.txtState = (TextView) getActivity().findViewById(R.id.txtState);
        this.txtStart = (TextView) getActivity().findViewById(R.id.txtStart);
        this.txtName = (TextView) getActivity().findViewById(R.id.txtName);
        this.txtReference = (TextView) getActivity().findViewById(R.id.txtReference);
        this.txtValue = (TextView) getActivity().findViewById(R.id.txtValue);
        this.txtTotal = (TextView) getActivity().findViewById(R.id.txtTotal);
        this.txtQuantity = (TextView) getActivity().findViewById(R.id.txtQuantity);
        this.imgSecret = (ImageView) getActivity().findViewById(R.id.imgSecret);
        this.populateTransaction();


        if (getActivity().getIntent().hasExtra("transaction")) {
            this.transaction = (Transaction) getActivity().getIntent().getSerializableExtra("transaction");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transaction_read, container, false);
    }

    private void trySetSecret(){
        if (this.transaction != null) {
            try {
                if ( !this.transaction.isReceipt() && !this.transaction.secret.equals("") && (this.transaction.secret.length() > 0)) {
                  /*  QRCodeWriter qrCodeWriter = new QRCodeWriter();
                    BitMatrix bitMatrix = qrCodeWriter.encode(this.transaction.secret, BarcodeFormat.QR_CODE, 1024, 1024);
                    this.imgSecret.setImageBitmap(ImageHelper.BitmapFromBitMatrix(bitMatrix));*/
                } else {
                    this.imgSecret.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                Log.d(TransactionReadFragment.class.getName(), e.getMessage());
            }
        }
    }

    private void populateTransaction() {
        try {
            if (this.transaction != null) {
                this.txtName.setText(this.transaction.name);
                this.txtReference.setText(this.transaction.reference);
                this.txtValue.setText(String.valueOf(this.transaction.itemValue));
                this.txtTotal.setText(String.valueOf(this.transaction.totalValue));
                this.txtQuantity.setText(String.valueOf(this.transaction.quantity));
                this.txtState.setText(this.transaction.state);
                this.txtStart.setText(DateTimeHelper.epochToString(this.transaction.startTimeStamp,this.transaction.timeZone ));
                this.txtEnd.setText(DateTimeHelper.epochToString(this.transaction.endTimeStamp,this.transaction.timeZone ));
                this.txtCurrency1.setText(CurrencyHelper.currencyCodeToSymbol(this.transaction.currency));
                this.txtCurrency2.setText(CurrencyHelper.currencyCodeToSymbol(this.transaction.currency));
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
            Log.d(TransactionReadFragment.class.getName(), ex.getMessage());
        }
    }
}
