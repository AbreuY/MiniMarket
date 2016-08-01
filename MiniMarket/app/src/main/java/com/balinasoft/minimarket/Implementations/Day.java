package com.balinasoft.minimarket.Implementations;

import com.balinasoft.minimarket.interfaces.Title;

import java.text.SimpleDateFormat;

/**
 * Created by Microsoft on 11.07.2016.
 */
public class Day  implements Title{
    public long getDay() {
        return day;
    }

    long day;

    public Day(long day) {
        this.day = day;
    }

    @Override
    public String getTitle() {
        return new SimpleDateFormat("EEE,MM,d").format(day);
    }
}
