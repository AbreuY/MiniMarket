package com.balinasoft.minimarket.networking.Response;

import com.balinasoft.minimarket.networking.Result.LoginResult;

/**
 * Created by Microsoft on 30.05.2016.
 */
public class ResponseAthorization extends BaseResponse{
    LoginResult result;
    public LoginResult getResult() {
        return result;
    }

    public void setResult(LoginResult result) {
        this.result = result;
    }


}
