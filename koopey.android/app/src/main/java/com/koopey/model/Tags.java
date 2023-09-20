package com.koopey.model;

import android.util.Base64;
import android.util.Log;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Scott on 03/10/2016.
 */
public class Tags implements Serializable, Comparator<Tags>, Comparable<Tags> {

    public static final String TAGS_FILE_NAME = "tags.dat";
    //private transient Context mContext;
    private List<Tag> tags;

    public Tags() {
        tags = new ArrayList();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    @Override
    public int compare(Tags o1, Tags o2) {
        //-1 not the same, 0 is same, 1 > is same but larger
        int result = -1;
        if (o1.size() < o2.size()) {
            result = -1;
        } else if (o1.size() > o2.size()) {
            result = 1;
        } else {
            //Sort both lists before compare
            o1.sort();
            o2.sort();
            //Check each tag in tags
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

    protected Tag get(int i) {
        return this.tags.get(i);
    }

    public int size() {
        return this.tags.size();
    }

    public void add(Tag t) {
        this.tags.add(t);
    }

    public List<Tag> getList() {
        return this.tags;
    }

    public ArrayList<Tag> getArrayList() {
        return (ArrayList) this.tags;//new ArrayList<Tag>( this.tags.toArray());
    }

    public void setTagList(List<Tag> arr) {
        this.tags = arr;
    }

    public boolean isEmpty() {
        return this.size() == 0 ? true : false;
    }

    public void sort() {
        Collections.sort(tags);
    }

    public int compareTo(Tags o) {
        return this.compare(this, o);
    }

    public boolean contains(Tag item) {
        return true;
      /*  Tag result = this.findTag(item.id);
        if (!result.id.equals("")) {
            return true;
        } else {
            return false;
        }*/
    }

   /* public Tag findTag(String id) {
        Tag result;
        for (int i = 0; i < this.tags.size(); i++) {
            if (tags.get(i).id.equals(id)) {
                result = tags.get(i);
                break;
            }
        }
        return result;
    }*/

}