package com.balinasoft.mallione.networking;

import com.balinasoft.mallione.networking.Response.ResponseBankRegister;
import com.balinasoft.mallione.networking.Response.ResponseBankStatus;
import com.balinasoft.mallione.networking.Response.ResponseBankStatusExtended;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by hzkto on 10/18/2016.
 */

public interface APIBank {
    String BASE_URL = "https://securepayments.sberbank.ru/payment/rest/";
//    String BASE_URL = "https://3dsec.sberbank.ru/payment/rest/";
    String PASSWORD = "mirny678170";
//    String PASSWORD = "mallione";
    String USER_NAME = "mallione-api";
    String RETURN_URL = "http://b2b-mallione.ru/payment/payment_success_ru.html";
    String FAIL_URL = "https://3dsec.sberbank.ru/payment/merchants/mallione/errors_ru.html";

    @POST("register.do")
    Call<ResponseBankRegister> register(
            @Query("amount") String amount,
            @Query("failUrl") String failUrl,
            @Query("orderNumber") String orderNumber,
            @Query("password") String password,
            @Query("returnUrl") String returnUrl,
            @Query("userName") String userName);

    @POST("getOrderStatus.do")
    Call<ResponseBankStatus> getOrderStatus(
            @Query("orderId") String orderId,
            @Query("password") String password,
            @Query("userName") String userName);

    @POST("getOrderStatusExtended.do")
    Call<ResponseBankStatusExtended> getOrderStatusExtended(
            @Query("orderNumber") String orderNumber,
            @Query("password") String password,
            @Query("userName") String userName);


}
