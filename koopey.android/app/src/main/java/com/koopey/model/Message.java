package com.koopey.model;

import android.util.Log;

import com.google.gson.Gson;
import com.koopey.model.base.Base;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

/**
 * Created by Scott on 15/10/2016.
 */
public class Message extends Base  {


    public Users users = new Users();

    public String senderId = "";
    public String receiverId = "";

    public String language = "";
    public boolean archived = false;
    public boolean delivered = false;
    public boolean read = false;
    public boolean sent = false;
    public long createTimeStamp = System.currentTimeMillis();
    public long readTimeStamp = 0; //read only
    public long updateTimeStamp = 0; //read only
    public long deleteTimeStamp = 0; //read only

    public Message() {
    }

   /* @Override
    public int compare(Message o1, Message o2) {
        if (o1.hashCode() < o2.hashCode()) {
            return -1;
        } else if (o1.hashCode() > o2.hashCode()) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public int compareTo(Message o) {
        return compare(this, o);
    }*/

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }

    public boolean equals(Message message) {
        if (this.id.equals(message.id)){
            return true;
        } else {
            return false;
        }
    }

    public User getSender() {
        User user = new User();

        for (int i = 0; i < this.users.size(); i++) {
            user = this.users.get(i);
            if (user.type.equals("sender")) {
                return user;
            }
        }

        return user;
    }

    public Users getReceivers() {
        Users users = new Users();

        for (int i = 0; i < this.users.size(); i++) {
            User user = this.users.get(i);
            if (user.type.equals("receiver")) {
                users.add(user);
            }
        }

        return users;
    }

    public String getSummary() {
        if (this.description.length() <= 20) {
            return this.description + "...";
        } else {
            return this.description.substring(0, 20) + "...";
        }
    }

   /* protected boolean contains(String userId) {
        if (this.fromId.equals(userId) || this.toId.equals(userId)) {
            return true;
        } else {
            return false;
        }
    }*/



    public boolean isEmpty() {
        return receiverId == null || senderId == null  || receiverId.length() <= 0 ||senderId.length() <= 0 || super.isEmpty() ? true : false;
    }
}
