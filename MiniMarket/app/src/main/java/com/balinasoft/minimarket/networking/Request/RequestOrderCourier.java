package com.balinasoft.minimarket.networking.Request;

/**
 * Created by Microsoft on 27.07.2016.
 */
public class RequestOrderCourier {
    private String order_id;

    private String session_id;

    private String courier_id;
    private String response;

    public String getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(String courier_id) {
        this.courier_id = courier_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
