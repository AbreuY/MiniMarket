package com.balinasoft.minimarket.networking.Result;

import com.balinasoft.minimarket.models.Order;

import java.util.ArrayList;

/**
 * Created by Microsoft on 14.07.2016.
 */
public class ResultOrders {
    private ArrayList<Order> complete;
    private ArrayList<Order> active;

    public ArrayList<Order> getActive() {
        return active;
    }

    public void setActive(ArrayList<Order> active) {
        this.active = active;
    }

    public ArrayList<Order> getComplete() {
        return complete;
    }

    public void setComplete(ArrayList<Order> complete) {
        this.complete = complete;
    }
}
