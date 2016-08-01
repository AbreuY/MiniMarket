package com.balinasoft.minimarket.networking.Request;

/**
 * Created by Anton on 11.07.2016.
 */
public class RequestUserData extends PaginationRequest {
    private String session_id;
    private int user_id;

    public RequestUserData(int limit) {
        super(0, limit);
    }
    public RequestUserData(){
        this(10);
    }

    public RequestUserData(String session_id, int user_id) {
        this(10);
        this.session_id = session_id;
        this.user_id = user_id;
    }
}
