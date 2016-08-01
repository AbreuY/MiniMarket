package com.balinasoft.minimarket.models.FormModel;

public class LoginForm extends BaseForm{
    private String platform = "android";
    private String token;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}