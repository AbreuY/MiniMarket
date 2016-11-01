package com.balinasoft.mallione.networking.Response;

/**
 * Created by hzkto on 10/18/2016.
 */

public class ResponseBankRegister {
    String orderId;
    String formUrl;
    private String errorCode;
    private String errorMessage;

//    public String getErrorCode() {
//        return errorCode;
//    }

//    public void setErrorCode(String errorCode) {
//        this.errorCode = errorCode;
//    }

//    public String getErrorMessage() {
//        return errorMessage;
//    }

//    public void setErrorMessage(String errorMessage) {
//        this.errorMessage = errorMessage;
//    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFormUrl() {
        return formUrl;
    }

    public void setFormUrl(String formUrl) {
        this.formUrl = formUrl;
    }

    public String getError() {
        return errorMessage;
    }

    public boolean isSuccess(){
        if(errorCode.isEmpty()){
            return true;
        }
        return false;
    }
    boolean isFailure(){
        return !isSuccess();
    }
}
