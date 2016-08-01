package com.balinasoft.minimarket.networking.Request;

/**
 * Created by Anton on 11.07.2016.
 */
public class RequestCountNotification {
    private String session_id;
    private int user_id;

    public RequestCountNotification(String session_id, int user_id) {
        this.session_id = session_id;
        this.user_id = user_id;
    }
}
