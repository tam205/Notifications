package com.example.notifications;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MainActivity extends AppCompatActivity {

    private static final int NOTIFICATION_ID = 1;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Ensure Notification Channels are created
        NotificationHelper.createNotificationChannels(this);

        // Request Notification Permission for Android 13+
        requestNotificationPermission();

        // Button to trigger notifications
        Button notifyButton = findViewById(R.id.notify_button);
        notifyButton.setOnClickListener(v -> {
            boolean isCritical = false; // Example: User selects task type (critical or general)
            sendNotification(isCritical);
        });
    }



    @SuppressLint("MissingPermission")
    private void sendNotification(boolean isCritical) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // Prepare Intent to open TaskDetailsActivity
        Intent intent = new Intent(this, TaskDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Build Notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, isCritical ? NotificationHelper.CHANNEL_HIGH_PRIORITY : NotificationHelper.CHANNEL_GENERAL)
                .setSmallIcon(R.drawable.ic_notification) // Add a notification icon in res/drawable
                .setContentTitle("Task Reminder")
                .setContentText("Don't forget to complete your task!")
                .setPriority(isCritical ? NotificationCompat.PRIORITY_HIGH : NotificationCompat.PRIORITY_LOW)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        // Add Sound and Vibration for Critical Notifications
        if (isCritical) {
            builder.setDefaults(NotificationCompat.DEFAULT_SOUND | NotificationCompat.DEFAULT_VIBRATE);
        }

        // Show Notification
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void requestNotificationPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 1);
            }
        }
    }
}