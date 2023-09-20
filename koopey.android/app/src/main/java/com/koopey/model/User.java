package com.koopey.model;
/*
* NOTE: 1) Bitmap class is non serializable amd therefore not used.*
* */

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.koopey.model.base.Base;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class User extends Base /*implements Serializable, Comparator<User>, Comparable<User>*/ {
    //Constants
    public static final String USER_FILE_NAME = "user.dat";
    //Booleans
    @Builder.Default
    public boolean available = false;
    @Builder.Default
    public boolean authenticated = false;
    @Builder.Default
    public boolean track = false;
    //Integers
    @Builder.Default
    public int distance = 0;
    @Builder.Default
    public int score = 0;

    public Date birthday;
    @Builder.Default
    public long createTimeStamp = System.currentTimeMillis();
    @Builder.Default
    public long readTimeStamp = 0; //read only
    @Builder.Default
    public long updateTimeStamp = 0; //read only
    @Builder.Default
    public long deleteTimeStamp = 0; //read only
    //Strings

    public String username;
    public String avatar;
    public String education;

    public String mobile;
    public String email;

    @Builder.Default
    public String currency = "eur";
    @Builder.Default
    public String language = "en";
    @Builder.Default
    public String measure = "metric";
    @Builder.Default
    public String player = "grey";
    //Objects
    public Location location ;
   // public Reviews reviews = new Reviews();
   // public Scores scores = new Scores();
   @Builder.Default
    public Wallets wallets = new Wallets();
    //private transient Context context;



    public void setAlias(String alias){
        this.username = alias;
    }

    public String getAlias(){
        return username;
    }

  /*  @Override
    public int compare(User o1, User o2) {
        if (o1.hashCode() < o2.hashCode()) {
            return -1;
        } else if (o1.hashCode() > o2.hashCode()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int compareTo(User o) {
        return compare(this, o);
    }*/


    public String getBirthdayAsString() {
        return new SimpleDateFormat("yyyy/MM/dd").format(birthday);
    }

    public User getUserBasicWithAvatar() {



       /* User. userBasic ;
        userBasic.id = this.id;
        userBasic.type = "basic";
        userBasic.setAlias(this.getAlias());
        userBasic.name = this.name;
        userBasic.score = this.score;
        userBasic.player = this.player;
        userBasic.avatar = this.avatar;
        userBasic.currency = this.currency;
        userBasic.language = this.language;
       // userBasic.reviews = this.reviews;
        userBasic.wallets = this.wallets;
        // userBasic.locations = this.locations;
        return userBasic;*/

        return null;
    }

   /* public boolean equals(User user) {
        if (user != null && user.equals(this) && user.username.equals(this.username)) {
            return true;
        } else {
            return false;
        }
    }*/




    public boolean isEmpty() {
        return username == null  || email == null || username.length() <= 0  || email.length() <= 0 || super.isEmpty() ? true : false;
    }
}
