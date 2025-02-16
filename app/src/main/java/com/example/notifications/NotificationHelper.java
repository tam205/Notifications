package com.example.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

public class NotificationHelper {

    public static final String CHANNEL_HIGH_PRIORITY = "high_priority_channel";
    public static final String CHANNEL_GENERAL = "general_channel";

    /**
     * Creates notification channels for high-priority and general tasks.
     */
    public static void createNotificationChannels(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create High Priority Channel
            NotificationChannel highPriorityChannel = new NotificationChannel(
                    CHANNEL_HIGH_PRIORITY,
                    "High Priority Tasks",
                    NotificationManager.IMPORTANCE_HIGH
            );
            highPriorityChannel.setDescription("Critical reminders with sound and vibration");
            highPriorityChannel.enableVibration(true); // Enable vibration
            highPriorityChannel.setSound(null, null); // You can set a custom sound here

            // Create General Channel
            NotificationChannel generalChannel = new NotificationChannel(
                    CHANNEL_GENERAL,
                    "General Tasks",
                    NotificationManager.IMPORTANCE_LOW
            );
            generalChannel.setDescription("Regular notifications without sound or vibration");
            generalChannel.enableVibration(false); // Disable vibration

            // Register the channels with the system
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(highPriorityChannel);
                notificationManager.createNotificationChannel(generalChannel);
            }
        }
    }
}
