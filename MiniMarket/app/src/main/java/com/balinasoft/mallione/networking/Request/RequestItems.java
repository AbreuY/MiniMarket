package com.balinasoft.mallione.networking.Request;

import java.util.List;

/**
 * Created by Microsoft on 31.05.2016.
 */
public class RequestItems extends PaginationRequest {

    private int shop_id;

    private List<Integer> items_id;

    public RequestItems(int shop_id) {
        super(0,10);
        this.shop_id=shop_id;
    }
    public RequestItems(List<Integer> items_id) {
        super(0,items_id.size());
        this.items_id=items_id;
    }
    public void addItemsId(Integer item_id){
        items_id.add(item_id);
        setLimit(items_id.size());
    }
    public RequestItems(int offset, int limit) {
        super(offset, limit);
    }


    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public List<Integer> getItems_id() {
        return items_id;
    }

    public void setItems_id(List<Integer> items_id) {
        this.items_id = items_id;
    }
}
