package com.balinasoft.mallione.networking.Response;

/**
 * Created by Microsoft on 30.05.2016.
 */
public class BaseResponse {
    private int success;
    private String error;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public boolean isSuccess(){
        if(success==1){
            return true;
        }
        return false;
    }
    boolean isFailure(){
        return !isSuccess();
    }
}
