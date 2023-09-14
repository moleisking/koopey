package com.koopey.model;

import org.json.JSONArray;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Classifications implements Serializable {

    public static final String CLASSIFICATIONS_FILE_NAME = "classifications.dat";

    private List<Transaction> classifications;

    public Classifications() {
        classifications = new ArrayList<Transaction>(0);
    }

    @Override
    public String toString() {
        JSONArray json = new JSONArray();
        if (this.classifications.size() > 0) {
            for (int i = 0; i < classifications.size(); i++) {
                json.put(classifications.get(i).toString());
            }
        }
        return json.toString();
    }

    /* Array */

    public void add(Transaction transaction) {
        if (transaction != null && !this.contains(transaction)) {
            this.classifications.add(transaction);
        }
    }

    public void add(Classifications classifications) {
        for (int i = 0; i < classifications.size(); i++) {
            this.add(classifications.get(i)); //Checks for duplicates
        }
    }

    protected boolean contains(Transaction message) {
        boolean result = false;
        for (int i = 0; i < this.classifications.size(); i++) {
            Transaction cursor = this.classifications.get(i);
            if (message.equals(cursor) ) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void set(Transaction transaction) {
        try {
            for (int i = 0; i < classifications.size(); i++) {
                if(classifications.get(i).id.equals(transaction.id)){
                    Transaction temp = classifications.get(i);
                    temp.state = transaction.state;
                    temp.endTimeStamp = transaction.endTimeStamp;
                    classifications.set(i,temp);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public Transaction get(int index) {
        return classifications.get(index);
    }

    public List<Transaction> getList() {
        return classifications;
    }



    public void remove(User u) {
        classifications.remove(u);
    }

    protected void setList(List<Transaction> classifications) {
        this.classifications = classifications;
    }

    public int size() {
        return classifications.size();
    }

    public boolean isEmpty() {
        if (this.size() == 0) {
            return true;
        } else {
            return false;
        }
    }
}
