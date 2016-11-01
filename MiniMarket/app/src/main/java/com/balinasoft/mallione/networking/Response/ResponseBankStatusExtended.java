package com.balinasoft.mallione.networking.Response;

/**
 * Created by hzkto on 10/24/2016.
 */

public class ResponseBankStatusExtended {
    String orderStatus;
    String errorCode;

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
