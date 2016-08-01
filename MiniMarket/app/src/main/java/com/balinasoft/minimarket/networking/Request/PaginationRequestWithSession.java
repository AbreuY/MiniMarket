package com.balinasoft.minimarket.networking.Request;

/**
 * Created by Microsoft on 19.07.2016.
 */
public class PaginationRequestWithSession extends PaginationRequest {
    String session_id=null;
    String user_id=null;

    public PaginationRequestWithSession() {
        super(0,10);
    }

    public PaginationRequestWithSession(String session_id, String user_id) {
        this();
        this.session_id = session_id;
        this.user_id = user_id;
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
}
