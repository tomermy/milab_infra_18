package com.example.tomermai.androidUtils.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.tomermai.androidUtils.models.NotificationsFactory;

public class NotificationsReceiver extends BroadcastReceiver {
    public static String NOTIFICATION_DELAY = "tomer.notification";

    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationsFactory pushNotificationsFactory = NotificationsFactory.getNotificationsFactoryInstance();
        double delayedMinutes = intent.getDoubleExtra(NOTIFICATION_DELAY, 5);
        Notification notification =
                pushNotificationsFactory.getNotificationWithMinutesDelay(context, delayedMinutes);
        int notificationId = pushNotificationsFactory.getNotificationId();

        notificationManager.notify(notificationId, notification);
    }
}