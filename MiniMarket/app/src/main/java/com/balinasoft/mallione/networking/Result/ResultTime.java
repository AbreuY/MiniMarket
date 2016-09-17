package com.balinasoft.mallione.networking.Result;

import com.balinasoft.mallione.models.Data;

import java.util.ArrayList;

/**
 * Created by Microsoft on 11.07.2016.
 */
public class ResultTime {
    private ArrayList<Data> data;

    private long day;

    public ArrayList<Data> getData() {
        return data;
    }

    public void setData(ArrayList<Data> data) {
        this.data = data;
    }

    public long getDay() {
        return day*1000;
    }

    public void setDay(long day) {
        this.day = day;
    }
}
