package com.balinasoft.mallione.networking.Request;

/**
 * Created by Microsoft on 31.05.2016.
 */
public class RequestItem extends PaginationRequest{
   private int user_id;
   private int item_id;

    public RequestItem(int user_id, int item_id,int offset,int limit) {
        super(offset,limit);
        this.user_id = user_id;
        this.item_id = item_id;

    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }
}
