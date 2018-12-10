package com.example.arpan.ezpay;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;

public class NotificationHelper extends ContextWrapper {
    private static final String Test_CHANNEL_ID = "com.example.eacaballeroespinosa.androidnotificationchannel.Test";
    private static final String Test_CHANNEL_NAME = "Test Channel";
    private NotificationManager manager;

    public NotificationHelper(Context base) {
        super(base);
        createChannels();
    }

    private void createChannels() {
        NotificationChannel testChannel = new NotificationChannel(Test_CHANNEL_ID, Test_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        testChannel.enableLights(true);
        testChannel.enableVibration(true);
        testChannel.setLightColor(Color.GREEN);
        testChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(testChannel);

    }

    public NotificationManager getManager(){
        if (manager == null)
            manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        return manager;
    }

    public Notification.Builder getTestChannelNotification(String title, String body) {
        return new Notification.Builder(getApplicationContext(), Test_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true);
    }

}
