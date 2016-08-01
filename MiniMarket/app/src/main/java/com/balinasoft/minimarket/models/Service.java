package com.balinasoft.minimarket.models;

import com.balinasoft.minimarket.models.ProductItems.PreViewProductItem;
import com.balinasoft.minimarket.models.Shops.Shop;

/**
 * Created by Microsoft on 13.07.2016.
 */
public class Service {
    public static final String ACTIVE="Активный";
    private String id;

    private Shop shop;

    private String price;

    private String status;

    private PreViewProductItem item;

    private String date_time_record;

    private String review_status;


    public String getDate_time_record() {
        return date_time_record;
    }

    public void setDate_time_record(String date_time_record) {
        this.date_time_record = date_time_record;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PreViewProductItem getItem() {
        return item;
    }

    public void setItem(PreViewProductItem item) {
        this.item = item;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getReview_status() {
        return review_status;
    }

    public void setReview_status(String review_status) {
        this.review_status = review_status;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
