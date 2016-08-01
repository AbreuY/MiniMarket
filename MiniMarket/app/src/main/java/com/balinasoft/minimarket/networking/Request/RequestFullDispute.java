package com.balinasoft.minimarket.networking.Request;

/**
 * Created by Microsoft on 25.07.2016.
 */
public class RequestFullDispute {
    String session_id, user_id,dispute_id;

    public RequestFullDispute(String dispute_id, String session_id, String user_id) {
        this.dispute_id = dispute_id;
        this.session_id = session_id;
        this.user_id = user_id;
    }
}
