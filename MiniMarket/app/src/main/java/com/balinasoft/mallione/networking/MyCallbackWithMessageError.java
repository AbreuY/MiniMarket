package com.balinasoft.mallione.networking;


import android.widget.Toast;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.networking.Response.BaseResponse;

import retrofit2.Call;

import retrofit2.Response;

abstract public class MyCallbackWithMessageError<T extends BaseResponse> extends BaseCallBack<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        onRequestEnd();
        if (response.isSuccessful()) {
            if (response.body() != null) {

                if (response.body().isSuccess()) {
                    onData(response.body());
                } else
                    Toast.makeText(getContext(), response.body().getError(), Toast.LENGTH_SHORT).show();

            } else
                Toast.makeText(getContext(), getContext().getString(R.string.error), Toast.LENGTH_SHORT).show();

        } else
            Toast.makeText(getContext(), getContext().getString(R.string.error), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        super.onFailure(call,t);
        Toast.makeText(getContext(), getContext().getString(R.string.error), Toast.LENGTH_SHORT).show();

    }

}
