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

    @Builder.Default
    private Users users = new Users();
    private String senderId ;
    private String receiverId ;
    @Builder.Default
    private String language = "en";
    @Builder.Default
    private boolean archived = false;
    @Builder.Default
    private boolean delivered = false;
    @Builder.Default
    private boolean read = false;
    @Builder.Default
    private boolean sent = false;



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

  //  @Override
  //  public String toString() {
    //    return new Gson().toJson(this);
   // }

    /*public boolean equals(Message message) {
        if (this.id.equals(message.id)){
            return true;
        } else {
            return false;
        }
    }*/

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
