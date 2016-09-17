package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.models.Notification;

import java.util.ArrayList;

/**
 * Created by Anton on 11.07.2016.
 */
public class ResponseNotification extends BaseResponse{
    ArrayList<Notification> result;

    public ArrayList<Notification> getResult() {
        return result;
    }

    public void setResult(ArrayList<Notification> result) {
        this.result = result;
    }
}
