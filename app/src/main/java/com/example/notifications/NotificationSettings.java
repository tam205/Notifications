package com.example.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationSettings {

    /**
     * Updates the vibration setting for a specific notification channel.
     *
     * @param context      The application context.
     * @param channelId    The ID of the notification channel.
     * @param enableVibration True to enable vibration, false to disable it.
     */
    public static void updateChannelVibration(Context context, String channelId, boolean enableVibration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            NotificationChannel channel = notificationManager.getNotificationChannel(channelId);
            if (channel != null) {
                channel.enableVibration(enableVibration);
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}