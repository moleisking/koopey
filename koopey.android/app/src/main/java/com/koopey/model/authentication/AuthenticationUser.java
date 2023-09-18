package com.koopey.model.authentication;

import com.koopey.model.Image;
import com.koopey.model.Messages;
import com.koopey.model.Transactions;
import com.koopey.model.User;

import java.io.Serializable;

public class AuthenticationUser extends User implements Serializable {

    public static final String AUTH_USER_FILE_NAME = "authUser.dat";
    // private transient Context context;
    public String ip = "";
    public String device = "";
    public String type = "";
    public String token = "";

    public boolean notify = false;
    public boolean terms = false;
    public boolean cookies = false;
    public Transactions transactions = new Transactions();
    public Messages messages = new Messages();

    public AuthenticationUser() {

    }

   /* @Override
    public String toString() {
        //JSONObject adds backslash in front of forward slashes causing corrupt images
        return this.toJSONObject().toString().replaceAll("\\/", "/");
    }*/

  /*  public void parseJSON(String json) {
        try {
            if (json.length() >= 1) {
                if (json.substring(0, 10).replaceAll("\\s+", "").contains("user")) {
                    this.parseJSON(new JSONObject(json).getJSONObject("user"));//{user:{id:1}}
                } else if (!json.substring(0, 10).replaceAll("\\s+", "").contains("user")) {
                    this.parseJSON(new JSONObject(json));//{id:1}
                }
            }
            //this.parseJSON(new JSONObject(json));
        } catch (Exception ex) {
            Log.d(AuthUser.class.getName(), ex.getMessage());
        }
    }

    @Override
    public void parseJSON(JSONObject jsonObject) {

        try {
            //Boolean
            if (jsonObject.has("cookies")) {
                this.cookies = jsonObject.getBoolean("cookies");
            }
            if (jsonObject.has("terms")) {
                this.terms = jsonObject.getBoolean("terms");
            }
            if (jsonObject.has("notify")) {
                this.notify = jsonObject.getBoolean("notify");
            }
            //Strings
            if (jsonObject.has("device")) {
                this.token = jsonObject.getString("device");
            }
            if (jsonObject.has("ip")) {
                this.token = jsonObject.getString("ip");
            }
            if (jsonObject.has("token")) {
                this.token = jsonObject.getString("token");
            }
            if (jsonObject.has("type")) {
                this.type = jsonObject.getString("type");
            }
            //Arrays
            if (jsonObject.has("transactions")) {
                this.transactions.parseJSON(jsonObject.getJSONArray("transactions"));
            }
            if (jsonObject.has("messages")) {
                this.messages.parseJSON(jsonObject.getJSONArray("messages"));
            }
            super.parseJSON(jsonObject);
        } catch (Exception ex) {
            Log.d(AuthUser.class.getName(), ex.getMessage());
        }
    }*/

    public Image getAvatarImage() {
        Image img = new Image();
        img.uri = this.avatar;
        return img;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }





    public boolean isEmpty() {
        if (this.token.equals("")) {
            return true;
        } else {
            return false;
        }
    }

    public User getUser() {
        return (User) this;
    }

    public void syncronize(User user) {
        this.avatar = user.avatar;
        this.description = user.description;
        this.mobile = user.mobile;
        this.education = user.education;
        this.currency = user.currency;
        this.location = user.location;
    }



}
