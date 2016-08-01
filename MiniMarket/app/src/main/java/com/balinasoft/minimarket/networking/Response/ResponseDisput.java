package com.balinasoft.minimarket.networking.Response;

import com.balinasoft.minimarket.models.FullDispute;

/**
 * Created by Microsoft on 25.07.2016.
 */
public class ResponseDisput extends BaseResponse{
    public FullDispute getResult() {
        return result;
    }

    public void setResult(FullDispute result) {
        this.result = result;
    }

    FullDispute result;
}
