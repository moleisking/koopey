package com.koopey.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.util.Log;

import androidx.legacy.content.WakefulBroadcastReceiver;

import com.koopey.service.MessageIntentService;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Scott on 17/01/2017.
 */
public class MessageReceiver extends WakefulBroadcastReceiver {
    //private static final String LOG_HEADER = "MESSAGE:RECEIVER";
    private static final String ACTION_START_NOTIFICATION_SERVICE = "ACTION_START_NOTIFICATION_SERVICE";
    private static final String ACTION_DELETE_NOTIFICATION = "ACTION_DELETE_NOTIFICATION";
    private static final int NOTIFICATIONS_INTERVAL = 600;//in seconds, every 10 minutes

    public static void startAlarm(Context context) {
        Log.d(MessageReceiver.class.getName(),"start");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                getTriggerAt(new Date()),
                NOTIFICATIONS_INTERVAL * 1000,
                alarmIntent);
        //AlarmManager.INTERVAL_FIFTEEN_MINUTES
    }

    public static void stopAlarm(Context context) {
        Log.d(MessageReceiver.class.getName(),"stop");
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent alarmIntent = getStartPendingIntent(context);
        alarmManager.cancel(alarmIntent);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(MessageReceiver.class.getName(),"receive");
        String action = intent.getAction();
        Intent serviceIntent = null;
        if (ACTION_START_NOTIFICATION_SERVICE.equals(action)) {
            Log.i(getClass().getSimpleName(), "onReceive from alarm, starting notification service");
            serviceIntent = MessageIntentService.createIntentStartNotificationService(context);
        } else if (ACTION_DELETE_NOTIFICATION.equals(action)) {
            Log.i(getClass().getSimpleName(), "onReceive delete notification action, starting notification service to handle delete");
            serviceIntent = MessageIntentService.createIntentDeleteNotification(context);
        }

        if (serviceIntent != null) {
            startWakefulService(context, serviceIntent);
        }
    }

    private static long getTriggerAt(Date now) {
        Log.d(MessageReceiver.class.getName(),"trigger");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        //calendar.add(Calendar.HOUR, NOTIFICATIONS_INTERVAL_IN_HOURS);
        return calendar.getTimeInMillis();
    }

    private static PendingIntent getStartPendingIntent(Context context) {
        Log.d(MessageReceiver.class.getName(),"start");
        Intent intent = new Intent(context, MessageReceiver.class);
        intent.setAction(ACTION_START_NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }

    public static PendingIntent getDeleteIntent(Context context) {
        Log.d(MessageReceiver.class.getName(),"delete");
        Intent intent = new Intent(context, MessageReceiver.class);
        intent.setAction(ACTION_DELETE_NOTIFICATION);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        } else {
            return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }
}
