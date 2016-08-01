package com.balinasoft.minimarket.networking.Services;

import android.app.NotificationManager;
import android.content.Context;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;


public class MyGCMListenerService extends GcmListenerService {

    private static final String TAG = "MyGCMListenerService";
    public static final String TYPE_MESSAGE = "type";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String type = data.getString(TYPE_MESSAGE);
        Log.d(TAG, "From: " + from);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);

    }

    private void sendNotification(NotificationCompat.Builder builder) {

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setAutoCancel(true)
                .setSound(defaultSoundUri);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0, builder.build());
    }
}