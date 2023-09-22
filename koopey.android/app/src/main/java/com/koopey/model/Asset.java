package com.koopey.model;
/*
* Bitmap class is non serializable amd therefore not used
* */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URI;
import java.util.Comparator;
import java.util.UUID;

//import com.google.android.maps.GeoPoint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.koopey.R;
import com.koopey.model.base.Base;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
//import com.koopey.view.RoundImage;
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Asset extends Base {

    @Builder.Default
    public Advert advert = new Advert();
    //public User user = new User();
    //Arrays
    @Builder.Default
    public Images images = new Images();
    public Location location ;
    public Transactions transactions;
    public Tags tags;
    public static final String ASSET_FILE_NAME = "asset.dat";
    private  String buyerId ;
    private  String sellerId ;
    private  String description ;
    private  String dimensiontUnit ;
    private  String weightUnit ;
    @Builder.Default
    private  String currency = "eur";
    private  String fileData;
    private  String fileName;
    private  String fileType ;
       @Builder.Default
       private  Double width = 0.0d; //cm
    @Builder.Default
    private  Double height = 0.0d; //cm
    @Builder.Default
    private  Double length = 0.0d; //cm
    @Builder.Default
    private  Double weight = 0.0d; //kg
    @Builder.Default
    private  Double value = 200d;
    @Builder.Default
    private  long fileSize = 0;
    @Builder.Default
    private  long manufactureDate = 0;
    @Builder.Default
    private  int distance = 0;
    @Builder.Default
    private  int quantity = 0;
    @Builder.Default
    private  boolean available = true;



  /*  @Override
    public int compare(Asset o1, Asset o2) {
        if (o1.hashCode() < o2.hashCode()) {
            return -1;
        } else if (o1.hashCode() > o2.hashCode()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int compareTo(Asset o) {
        return compare(this, o);
    }*/

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    /*********
     * Checks
     *********/

    public boolean isValid() {
        boolean hasImage = false;

        //Check if at least one images was uploaded
        for (int i = 0; i < 4; i++) {
            if (!this.images.get(i).uri.equals("")) {
                hasImage = true;
            }
        }
        //Note* userid is also passed in token so userid check is not necessary
        if (hasImage && !this.getName().equals("") && this.value >= 0 && this.tags.size() >= 0) {
            return true;
        } else {
            return false;
        }
    }



    /*********
     * Helpers
     *********/

    public String getValueAsString() {
        return Double.toString(value);
    }




    public boolean isEmpty() {
        return buyerId == null || sellerId == null  || buyerId.length() <= 0 || sellerId.length() <= 0 || super.isEmpty() ? true : false;
    }
}
