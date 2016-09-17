package com.balinasoft.mallione.networking.Request;

/**
 * Created by Microsoft on 13.07.2016.
 */
public class RequestAddRecord {
    private long time;

    private String session_id;

    private String time_id;

    private int user_id;

    public RequestAddRecord(){

    }
    public RequestAddRecord(String session_id, long time, String time_id, int user_id) {
        this.session_id = session_id;
        this.time = time;
        this.time_id = time_id;
        this.user_id = user_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String  getTime_id() {
        return time_id;
    }

    public void setTime_id(String time_id) {
        this.time_id = time_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
