package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.models.Dispute;

import java.util.ArrayList;

/**
 * Created by Microsoft on 22.07.2016.
 */
public class ResponseDisputes extends BaseResponse {
    ArrayList<Dispute> result;

    public ArrayList<Dispute> getResult() {
        return result;
    }

    public void setResult(ArrayList<Dispute> result) {
        this.result = result;
    }
}
