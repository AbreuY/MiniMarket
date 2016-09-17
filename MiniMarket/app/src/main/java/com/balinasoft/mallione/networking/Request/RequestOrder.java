package com.balinasoft.mallione.networking.Request;

import com.balinasoft.mallione.models.ItemOrder;
import com.balinasoft.mallione.models.ProductItems.BasketProductItem;
import com.balinasoft.mallione.models.modelUsers.User;

import java.util.ArrayList;

/**
 * Created by Microsoft on 08.07.2016.
 */
public class RequestOrder {

    public static final String NON_CASH="non-cash";
    public static final String CASH="cash";
    private double total;

    private String type_payment;

    private long date_time_user;

    private String session_id;

    private String shop_id;

    private ArrayList<ItemOrder> items;

    private String address;

    private String description;

    private Integer user_id;

    public RequestOrder(User user, ArrayList<BasketProductItem> basketProductItems){
        this(basketProductItems);
        this.user_id=user.getId();
        this.session_id=user.getSession_id();
        shop_id=basketProductItems.get(0).getShop_id();

    }
    public RequestOrder(ArrayList<BasketProductItem> basketProductItems) {
        items=new ArrayList<>();
        for (BasketProductItem p : basketProductItems) {
            total += Double.valueOf(p.getPrice()) * p.getAmountProduct();
            items.add(new ItemOrder(p.getAmountProduct(),p.getId()));
        }

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getDate_time_user() {
        return date_time_user;
    }

    public void setDate_time_user(long date_time_user) {
        this.date_time_user = date_time_user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<ItemOrder> getItems() {
        return items;
    }

    public void setItems(ArrayList<ItemOrder> items) {
        this.items = items;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getType_payment() {
        return type_payment;
    }

    public void setType_payment(String type_payment) {
        this.type_payment = type_payment;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }
}
