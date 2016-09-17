package com.balinasoft.mallione.models;

/**
 * Created by Anton on 11.07.2016.
 */
public class TimeOrder {
    private int id;

    private Long time;

    private String description;

    public TimeOrder(int id, Long time, String description) {
        this.id = id;
        this.time = time;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
