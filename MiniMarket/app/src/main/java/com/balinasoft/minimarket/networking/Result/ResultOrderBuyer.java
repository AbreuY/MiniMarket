package com.balinasoft.minimarket.networking.Result;

import com.balinasoft.minimarket.models.ProductItems.BasketProductItem;
import com.balinasoft.minimarket.models.Shops.Shop;
import com.balinasoft.minimarket.models.modelUsers.Manager;

import java.util.ArrayList;

/**
 * Created by Microsoft on 15.07.2016.
 */
public class ResultOrderBuyer {
    private String total;

    private String type_payment;

    private Shop shop;

    private Manager manager;

    private String payment;

    private String status;

    private String dispute_status;

    private String date_time_end;

    private int id;

    private String date_time_start;

    private String date_time_approximate;

    private ArrayList<BasketProductItem> items;

    private String address;

    private String description;

    private String review_status;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate_time_approximate() {
        return date_time_approximate;
    }

    public void setDate_time_approximate(String date_time_approximate) {
        this.date_time_approximate = date_time_approximate;
    }

    public String getDate_time_end() {
        return date_time_end;
    }

    public void setDate_time_end(String date_time_end) {
        this.date_time_end = date_time_end;
    }

    public String getDate_time_start() {
        return date_time_start;
    }

    public void setDate_time_start(String date_time_start) {
        this.date_time_start = date_time_start;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDispute_status() {
        return dispute_status;
    }

    public void setDispute_status(String dispute_status) {
        this.dispute_status = dispute_status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<BasketProductItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<BasketProductItem> items) {
        this.items = items;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
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

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getType_payment() {
        return type_payment;
    }

    public void setType_payment(String type_payment) {
        this.type_payment = type_payment;
    }
}
