package com.hangoverstudios.men.photo.suite.editor.app.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.hangoverstudios.men.photo.suite.editor.app.R;
import com.hangoverstudios.men.photo.suite.editor.app.activities.SplashScreen;

import java.util.Random;

public class AlarmEvngReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        RemoteViews remoteViewCollapse = new RemoteViews(context.getPackageName(), R.layout.notification_collapse);

        RemoteViews remoteViewExpand = new RemoteViews(context.getPackageName(), R.layout.notification_expand);

        Intent launchIntent = new Intent(context, SplashScreen.class);
        Random random = new Random();
        int importance = NotificationManager.IMPORTANCE_HIGH;
        String channelId = "channel-49";
        String channelName = "Men Suit Editor frames";
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.DecoratedCustomViewStyle())
                .setCustomContentView(remoteViewCollapse)
                .setCustomBigContentView(remoteViewExpand);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(launchIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }
        mBuilder.setContentIntent(resultPendingIntent);
        notificationManager.notify(1, mBuilder.build());
    }

}