package com.balinasoft.minimarket.models;

import com.balinasoft.minimarket.models.Shops.Shop;

/**
 * Created by Microsoft on 22.07.2016.
 */
public class Dispute {

    private String id;

    private Shop shop;

    private String title;

    private String is_open;

    private String date_time;
    private String order_id;

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIs_open() {
        return is_open;
    }

    public void setIs_open(String is_open) {
        this.is_open = is_open;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
    public boolean isOpen(){
        return is_open.equals(isOpenMarker);
    }
    public static final  String isOpenMarker="Открыт";
}
