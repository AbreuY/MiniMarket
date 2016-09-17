package com.balinasoft.mallione.networking.Request;

/**
 * Created by Microsoft on 25.07.2016.
 */
public class RequestUser {

    String user_id,session_id;

    public RequestUser(String session_id, String id) {
        this.user_id=id;
        this.session_id=session_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public RequestUser(){

    }
}
