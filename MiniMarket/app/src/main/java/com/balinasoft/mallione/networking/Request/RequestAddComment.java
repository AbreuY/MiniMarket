package com.balinasoft.mallione.networking.Request;

/**
 * Created by hzkto on 11/15/2016.
 */

public class RequestAddComment {
    String session_id;
    String user_id;
    String item_id;
    String comment;
    String score;

    public RequestAddComment(String session_id, String user_id, String item_id, String comment, String score) {
        this.session_id = session_id;
        this.user_id = user_id;
        this.item_id = item_id;
        this.comment = comment;
        this.score = score;
    }

    public RequestAddComment() {
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
