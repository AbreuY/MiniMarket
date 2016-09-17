package com.balinasoft.mallione.networking.Request;

/**
 * Created by Microsoft on 19.07.2016.
 */
public class RequestReviews extends PaginationRequestWithSession {
    String shop_id;

    public RequestReviews(String session_id, String user_id, String shop_id) {
        super(session_id, user_id);
        this.shop_id = shop_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

}
