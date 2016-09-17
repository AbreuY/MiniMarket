package com.balinasoft.mallione.networking;

import android.support.annotation.NonNull;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Microsoft on 14.04.2016.
 */
public class ApiFactory {

    private static final OkHttpClient CLIENT =new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(45, TimeUnit.SECONDS)
            .build();

    @NonNull
    public static API getService() {
        return getRetrofit().create(API.class);
    }

    @NonNull
    private static Retrofit getRetrofit() {

        return new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
