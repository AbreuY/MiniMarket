package com.balinasoft.mallione.networking;

import com.balinasoft.mallione.networking.Response.BaseResponse;

import retrofit2.Call;
import retrofit2.Response;

abstract public class CallBackWithoutMessage<T extends BaseResponse> extends BaseCallBack<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        super.onResponse(call, response);
        if(response.body()!=null){
            onData(response.body());
        }
        onRequestEnd();
    }
}
