package com.koopey.helper;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.koopey.view.fragment.TransactionEditFragment;

public class QRCodeHelper {

    public static Bitmap StringToQRCodeBitmap(String secret){
        Bitmap qrCode = null;
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(secret, BarcodeFormat.QR_CODE, 1024, 1024);
            qrCode                = BitmapFromBitMatrix(bitMatrix);
        } catch (Exception e) {
            Log.d(TransactionEditFragment.class.getSimpleName(), e.getMessage());
        }
        return qrCode;
    }

    public static Bitmap BitmapFromBitMatrix(BitMatrix matrix) {
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        int[] pixels = new int[width * height];
        int WHITE = 0xFF000000;
        int BLACK = 0xFFFFFFFF;
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = matrix.get(x, y) ? WHITE : BLACK;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }
}
