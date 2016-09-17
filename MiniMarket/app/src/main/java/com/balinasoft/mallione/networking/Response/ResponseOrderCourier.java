package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.networking.Result.ResultOrderCourier;

/**
 * Created by Microsoft on 27.07.2016.
 */
public class ResponseOrderCourier extends BaseResponse{
    ResultOrderCourier result;

    public ResultOrderCourier getResult() {
        return result;
    }

    public void setResult(ResultOrderCourier result) {
        this.result = result;
    }
}
