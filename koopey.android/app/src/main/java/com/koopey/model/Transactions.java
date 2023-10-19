package com.koopey.model;

import com.koopey.model.base.BaseCollection;
import java.util.Date;

public class Transactions extends BaseCollection<Transaction> {

    public static final String TRANSACTIONS_FILE_NAME = "transactions.dat";


    public Transactions getTransactionsOfDate(Date start) {
        Transactions todayTransactions = new Transactions();
        for (int i = 0; i < this.size(); i++) {
            Date transactionDate = this.get(i).getStartAsDate();
            if (transactionDate.getYear() == start.getYear()
                    && transactionDate.getMonth() == start.getMonth()
                    && transactionDate.getDay() == start.getDay()) {
                todayTransactions.add(this.get(i));
            }
        }
        return todayTransactions;
    }

    public Transactions getTransactionsBetweenDates(Date start, Date end) {
        Transactions todayTransactions = new Transactions();
        for (int i = 0; i < this.size(); i++) {
            Date transactionDate = this.get(i).getStartAsDate();
            if (transactionDate.getYear() >= start.getYear()
                    && transactionDate.getMonth() >= start.getMonth()
                    && transactionDate.getDay() >= start.getDay()
                    && transactionDate.getYear() <= end.getYear()
                    && transactionDate.getMonth() <= end.getMonth()
                    && transactionDate.getDay() <= end.getDay()) {
                todayTransactions.add(this.get(i));
            }
        }
        return todayTransactions;
    }

    public Transactions getTransactionsFromDate(Date start) {
        Transactions todayTransactions = new Transactions();
        for (int i = 0; i < this.size(); i++) {
            Date transactionDate = this.get(i).getStartAsDate();
            if (transactionDate.getYear() >= start.getYear()
                    && transactionDate.getMonth() >= start.getMonth()
                    && transactionDate.getDay() >= start.getDay()) {
                todayTransactions.add(this.get(i));
            }
        }
        return todayTransactions;
    }

    public Double getSum(String currency){
        Double sum = 0.0d;
        for (int i = 0; i < this.size(); i++) {
         if(this.get(i).getCurrency().equals(currency)){
             sum += this.get(i).getTotal();
         }
        }
        return sum;
    }
}
