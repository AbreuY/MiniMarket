package com.balinasoft.mallione.networking.Request;

/**
 * Created by Microsoft on 02.07.2016.
 */
public class RequestComment extends PaginationRequest {
    int item_id;
    public RequestComment(int item_id) {
        super(0,10);
        this.item_id=item_id;
    }
}
