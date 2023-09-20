package com.koopey.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Scott on 19/06/2018.
 */

public class Events  implements Serializable {
    private static final String LOG_HEADER = "EVENTS";
    public static final String EVENTS_FILE_NAME = "events.dat";
    private List<Event> events;

    public Events() {
        events = new ArrayList<Event>(0);
    }

    @Override
    public String toString() {
        JSONArray json = new JSONArray();
        if (this.events.size() > 0) {
            for (int i = 0; i < events.size(); i++) {
                json.put(events.get(i).toString());
            }
        }
        return json.toString();
    }

     /* Array */

    public void add(Event event) {
        if (event != null && !this.contains(event)) {
            this.events.add(event);
        }
    }

    public void add(Events events) {
        for (int i = 0; i < events.size(); i++) {
            this.add(events.get(i)); //Checks for duplicates
        }
    }

    protected boolean contains(Event event) {
        boolean result = false;
        for (int i = 0; i < this.events.size(); i++) {
            Event cursor = this.events.get(i);
            if (event.equals(cursor) ) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void set(Event event) {
        try {
            for (int i = 0; i < events.size(); i++) {
                if(events.get(i).getId().equals(event.getId())){
                    events.set(i,event);
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

    }

    public Event get(int index) {
        return events.get(index);
    }

    public Events getEventsOfDate(Date start) {
        Events todayTransactions = new Events();
        for (int i = 0; i < this.events.size(); i++) {
            Date transactionDate = this.events.get(i).getStartTimeStampAsDate();
            if (transactionDate.getYear() == start.getYear()
                    && transactionDate.getMonth() == start.getMonth()
                    && transactionDate.getDay() == start.getDay()) {
                todayTransactions.add(this.events.get(i));
            }
        }
        return todayTransactions;
    }

    public Events getTransactionsBetweenDates(Date start, Date end) {
        Events todayTransactions = new Events();
        for (int i = 0; i < this.events.size(); i++) {
            Date transactionDate = this.events.get(i).getStartTimeStampAsDate();
            if (transactionDate.getYear() >= start.getYear()
                    && transactionDate.getMonth() >= start.getMonth()
                    && transactionDate.getDay() >= start.getDay()
                    && transactionDate.getYear() <= end.getYear()
                    && transactionDate.getMonth() <= end.getMonth()
                    && transactionDate.getDay() <= end.getDay()) {
                todayTransactions.add(this.events.get(i));
            }
        }
        return todayTransactions;
    }

    public Events getTransactionsFromDate(Date start) {
        Events todayTransactions = new Events();
        for (int i = 0; i < this.events.size(); i++) {
            Date transactionDate = this.events.get(i).getStartTimeStampAsDate();
            if (transactionDate.getYear() >= start.getYear()
                    && transactionDate.getMonth() >= start.getMonth()
                    && transactionDate.getDay() >= start.getDay()) {
                todayTransactions.add(this.events.get(i));
            }
        }
        return todayTransactions;
    }

    public List<Event> getList() {
        return events;
    }



    public void remove(Event event) {
        events.remove(event);
    }

    protected void setList(List<Event> events) {
        this.events = events;
    }

    public int size() {
        return events.size();
    }

    public boolean isEmpty() {
        if (this.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

}
