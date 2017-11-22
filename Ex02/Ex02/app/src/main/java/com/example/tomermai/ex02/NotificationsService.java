package com.example.tomermai.ex02;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * helper methods.
 */
public class NotificationsService extends IntentService {
    private static final String ACTION_NOTIFICATION = "com.example.tomermai.ex02.action.NOTIFICATION_KEY";

    public static final String EXTRA_TIME_INTERVAL = "com.example.tomermai.ex02.extra.PARAM1";
    private static final long DEFAULT_INTERVAL = 300000;

    public NotificationsService() {
        super("NotificationsService");
    }

    public static void startActionNotifications(Context context, long timeInterval) {
        Intent intent = new Intent(context, NotificationsService.class);
        intent.setAction(ACTION_NOTIFICATION);
        intent.putExtra(EXTRA_TIME_INTERVAL, timeInterval);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Do nothing ("Miktzoan")
        if (intent == null) return;
        double delayMinutesValue;
        final String action = intent.getAction();

        if (ACTION_NOTIFICATION.equals(action)) {
            final long timeInterval = intent.getLongExtra(EXTRA_TIME_INTERVAL,DEFAULT_INTERVAL);
            delayMinutesValue = timeInterval / (60.0 * 1000);
            handleActionNotifications(timeInterval, delayMinutesValue);
        }
    }

    private void handleActionNotifications(long timeIntervalMilis, double delayMinutesValue) {
        Context context = this;
        Notification pushNotification = buildNotification(context, delayMinutesValue);
        PendingIntent alarmPendingIntent = getNotificationsPendingIntent(pushNotification);
        AlarmManager alarmMgr = (AlarmManager)getSystemService(Context.ALARM_SERVICE);

        // Repetitive use of notification every "timeInterval"
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + timeIntervalMilis,
                timeIntervalMilis, alarmPendingIntent);
    }

    private PendingIntent getNotificationsPendingIntent(Notification pushNotification) {
        Intent notificationIntent = new Intent(this, NotificationsReceiver.class);
        notificationIntent.putExtra(NotificationsReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationsReceiver.NOTIFICATION_KEY, pushNotification);

        return PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private Notification buildNotification(Context context, double delayMinutesValue) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My Spam Notification")
                .setContentText("Guess what? already passed: " + delayMinutesValue + " minutes");

        return builder.build();
    }
}
