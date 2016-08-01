package com.balinasoft.minimarket.networking.Response;

import com.balinasoft.minimarket.models.Blurb;

import java.util.ArrayList;

/**
 * Created by Microsoft on 20.07.2016.
 */
public class ResponseBlurb extends BaseResponse{
    public ArrayList<Blurb> getResult() {
        return result;
    }

    public void setResult(ArrayList<Blurb> result) {
        this.result = result;
    }

    ArrayList<Blurb> result;
}
