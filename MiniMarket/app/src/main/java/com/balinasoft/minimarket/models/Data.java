package com.balinasoft.minimarket.models;

import com.balinasoft.minimarket.interfaces.Title;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Microsoft on 11.07.2016.
 */
public class Data implements Title{
    private String id;

    private long time;

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time*1000;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String getTitle() {
        return new SimpleDateFormat("HH:mm").format(new Date(getTime()));
    }
}
