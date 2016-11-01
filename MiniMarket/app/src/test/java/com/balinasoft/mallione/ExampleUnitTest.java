package com.balinasoft.mallione;

import com.balinasoft.mallione.networking.Request.RequestOrderManager;

import org.junit.Test;

import java.io.IOException;

import static com.balinasoft.mallione.networking.ApiFactory.getService;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    //    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
//            .readTimeout(60, TimeUnit.SECONDS)
//            .connectTimeout(45, TimeUnit.SECONDS)
//            .build();
//    @NonNull
//    public static APIBank getBankService() {
//
//        return getBankRetrofit().create(APIBank.class);
//    }
//    @NonNull
//    private static Retrofit getBankRetrofit() {
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//
//        return new Retrofit.Builder()
//                .baseUrl(APIBank.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .client(client)
//                .build();
//    }


    @Test
    public void addition_isCorrect() throws IOException {
        RequestOrderManager requestOrderManager = new RequestOrderManager();
        requestOrderManager.setManager_id("69");
        requestOrderManager.setOrder_id("386");
        requestOrderManager.setSession_id("9a9aff3fd9f92c54020a559eccf75a68bf850c3c");
        requestOrderManager.setResponse("20 часов");
        //{"manager_id":"69","order_id":"386","response":"20 часов","session_id":"9a9aff3fd9f92c54020a559eccf75a68bf850c3c"}

        getService().orderManagerResponse2(requestOrderManager).execute();

//        ResponseAnswer r = ApiFactory.getService().orderManagerResponse(requestOrderManager).execute().body();
        //        ResponseBankRegister r = ApiFactory.getBankService().register3(32, APIBank.FAIL_URL, 25, APIBank.PASSWORD, APIBank.RETURN_URL, APIBank.USER_NAME).execute().body();
    }
}