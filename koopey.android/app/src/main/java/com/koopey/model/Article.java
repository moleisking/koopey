/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.koopey.model;

import com.google.gson.Gson;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Created by Scott on 23/06/2018.
 */

public class Article implements Serializable, Comparator<Article>, Comparable<Article> {

    //Objects
    public Advert advert = new Advert();
    public User user ;
    //Arrays
    public Images images = new Images();
    public Location location ;

    public Tags tags = new Tags();
    //Strings
    public static final String ARTICLE_FILE_NAME = "Article.dat";
  ;
    public String id = UUID.randomUUID().toString();
    public String hash = "";
    public String title = "";
    public String content = "";
    //Longs
    public long createTimeStamp = System.currentTimeMillis();
    public long readTimeStamp = 0; //read only
    public long updateTimeStamp = 0; //read only
    public long deleteTimeStamp = 0; //read only
    //Ints
    public int distance = 0;
    public int quantity = 0;
    //Booleans
    public boolean available = true;

    public Article() {
    }

    @Override
    public int compare(Article o1, Article o2) {
        if (o1.hashCode() < o2.hashCode()) {
            return -1;
        } else if (o1.hashCode() > o2.hashCode()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int compareTo(Article o) {
        return compare(this, o);
    }

    @Override
    public String toString() {
        //JSONObject adds backslash in front of forward slashes causing corrupt images
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
        if (hasImage && !this.title.equals("") && !this.content.equals("") ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean equals(Article article) {
        if (article.id.equals(this.id)) {
            return true;
        } else {
            return false;
        }
    }



}
