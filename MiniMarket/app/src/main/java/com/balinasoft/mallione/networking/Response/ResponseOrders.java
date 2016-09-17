package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.networking.Result.ResultOrders;

/**
 * Created by Microsoft on 14.07.2016.
 */
public class ResponseOrders extends BaseResponse {
    private ResultOrders result;

    public ResultOrders getResult() {
        return result;
    }

    public void setResult(ResultOrders result) {
        this.result = result;
    }
}
