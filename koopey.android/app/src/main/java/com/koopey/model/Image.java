package com.koopey.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.Comparator;
import java.util.UUID;

import com.koopey.model.base.Base;
import com.koopey.view.component.RoundImage;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class Image extends Base {

    @Builder.Default
    public String uri = "";
    @Builder.Default
    public int width = 512;
    @Builder.Default
    public int height = 512;

    public boolean isEmpty() {
        if (this.isEmpty() && this.uri.equals("") ) {
            return true;
        } else {
            return false;
        }
    }

    public void setUri(Bitmap bitmap) {
        //Resize image to 512 * 512
        Bitmap scaleBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        scaleBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        this.uri = "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }

    public Bitmap getBitmap() {
        //gets bitmap from encoded data of Uri string
        byte[] arr = Base64.decode(this.uri.split(",", 2)[1], Base64.DEFAULT);
        // ByteArrayInputStream inputStream = new ByteArrayInputStream (arr);
        return BitmapFactory.decodeByteArray(arr, 0, arr.length);
    }

    public Uri getUri() {
        return Uri.parse(this.uri);
    }

    public String getSmallUri() {
        Bitmap smallImage = Bitmap.createScaledBitmap(this.getBitmap(), 256, 256, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        smallImage.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return "data:image/png;base64," + Base64.encodeToString(byteArray, Base64.NO_WRAP);
    }


    public RoundImage getRoundBitmap() {
        Bitmap temp = Bitmap.createScaledBitmap(this.getBitmap(), 100, 100, true);
        RoundImage roundedImage = new RoundImage(temp);
        return roundedImage;
    }

    public int getBitmapFileSize(Bitmap bitmap) {
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, bao);
        byte[] ba = bao.toByteArray();
        //returns size in bytes
        Log.d(Image.class.getName(), Integer.toString(ba.length));
        return ba.length;
    }

    public String getUriMD5() {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(this.uri.getBytes());
            result = messageDigest.toString();
        } catch (Exception ex) {
            Log.d(Image.class.getName(), ex.getMessage());
        }
        return result;
    }

    public String getPeakUri() {
        return uri.length() > 100 ? uri.substring(0, 99) : uri;
    }

}
