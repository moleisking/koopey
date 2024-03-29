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
public class Message extends Base  {

    public static final String MESSAGE_FILE_NAME = "message.dat";
    @Builder.Default
    private Users users = new Users();
    private String senderId ;
    private String receiverId ;
    @Builder.Default
    private String language = "en";

    public User getSender() {
       /* User user = new User();

        for (int i = 0; i < this.users.size(); i++) {
            user = this.users.get(i);
            if (user.type.equals("sender")) {
                return user;
            }
        }

        return user;*/
        return null;
    }

    public Users getReceivers() {
        Users users = new Users();

        for (int i = 0; i < this.users.size(); i++) {
            User user = this.users.get(i);
            if (user.getType().equals("receiver")) {
                users.add(user);
            }
        }

        return users;
    }

    public String getSummary() {
        if (this.getDescription().length() <= 20) {
            return this.getDescription() + "...";
        } else {
            return this.getSummary().substring(0, 20) + "...";
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
