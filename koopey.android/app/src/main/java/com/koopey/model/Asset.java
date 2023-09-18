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
//import com.koopey.view.RoundImage;

public class Asset extends Base {

    //Objects
    public Advert advert = new Advert();
    //public User user = new User();
    //Arrays
    public Images images = new Images();
    public Location location = new Location();
    public Transactions transactions = new Transactions();
    public Tags tags = new Tags();
    //Strings
    public static final String ASSET_FILE_NAME = "Asset.dat";
    private static final String LOG_HEADER = "ASSET:";
    public String id = UUID.randomUUID().toString();
    public String buyerId = "";
    public String sellerId = "";
    public String hash = "";
    public String title = "";
    public String description = "";
    public String dimensiontUnit = "";
    public String weightUnit = "";
    public String currency = "eur";
    public String fileData = "";
    public String fileName = "";
    public String fileType = "";
    //Doubles
    public Double width = 0.0d; //cm
    public Double height = 0.0d; //cm
    public Double length = 0.0d; //cm
    public Double weight = 0.0d; //kg
    public Double value = 200d;

    //Longs
    public long fileSize = 0;
    public long manufactureDate = 0;
    public long createTimeStamp = System.currentTimeMillis();
    public long readTimeStamp = 0; //read only
    public long updateTimeStamp = 0; //read only
    public long deleteTimeStamp = 0; //read only
    //Ints
    public int distance = 0;
    public int quantity = 0;
    //Booleans
    public boolean available = true;

    public Asset() {
    }

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
        if (hasImage && !this.title.equals("") && this.value >= 0 && this.tags.size() >= 0) {
            return true;
        } else {
            return false;
        }
    }



    /*********
     * Helpers
     *********/

    public String getValue() {
        return Double.toString(value);
    }




    public boolean isEmpty() {
        return buyerId == null || sellerId == null  || buyerId.length() <= 0 || sellerId.length() <= 0 || super.isEmpty() ? true : false;
    }
}
