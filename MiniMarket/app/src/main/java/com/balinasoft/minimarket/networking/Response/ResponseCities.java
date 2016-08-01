package com.balinasoft.minimarket.networking.Response;

import com.balinasoft.minimarket.models.modelUsers.City;

import java.util.ArrayList;

/**
 * Created by Microsoft on 14.06.2016.
 */
public class ResponseCities extends BaseResponse {
    public ArrayList<City> getResult() {
        return result;
    }

    public void setResult(ArrayList<City> result) {
        this.result = result;
    }

    ArrayList<City> result;
}
