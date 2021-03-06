package com.balinasoft.mallione.networking.Request;

public class RequestCourierStatus {
    private String c_status;

    private String session_id;

    private String user_id;

    public String getC_status() {
        return c_status;
    }

    public void setC_status(String c_status) {
        this.c_status = c_status;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public void setUserId(String courier_id) {
        this.user_id  = courier_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [c_status = " + c_status + ", session_id = " + session_id + ", courier_id = " + user_id  + "]";
    }
}
