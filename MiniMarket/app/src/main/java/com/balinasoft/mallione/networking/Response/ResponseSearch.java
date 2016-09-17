package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.models.ProductItems.PreViewProductItem;

import java.util.ArrayList;

/**
 * Created by Anton Kolotsey on 21.07.2016.
 */
public class ResponseSearch extends BaseResponse{
    public ArrayList<PreViewProductItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<PreViewProductItem> result) {
        this.result = result;
    }

    ArrayList<PreViewProductItem> result;
}
