package com.balinasoft.minimarket.networking.Response;

import com.balinasoft.minimarket.networking.Result.ResultOrderCourier;

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
