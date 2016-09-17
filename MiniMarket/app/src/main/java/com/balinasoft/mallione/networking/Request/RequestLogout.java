package com.balinasoft.mallione.networking.Request;

/**
 * Created by Microsoft on 21.07.2016.
 */
public class RequestLogout {
    private String session_id;

    private String token;

    private String user_id;

    public RequestLogout(String session_id, String token, String user_id) {
        this.session_id = session_id;
        this.token = token;
        this.user_id = user_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
