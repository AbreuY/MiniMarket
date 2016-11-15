package com.balinasoft.mallione.networking;


import android.widget.Toast;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.networking.Response.BaseResponse;

import java.io.IOException;

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
        super.onFailure(call, t);
        if (isConecctedToInternet()) {
            Toast.makeText(getContext(), getContext().getString(R.string.error), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Проверьте ваше подключение к сети", Toast.LENGTH_SHORT).show();
        }

    }

    public boolean isConecctedToInternet() {

        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
