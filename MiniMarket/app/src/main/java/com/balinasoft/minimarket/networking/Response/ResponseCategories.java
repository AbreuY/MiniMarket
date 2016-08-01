package com.balinasoft.minimarket.networking.Response;

import com.balinasoft.minimarket.models.Category;

import java.util.ArrayList;

/**
 * Created by Microsoft on 31.05.2016.
 */
public class ResponseCategories extends BaseResponse {
    public ArrayList<Category> getResult() {
        return result;
    }

    public void setResult(ArrayList<Category> result) {
        this.result = result;
    }

    ArrayList<Category> result;
}
