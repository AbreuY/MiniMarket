package com.balinasoft.minimarket.networking.Request;

import java.util.List;

/**
 * Created by Microsoft on 31.05.2016.
 */
public class RequestShops extends PaginationRequest {
    private Integer category_id;
    private List<String> shops_id;
    private String session_id;
    private String user_id;


    public RequestShops(int route_id) {
        super(0, 10);//по 10 штук
        this.category_id = route_id;
    }
    public RequestShops(List<String> shops_id){
        this(0);
        this.shops_id=shops_id;
    }

    public int getRoute_id() {
        return category_id;
    }

    public void setRoute_id(int route_id) {
        this.category_id = route_id;
    }

    public List<String> getShops_id() {
        return shops_id;
    }

    public void setShops_id(List<String> shops_id) {
        this.shops_id = shops_id;
    }


    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
