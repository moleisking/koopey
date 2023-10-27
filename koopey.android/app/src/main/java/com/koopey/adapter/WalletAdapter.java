package com.koopey.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/*import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;*/

import com.koopey.R;
import com.koopey.helper.QRCodeHelper;
import com.koopey.model.Wallet;
import com.koopey.model.Wallets;

public class WalletAdapter extends ArrayAdapter<Wallet> {

    private boolean showImage = true;
    private boolean showValue = false;

    public WalletAdapter(Context context, Wallets wallets, boolean showImages, boolean showValues) {
        super(context, 0, wallets);
        this.showImage = showImages;
        this.showValue = showValues;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)    {
        try {
            Wallet wallet = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.wallet_row, parent, false);
            }

            ImageView imgQRCode = convertView.findViewById(R.id.imgQRCodeItem);
            TextView txtCurrency = convertView.findViewById(R.id.txtCurrencyItem);
            TextView txtValue = convertView.findViewById(R.id.txtValueItem);

            txtCurrency.setText(wallet.getCurrency().toUpperCase());
            if (this.showValue) {
                txtValue.setVisibility(View.VISIBLE);
                txtValue.setText(Double.toString(wallet.getValue()));
            } else{
                txtValue.setVisibility(View.INVISIBLE);
            }
            try {
                if( this.showImage && !wallet.getName().equals("") && (wallet.getName().length() > 0)  ) {
                    imgQRCode.setImageBitmap(QRCodeHelper.StringToQRCodeBitmap(wallet.getId()));
                } else {
                    imgQRCode.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                Log.d(WalletAdapter.class.getName(),e.getMessage());
            }
            // Return the completed view to render on screen
        }catch (Exception ex){
            Log.d(WalletAdapter.class.getName(),ex.getMessage());
        }
        return convertView;
    }
}
