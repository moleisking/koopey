package com.koopey.model;

import android.util.Log;
import com.google.gson.Gson;
import com.koopey.model.base.Base;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class Tag extends Base  {

    public String de;
    public String en ;
    public String es ;
    public String fr ;
    public String it;
    public String pt ;
    public String zh;
    public String type ;
    // public String text = "";



    public int compare(Tag o1, Tag o2) {
        return String.CASE_INSENSITIVE_ORDER.compare(o1.getId(), o2.getId());
    }

   /* @Override
    public int compareTo(Tag o) {
        return compare(this, o);
    }*/

    //why .replaceAll("\"", "'")
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public String getText(String language) {
        if (language.equals("de")) {
            return this.de;
        } else if (language.equals("en")) {
            return this.en;
        } else if (language.equals("es")) {
            return this.es;
        } else if (language.equals("fr")) {
            return this.fr;
        } else if (language.equals("it")) {
            return this.it;
        } else if (language.equals("pt")) {
            return this.pt;
        } else if (language.equals("zh")) {
            return this.zh;
        } else {
            return this.en;
        }
    }



}
