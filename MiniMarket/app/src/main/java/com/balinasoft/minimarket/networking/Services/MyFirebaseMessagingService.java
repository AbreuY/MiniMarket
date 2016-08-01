package com.balinasoft.minimarket.networking.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.Activities.BasketItemsActivity;
import com.balinasoft.minimarket.Ui.Activities.BuerActivity;
import com.balinasoft.minimarket.Ui.Activities.CourierActivity;
import com.balinasoft.minimarket.Ui.Activities.DispatcherActivity;
import com.balinasoft.minimarket.Ui.Activities.MainActivity;
import com.balinasoft.minimarket.Ui.Activities.ManagerActivity;
import com.balinasoft.minimarket.Utils.AuthManager;
import com.balinasoft.minimarket.models.modelUsers.Buer;
import com.balinasoft.minimarket.models.modelUsers.Courier;
import com.balinasoft.minimarket.models.modelUsers.Dispatcher;
import com.balinasoft.minimarket.models.modelUsers.Manager;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    public static final String ORDER = "order_id";
    public static final String RECORD = "record_id";
    public static final String SHOP = "shop_id";
    public static final String ITEM = "item_id";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(final RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }
        new AuthManager(this).setExtractListener(new AuthManager.ExtractListener() {
            @Override
            public void onEmpty() {
                Intent intent = new Intent(MyFirebaseMessagingService.this, MainActivity.class);
                sendNotification(remoteMessage.getData().get("message"),getIntent(remoteMessage,intent));
            }

            @Override
            public void onManager(Manager manager) {
                Intent intent = new Intent(MyFirebaseMessagingService.this, ManagerActivity.class);
                sendNotification(remoteMessage.getData().get("message"),getIntent(remoteMessage,intent));
            }

            @Override
            public void onCourier(Courier courier) {
                Intent intent = new Intent(MyFirebaseMessagingService.this, CourierActivity.class);
                sendNotification(remoteMessage.getData().get("message"),getIntent(remoteMessage,intent));
            }

            @Override
            public void onBuer(Buer buer) {
                Intent intent = new Intent(MyFirebaseMessagingService.this, BuerActivity.class);
                sendNotification(remoteMessage.getData().get("message"),getIntent(remoteMessage,intent));
            }

            @Override
            public void onDispatcher(Dispatcher dispatcher) {
                Intent intent = new Intent(MyFirebaseMessagingService.this, DispatcherActivity.class);
                sendNotification(remoteMessage.getData().get("message"),getIntent(remoteMessage,intent));
            }
        }).extract();
        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendNotification(String messageBody, Intent intent) {

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_media_pause)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    public Intent getIntent(RemoteMessage remoteMessage, Intent intent) {
        for (String s : remoteMessage.getData().keySet()) {
            switch (s) {
                case ITEM:
                    return intent.putExtra(ITEM, remoteMessage.getData().get(ITEM));
                case ORDER:

                    return intent.putExtra(ORDER, remoteMessage.getData().get(ORDER));
                case RECORD:
                    return intent.putExtra(RECORD, remoteMessage.getData().get(RECORD));
                case SHOP:
                    intent=new Intent(this, BasketItemsActivity.class);
                    return intent.putExtra(SHOP, remoteMessage.getData().get(SHOP));
            }
        }
        return intent;
    }
}