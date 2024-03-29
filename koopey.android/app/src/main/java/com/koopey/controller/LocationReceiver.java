package com.koopey.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;

import com.koopey.service.MessageService;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Scott on 18/01/2017.
 */
public class LocationReceiver extends WakefulBroadcastReceiver {
    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";
    private static final int NOTIFICATIONS_INTERVAL = 120;//in seconds

    public static void startAlarm(Context context) {
        Log.d(LocationReceiver.class.getName(),"start");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                getTriggerAt(new Date()),
                NOTIFICATIONS_INTERVAL * 1000,
                alarmIntent);
        //AlarmManager.INTERVAL_FIFTEEN_MINUTES
    }

    public static void stopAlarm(Context context) {
        Log.d(LocationReceiver.class.getName(),"stop");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        alarmManager.cancel(alarmIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LocationReceiver.class.getName(),"receive");
        String action = intent.getAction();
        Intent serviceIntent = null;
        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {
            Log.i(getClass().getSimpleName(), "onReceive from alarm, starting notification service");
            serviceIntent = MessageService.createIntentStartNotificationService(context);
        } else if (ACTION_DELETE_NOTIFICATION.equals(action)) {
            Log.i(getClass().getSimpleName(), "onReceive delete notification action, starting notification service to handle delete");
            serviceIntent = MessageService.createIntentDeleteNotification(context);
        }

        if (serviceIntent != null) {
            startWakefulService(context, serviceIntent);
        }
    }

    private static long getTriggerAt(Date now) {
        Log.d(LocationReceiver.class.getName(),"trigger");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        //calendar.add(Calendar.HOUR, NOTIFICATIONS_INTERVAL_IN_HOURS);
        return calendar.getTimeInMillis();
    }

    private static PendingIntent getStartPendingIntent(Context context) {
        Log.d(LocationReceiver.class.getName(),"start");
        Intent intent = new Intent(context, MessageReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    public static PendingIntent getDeleteIntent(Context context) {
        Log.d(LocationReceiver.class.getName(),"delete");
        Intent intent = new Intent(context, MessageReceiver.class);
        intent.setAction(ACTION_DELETE_NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }
}
