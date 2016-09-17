package com.balinasoft.mallione.Implementations;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.balinasoft.mallione.Ui.Activities.BasketItemsActivity;
import com.balinasoft.mallione.interfaces.HandlingNotificationListener;
import com.balinasoft.mallione.networking.Services.MyFirebaseMessagingService;

/**
 * Created by Anton Kolotsey on 01.08.2016.
 */
public abstract class ShowFragmentNotification implements HandlingNotificationListener {
    Context context;
    public ShowFragmentNotification(Intent intent, Context context){
        this.context=context;
        checkInetnt(intent);

    }
    @Override
    public void checkInetnt(Intent intent) {
        if(intent!=null) {
            Bundle bundle=new Bundle();
            if (intent.getStringExtra(MyFirebaseMessagingService.SHOP) != null) {
                Intent intentShop=new Intent(context,BasketItemsActivity.class);
                intentShop.putExtra(MyFirebaseMessagingService.SHOP,intent.getStringExtra(MyFirebaseMessagingService.SHOP));
                shop(intentShop);
            }
            if (intent.getStringExtra(MyFirebaseMessagingService.RECORD) != null) {
                bundle.putString(MyFirebaseMessagingService.RECORD,intent.getStringExtra(MyFirebaseMessagingService.RECORD));

                record(bundle);
            }
            if (intent.getStringExtra(MyFirebaseMessagingService.ORDER) != null) {
                Intent orderIntent=new Intent(context, BasketItemsActivity.class);
                orderIntent.putExtra(MyFirebaseMessagingService.ORDER,intent.getStringExtra(MyFirebaseMessagingService.ORDER));
                order(orderIntent);
            }
            if (intent.getStringExtra(MyFirebaseMessagingService.ITEM) != null) {
                bundle.putString(MyFirebaseMessagingService.ITEM,intent.getStringExtra(MyFirebaseMessagingService.ITEM));
                item(bundle);
            }
        }
    }

    public abstract void order(Intent intent);

    public abstract void record(Bundle bundle);

    public abstract void shop(Intent intent);

    public abstract void item(Bundle bundle);
}
