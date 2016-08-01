package com.balinasoft.minimarket.models;

/**
 * Created by Microsoft on 08.07.2016.
 */
public class ItemOrder {
    private int item_id;

    private int quantity;

    public ItemOrder(int quantity, int item_id) {
        this.quantity = quantity;
        this.item_id = item_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
