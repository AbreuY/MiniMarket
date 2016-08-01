package com.balinasoft.minimarket.networking;

import android.content.Context;
import android.widget.Toast;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.networking.Response.BaseResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Microsoft on 02.06.2016.
 */
abstract public class MyCallback<T extends BaseResponse> implements Callback<T> {

    private static Context context;
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onRequestEnd();
        if (response.isSuccessful()) {
            if (response.body() != null) {

                if (response.body().isSuccess()) {
                    onData(response.body());
                } else
                    Toast.makeText(context, response.body().getError(), Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show();
        onRequestEnd();
    }

    abstract public void onData(T data);
    abstract public void onRequestEnd();
    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        MyCallback.context = context;
    }
}
