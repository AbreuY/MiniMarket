package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.networking.Result.ResultOrderBuyer;

/**
 * Created by Microsoft on 15.07.2016.
 */
public class ResponseOrderBuyer extends BaseResponse {

    public ResultOrderBuyer getResult() {
        return result;
    }

    public void setResult(ResultOrderBuyer result) {
        this.result = result;
    }

    ResultOrderBuyer result;
}
