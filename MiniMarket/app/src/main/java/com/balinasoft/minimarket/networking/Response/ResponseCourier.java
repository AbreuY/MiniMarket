package com.balinasoft.minimarket.networking.Response;

import com.balinasoft.minimarket.models.modelUsers.Courier;

import java.util.ArrayList;

/**
 * Created by Microsoft on 26.07.2016.
 */
public class ResponseCourier extends BaseResponse{
    ArrayList<Courier> result;

    public ArrayList<Courier> getResult() {
        return result;
    }

    public void setResult(ArrayList<Courier> result) {
        this.result = result;
    }
}
