package com.example.tomermai.androidUtils.models;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import com.example.tomermai.androidUtils.R;

public class NotificationsFactory {

    private int mNotificationId;

    private NotificationsFactory(int notificationId) {
        mNotificationId = notificationId;
    }

    public int getNotificationId() {
        return mNotificationId;
    }

    private static class NotificationsHolder {
        private static final NotificationsFactory INSTANCE = new NotificationsFactory(0);
    }

    public static NotificationsFactory getNotificationsFactoryInstance() {
        return NotificationsHolder.INSTANCE;
    }

    public Notification getNotificationWithMinutesDelay(Context context, double delayMinutesValue) {
        mNotificationId++;
        return buildNotification(context, delayMinutesValue);
    }

    private Notification buildNotification(Context context, double delayMinutesValue) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My Spam Notification")
                .setContentText("Guess what? already passed: " + delayMinutesValue + " minutes");

        return builder.build();
    }

}
