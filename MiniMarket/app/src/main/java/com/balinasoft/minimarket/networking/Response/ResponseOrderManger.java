package com.balinasoft.minimarket.networking.Response;

import com.balinasoft.minimarket.networking.Result.ResultOrderManager;

/**
 * Created by Microsoft on 26.07.2016.
 */
public class ResponseOrderManger extends BaseResponse{
    ResultOrderManager result;

    public ResultOrderManager getResult() {
        return result;
    }

    public void setResult(ResultOrderManager result) {
        this.result = result;
    }
}
