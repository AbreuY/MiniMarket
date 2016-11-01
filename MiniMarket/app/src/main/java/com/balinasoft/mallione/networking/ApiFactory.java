package com.balinasoft.mallione.networking;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Microsoft on 14.04.2016.
 */
public class ApiFactory {

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(45, TimeUnit.SECONDS)
            .build();

    @NonNull
    public static API getService() {

        return getRetrofit().create(API.class);
    }

    @NonNull
    public static APIBank getBankService() {

        return getBankRetrofit().create(APIBank.class);
    }
    private static Interceptor createInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder()
                        .method(original.method(), original.body());

//   .build();
                Log.d("API","url:" + original.url() + "; body:" + bodyToString(original));
                Request request = builder.build();
                Response r = chain.proceed(request);
                String bodyString = r.body().string();
                Log.d("API", "response: " + bodyString);
                return r.newBuilder()
                        .body(ResponseBody.create(r.body().contentType(), bodyString))
                        .build();
            }
        };
    }
    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            RequestBody body = copy.body();
            if (body != null) {
                body.writeTo
                        (buffer);
                return buffer.readUtf8();
            } else {
                return "get method:" +
                        request.url().encodedQuery();
            }
        } catch (final IOException e) {
            return "did not work";
        }
    }

    @NonNull
    private static Retrofit getRetrofit() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder()
//                .addInterceptor(createInterceptor())
//                .addInterceptor(interceptor)
//                .build();

        return new Retrofit.Builder()
                .baseUrl(API.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();
    }

    @NonNull
    private static Retrofit getBankRetrofit() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        return new Retrofit.Builder()
                .baseUrl(APIBank.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
                .build();
    }


}
