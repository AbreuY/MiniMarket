package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.models.Comments.Review;

import java.util.ArrayList;

/**
 * Created by Microsoft on 19.07.2016.
 */
public class ResposeReviewsShopForBuyer extends BaseResponse{
    ArrayList<Review> result;

    public ArrayList<Review> getResult() {
        return result;
    }

    public void setResult(ArrayList<Review> result) {
        this.result = result;
    }
}
