package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.models.Shops.ShopBasket;

import java.util.ArrayList;

/**
 * Created by Microsoft on 31.05.2016.
 */
public class ResponseShops extends BaseResponse {
    public ArrayList<ShopBasket> getResult() {
        return result;
    }

    public void setResult(ArrayList<ShopBasket> result) {
        this.result = result;
    }

    ArrayList<ShopBasket> result;
}
