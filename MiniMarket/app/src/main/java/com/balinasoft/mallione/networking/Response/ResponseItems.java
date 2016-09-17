package com.balinasoft.mallione.networking.Response;

import com.balinasoft.mallione.models.ProductItems.PreViewProductItem;

import java.util.ArrayList;

/**
 * Created by Microsoft on 31.05.2016.
 */
public class ResponseItems extends BaseResponse{
    public ArrayList<PreViewProductItem> getResult() {
        return result;
    }

    public void setResult(ArrayList<PreViewProductItem> result) {
        this.result = result;
    }

    ArrayList<PreViewProductItem> result;
}
