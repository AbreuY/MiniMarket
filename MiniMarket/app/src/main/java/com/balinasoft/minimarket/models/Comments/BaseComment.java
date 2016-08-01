package com.balinasoft.minimarket.models.Comments;

import com.balinasoft.minimarket.models.modelUsers.User;

/**
 * Created by Microsoft on 19.07.2016.
 */
public class BaseComment {
    private int id;

    private User user;

    private String comment;

    private String date_time;

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
