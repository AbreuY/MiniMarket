package com.balinasoft.mallione.networking.Response;

/**
 * Created by hzkto on 10/18/2016.
 */

public class ResponseBankStatus {
    String OrderStatus;
    String ErrorCode;

    public String getOrderStatus() {
        return OrderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        OrderStatus = orderStatus;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }
}
