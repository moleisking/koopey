package com.koopey.model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class Assets implements Serializable , Comparator<Assets>, Comparable<Assets> {

    public static final String MY_ASSETS_FILE_NAME = "my_assets.dat";
    public static final String ASSET_SEARCH_RESULTS_FILE_NAME = "asset_search_results.dat";
    public static final String ASSET_WATCH_LIST_FILE_NAME = "asset_watch_list.dat";
    protected List<Asset> assets;
    public String fileType;

    public Assets()
    {
        this.assets = new  ArrayList<Asset>(0);
    }

    public Assets(String fileType)    {
        this.assets = new  ArrayList<Asset>(0);
        this.fileType = fileType;
    }

    public Assets( Asset[] asset)    {
        this.assets = new  ArrayList<Asset>(2);
        for (int i =0; i < asset.length;i++) {
            this.assets.add(asset[i]);
        }
    }

    public Assets ( Assets assets)    {
        this.assets = new  ArrayList<Asset>();
        for (int i =0; i < assets.size();i++)        {
            this.assets.add(assets.get(i));
        }
    }

    @Override
    public int compare(Assets o1, Assets o2) {
        //-1 not the same, 0 is same, 1 > is same but larger
        int result = -1;
        if (o1.size() < o2.size()) {
            result = -1;
        } else if (o1.size() > o2.size()) {
            result = 1;
        } else {
            o1.sort();
            o2.sort();
            for (int i = 0; i < o1.size(); i++) {
                if (!o1.contains(o2.get(i))) {
                    result = -1;
                    break;
                } else if (i == o2.size() - 1) {
                    result = 0;
                    break;
                }
            }
        }
        return result;
    }

    public int compareTo(Assets o) {
        return this.compare(this, o);
    }

    /*********  Create *********/

    public void add(Asset p)
    {
        try
        {
            assets.add(p);
        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }

    }

    /*********  Read *********/

    public Asset get(int index)
    {
        return assets.get(index);
    }

    public Asset get(Asset asset)    {
        Asset result = null;
        for (int i =0; i < this.assets.size();i++)        {
            if ( this.assets.get(i).getId().equals(asset.getId()))            {
                result = this.assets.get(i);
                break;
            }
        }
        return result;
    }

    public Asset get(String id)    {
        Asset result = null;
        for (int i =0; i < assets.size();i++)        {
            if (assets.get(i).getId().equals(id))            {
                result = assets.get(i);
                break;
            }
        }
        return result;
    }

    public List<Asset> get() {
        return assets;
    }

    /*********  Checks *********/

    public boolean contains(Asset asset)    {
        boolean result = false;
        for (int i =0; i < this.assets.size();i++)        {
            if (this.assets.get(i).getId().equals(asset.getId()))            {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean contains(Assets assets)
    {
        boolean result = false;
        int counter = 0;
        for (int i =0; i < this.assets.size();i++)        {
            if(this.contains(this.assets.get(i)))            {
                counter++;
                if (counter == assets.size())                {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    public boolean isEmpty()
    {
        return this.size() == 0 ? true : false;
    }

    /*********  Delete *********/

    public void remove(Asset p)
    {
        assets.remove(p);
    }

    public int size()
    {
        return assets.size();
    }

    public List<Asset> getProductList()
    {
        return assets;
    }

    public void sort() {
        Collections.sort(assets);
    }

    protected void setAssetList(List<Asset> assets)
    {
        this.assets = assets;
    }

}