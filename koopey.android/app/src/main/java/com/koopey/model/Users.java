package com.koopey.model;

import com.koopey.model.base.BaseCollection;
import java.util.ArrayList;
import java.util.List;

public class Users extends BaseCollection<User> {

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
}

