package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.models.modelUsers.Courier;

import java.util.ArrayList;

/**
 * Created by Anton Kolotsey on 01.08.2016.
 */
public class CouriersDispatcherResponse extends BaseResponse{
    public ArrayList<Courier> getResult() {
        return result;
    }

    public void setResult(ArrayList<Courier> result) {
        this.result = result;
    }

    ArrayList<Courier> result;
}
