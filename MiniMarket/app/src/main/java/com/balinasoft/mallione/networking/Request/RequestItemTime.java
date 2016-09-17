package com.balinasoft.mallione.networking.Request;

/**
 * Created by Anton on 11.07.2016.
 */
public class RequestItemTime {
    int item_id;
    int offset;
    String session_id;
    int user_id;

    public RequestItemTime(int item_id, String session_id, int user_id) {
        this.item_id = item_id;
        this.offset = 0;
        this.session_id = session_id;
        this.user_id = user_id;
    }

    public void incrementOffset(){
        offset++;
    }
    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
}
