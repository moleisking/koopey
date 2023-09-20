package com.koopey.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Scott on 18/01/2017.
 */
public class Transactions implements Serializable {

    public static final String TRANSACTIONS_FILE_NAME = "transactions.dat";
    private List<Transaction> transactions;

    public Transactions() {
        transactions = new ArrayList<Transaction>(0);
    }

    @Override
    public String toString() {
        JSONArray json = new JSONArray();
        if (this.transactions.size() > 0) {
            for (int i = 0; i < transactions.size(); i++) {
                json.put(transactions.get(i).toString());
            }
        }
        return json.toString();
    }

     /* Array */

    public void add(Transaction transaction) {
            if (transaction != null && !this.contains(transaction)) {
                this.transactions.add(transaction);
            }
    }

    public void add(Transactions transactions) {
        for (int i = 0; i < transactions.size(); i++) {
            this.add(transactions.get(i)); //Checks for duplicates
        }
    }

    protected boolean contains(Transaction message) {
        boolean result = false;
        for (int i = 0; i < this.transactions.size(); i++) {
            Transaction cursor = this.transactions.get(i);
            if (message.equals(cursor) ) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void set(Transaction transaction) {
        try {
            for (int i = 0; i < transactions.size(); i++) {
                if(transactions.get(i).getId().equals(transaction.getId())){
                    Transaction temp = transactions.get(i);
                    temp.state = transaction.state;
                    temp.endTimeStamp = transaction.endTimeStamp;
                    transactions.set(i,temp);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public Transaction get(int index) {
        return transactions.get(index);
    }

    public Transactions getTransactionsOfDate(Date start) {
        Transactions todayTransactions = new Transactions();
        for (int i = 0; i < this.transactions.size(); i++) {
            Date transactionDate = this.transactions.get(i).getStartTimeStampAsDate();
            if (transactionDate.getYear() == start.getYear()
                    && transactionDate.getMonth() == start.getMonth()
                    && transactionDate.getDay() == start.getDay()) {
                todayTransactions.add(this.transactions.get(i));
            }
        }
        return todayTransactions;
    }

    public Transactions getTransactionsBetweenDates(Date start, Date end) {
        Transactions todayTransactions = new Transactions();
        for (int i = 0; i < this.transactions.size(); i++) {
            Date transactionDate = this.transactions.get(i).getStartTimeStampAsDate();
            if (transactionDate.getYear() >= start.getYear()
                    && transactionDate.getMonth() >= start.getMonth()
                    && transactionDate.getDay() >= start.getDay()
                    && transactionDate.getYear() <= end.getYear()
                    && transactionDate.getMonth() <= end.getMonth()
                    && transactionDate.getDay() <= end.getDay()) {
                todayTransactions.add(this.transactions.get(i));
            }
        }
        return todayTransactions;
    }

    public Transactions getTransactionsFromDate(Date start) {
        Transactions todayTransactions = new Transactions();
        for (int i = 0; i < this.transactions.size(); i++) {
            Date transactionDate = this.transactions.get(i).getStartTimeStampAsDate();
            if (transactionDate.getYear() >= start.getYear()
                    && transactionDate.getMonth() >= start.getMonth()
                    && transactionDate.getDay() >= start.getDay()) {
                todayTransactions.add(this.transactions.get(i));
            }
        }
        return todayTransactions;
    }

    public List<Transaction> getList() {
        return transactions;
    }



    public void remove(User u) {
        transactions.remove(u);
    }

    protected void setList(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public int size() {
        return transactions.size();
    }

    public boolean isEmpty() {
        if (this.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public Double getSum(String currency){
        Double sum = 0.0d;
        for (int i = 0; i < this.transactions.size(); i++) {
         if(this.transactions.get(i).currency.equals(currency)){
             sum += this.transactions.get(i).totalValue;
         }
        }
        return sum;
    }
}
