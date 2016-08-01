package com.balinasoft.minimarket.networking.Response;

import com.balinasoft.minimarket.networking.Result.ResultTime;

import java.util.ArrayList;

/**
 * Created by Anton on 11.07.2016.
 */
public class ResponseItemTime extends BaseResponse {


    ArrayList<ResultTime> result;

    public ArrayList<ResultTime> getResult() {
        return result;
    }

    public void setResult(ArrayList<ResultTime> result) {
        this.result = result;
    }
}
