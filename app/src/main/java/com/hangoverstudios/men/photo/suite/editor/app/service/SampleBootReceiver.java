package com.hangoverstudios.men.photo.suite.editor.app.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class SampleBootReceiver extends BroadcastReceiver {

    private AlarmManager alarmMgr, alarmMgrEvng;
    private PendingIntent alarmIntent, alarmIntentEvng;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent receiverIntent = new Intent(context, AlarmReceiver.class);
            alarmIntent = PendingIntent.getBroadcast(context, 0, receiverIntent, 0);

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 7);

            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntent);

            //Evening

            alarmMgrEvng = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            Intent receiverIntentEvng = new Intent(context, AlarmEvngReceiver.class);
            alarmIntentEvng = PendingIntent.getBroadcast(context, 0, receiverIntentEvng, 0);

            Calendar calendarEvng = Calendar.getInstance();
            calendarEvng.setTimeInMillis(System.currentTimeMillis());
            calendarEvng.set(Calendar.HOUR_OF_DAY, 19);

            alarmMgrEvng.setRepeating(AlarmManager.RTC_WAKEUP, calendarEvng.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY, alarmIntentEvng);

        }
    }
}