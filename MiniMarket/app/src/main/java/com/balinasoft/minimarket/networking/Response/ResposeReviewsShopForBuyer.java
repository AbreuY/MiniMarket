package com.balinasoft.minimarket.networking.Response;

import com.balinasoft.minimarket.models.Comments.Review;

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
