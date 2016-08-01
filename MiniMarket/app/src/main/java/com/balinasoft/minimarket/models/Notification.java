package com.balinasoft.minimarket.models;

import com.balinasoft.minimarket.models.Shops.Shop;

/**
 * Created by Anton on 11.07.2016.
 */
public class Notification {
    private Shop shop;

    private String is_new;

    private String notification;

    private String order_id;

    private String record_id;
    private String date_time;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }
}
