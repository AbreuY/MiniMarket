package com.balinasoft.mallione.models.Comments;

/**
 * Created by Microsoft on 19.07.2016.
 */
public class Review extends BaseComment {
    public static final int MAX_STAR=5;
    private String courier_score;

    private String shop_score;

    public String getCourier_score() {
        return courier_score;
    }

    public void setCourier_score(String courier_score) {
        this.courier_score = courier_score;
    }

    public String getShop_score() {
        return shop_score;
    }

    public void setShop_score(String shop_score) {
        this.shop_score = shop_score;
    }
    public float getRatingShop(){
        float star=Float.valueOf(shop_score)/MAX_STAR;
        float rating=star*Integer.valueOf(shop_score);
        return rating;
    }
}
