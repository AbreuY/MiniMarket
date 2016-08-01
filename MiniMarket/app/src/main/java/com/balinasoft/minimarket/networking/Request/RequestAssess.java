package com.balinasoft.minimarket.networking.Request;

/**
 * Created by Microsoft on 15.07.2016.
 */
public class RequestAssess {
    private String courier_score;

    private String session_id;

    private String shop_score;

    private String user_id;

    private String comment;

    private String order_id;

    private String record_id;

    public RequestAssess() {

    }

    public RequestAssess(String comment, String courier_score, String order_id, String record_id, String session_id, String shop_score, String user_id) {
        this.comment = comment;
        this.courier_score = courier_score;
        this.order_id = order_id;
        this.record_id = record_id;
        this.session_id = session_id;
        this.shop_score = shop_score;
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getCourier_score() {
        return courier_score;
    }

    public void setCourier_score(String courier_score) {
        this.courier_score = courier_score;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getRecord_id() {
        return record_id;
    }

    public void setRecord_id(String record_id) {
        this.record_id = record_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    public String getShop_score() {
        return shop_score;
    }

    public void setShop_score(String shop_score) {
        this.shop_score = shop_score;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public boolean isValid(){
        if(shop_score==null && shop_score.isEmpty()){
            return false;
        }
        if(order_id==null && order_id.isEmpty()){
            if(record_id==null && record_id.isEmpty()){
                return false;
            }
        }
        if(record_id==null && record_id.isEmpty()){
            if(order_id==null && order_id.isEmpty()){
                return false;
            }
        }
        return true;
    }
}
