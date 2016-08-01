package com.balinasoft.minimarket.networking.Request;

public class RequestOrderDispatcher
{
    private String response;

    private String session_id;

    private String courier_id;

    private String dispatcher_id;

    private String order_id;


    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getCourier_id() {
        return courier_id;
    }

    public void setCourier_id(String courier_id) {
        this.courier_id = courier_id;
    }

    public String getDispatcher_id() {
        return dispatcher_id;
    }

    public void setDispatcher_id(String dispatcher_id) {
        this.dispatcher_id = dispatcher_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}