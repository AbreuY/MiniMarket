package com.balinasoft.mallione.networking;

import android.content.Context;

import com.balinasoft.mallione.networking.Response.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Anton Kolotsey on 09.09.2016.
 */
abstract public class BaseCallBack<T extends BaseResponse> implements Callback<T> {
    private static Context context;
    @Override
    public void onResponse(Call<T> call, Response<T> response) {

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        onRequestEnd();
    }

    abstract public void onData(T data);
    abstract public void onRequestEnd();
    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        BaseCallBack.context = context;
    }

}
