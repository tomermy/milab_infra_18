package com.example.tomermai.ex02;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationsReceiver extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "tomer.notification_id";
    public static String NOTIFICATION_KEY = "tomer.notification";

    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getIntExtra(NOTIFICATION_ID, 1);
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = intent.getParcelableExtra(NOTIFICATION_KEY);
        notificationManager.notify(notificationId, notification);
    }
}