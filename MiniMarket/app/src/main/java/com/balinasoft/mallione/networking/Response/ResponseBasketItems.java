package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.models.ProductItems.BasketProductItem;

import java.util.ArrayList;

/**
 * Created by Microsoft on 05.07.2016.
 */
public class ResponseBasketItems extends BaseResponse {
    public ArrayList<BasketProductItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<BasketProductItem> result) {
        this.result = result;
    }

    ArrayList<BasketProductItem> result;
}
