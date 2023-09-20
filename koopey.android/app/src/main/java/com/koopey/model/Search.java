package com.koopey.model;

import android.util.Log;

import org.json.JSONObject;
import com.google.gson.Gson;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by Scott on 12/06/2017.
 */
public class Search implements Serializable {

    public String id = UUID.randomUUID().toString();
    public String userId = "";
    public String productId = "";
    public String transactionId = "";
    public String type = "users";
    public String period = "hour";
    public String currency = "btc";
    public String name = "";
    public String alias = "";
    public String measure = "metric";
    public int min = 0;
    public int max = 5000;
    public int radius = 10;
    public Double latitude;
    public Double longitude;
    public Tags tags = new Tags();
    public long start = 0;
    public long end = 0;
    public long createTimeStamp = System.currentTimeMillis();

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }


}
