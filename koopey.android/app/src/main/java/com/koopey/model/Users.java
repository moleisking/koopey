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

public class Users implements Serializable, Comparator<Users>, Comparable<Users> {

    private static final String LOG_HEADER = "USERS";
    public static final String USERS_FILE_NAME = "users.dat";
    public static final String SEARCH_RESULTS_FILE_NAME = "user_search_results.dat";
    private List<User> users;
    //private transient Context context;
    public String hash = "";

    public Users() {
        //this.context= context;
        users = new ArrayList<User>(0);
    }

    public Users(User[] user) {
        this.users = new ArrayList<User>(2);
        for (int i = 0; i < user.length; i++) {
            this.users.add(user[i]);
        }
    }

    public Users(Users users) {
        this.users = new ArrayList<User>();
        for (int i = 0; i < users.size(); i++) {
            this.users.add(users.get(i));
        }
    }

    @Override
    public int compare(Users o1, Users o2) {
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

    @Override
    public int compareTo(Users o) {
        return this.compare(this, o);
    }

    public void add(User user) {
        if (!this.contains(user)) {
            users.add(user);
        }
    }

    public void addBuyer(User user) {
        if (!this.contains(user)) {
            user.setType( "buyer");
            users.add(user);
        }
    }

    public void addSeller(User user) {
        if (!this.contains(user)) {
            user.setType("seller");
            users.add(user);
        }
    }

    public void add(Users users) {
        for (int i = 0; i < users.size(); i++) {
            this.add(users.get(i)); //Checks for duplicates
        }
    }

    public int count(List<User> users, String type) {
        if (users != null && (users.size() > 0)) {
            int counter = 0;
            for (int i = 0; i <= users.size(); i++) {
                if (users.get(i) != null && users.get(i).getType() == type) {
                    counter++;
                }
            }
            return counter;
        } else {
            return 0;
        }
    }

    public int countBuyer(List<User> users) {
        return count( users, "buyer");
    }

    public int countSeller(List<User> users) {
        return count( users, "seller");
    }

    public boolean contains(User user) {
        boolean result = false;
        for (int i = 0; i < this.users.size(); i++) {
            User cursor = this.users.get(i);
            if (user.equals(cursor) ) {
                result = true;
                break;
            }
        }
        return result;
    }

    public boolean contains(Users users) {
        boolean result = false;
        int counter = 0;
        for (int i = 0; i < this.users.size(); i++) {
            for (int j = 0; j < users.size(); j++) {
                if (this.users.get(i).getId().equals(users.get(j).getId())
                        || this.users.get(i).getName().equals(users.get(j).getName())
                        || this.users.get(i).getEmail().equals(users.get(j).getEmail())) {
                    counter++;
                    if (counter == users.size()) {
                        result = true;
                        break;
                    }
                }
            }
        }

        return result;
    }

    public static boolean equals(Users usersA, Users usersB) {
        if (usersA == null || usersB == null) {
            return false;
        } else if (usersA.size() != usersB.size()) {
            return false;
        } else if (usersA.size() == usersB.size()) {
            int counter = 0;
            for (int i = 0; i < usersA.size(); i++) {
                User userA = usersA.get(i);
                for (int j = 0; j < usersB.size(); j++) {
                    User userB = usersB.get(j);
                    if (userA.getId().equals(userB.getId()) ) {
                       counter++;
                       break;
                    }
                }
            }
            //Check counter results
            if (counter == usersA.size() && counter == usersB.size()) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public User get(int index) {
        return users.get(index);
    }

    public User get(String id) {
        User result = null;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId().equals(id)) {
                result = users.get(i);
                break;
            }
        }
        return result;
    }

    public User get(User user) {
        User result = null;

        for (int i = 0; i < this.users.size(); i++) {
            if (this.users.get(i).getId().equals(user.getId())
                    || this.users.get(i).getName().equals(user.getName())
                    || this.users.get(i).getEmail().equals(user.getEmail())) {
                result = this.users.get(i);
                break;
            }
        }

        return result;
    }

    public User get(String name, String email) {
        User result = null;

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getName().equals(name) || users.get(i).getEmail().equals(email)) {
                result = users.get(i);
                break;
            }
        }

        return result;
    }

    public List<User> getList() {
        return users;
    }

    public boolean isEmpty() {
        return this.size() == 0 ? true : false;
    }


    public void remove(User u) {
        users.remove(u);
    }

    public void set(User user){
        if (!this.contains(user)){
            for(int i = 0; i < this.users.size(); i++ ){
                User currentUser = this.users.get(i);
                if (currentUser.getId().equals(currentUser.getId())){
                    this.users.set(i,user);
                }
            }
        }
    }

    protected void setList(List<User> users) {
        this.users = users;
    }

    public int size() {
        return users.size();
    }

    public void sort() {
        Collections.sort(users);
    }


}

