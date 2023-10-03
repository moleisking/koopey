package com.koopey.helper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateTimeHelper {

    public static Date StartOfToday(){
        Date today = new Date();
        today.setHours(0);
        today.setSeconds(0);
        return today;
    }

    public static String epochToString(long epoch, String timeZone) {
        Date date = new Date(epoch);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone(timeZone));
        return format.format(date);
    }
    public static Date epochToDate(long epoch) {
        return new Date(epoch);
    }

    public static long dateToEpoch(int year, int month, int day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH,month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime().getTime();
    }

}
