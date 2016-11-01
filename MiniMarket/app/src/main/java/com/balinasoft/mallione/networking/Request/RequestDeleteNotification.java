package com.balinasoft.mallione.networking.Request;

public class RequestDeleteNotification {
    private String notification_id;

    private String session_id;

    private int user_id;


    public void setNotification_id(String notification_id) {
        this.notification_id = notification_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "ClassPojo [notification_id = " + notification_id + ", session_id = " + session_id + ", user_id = " + "qwe"  + "]";

    }
}
