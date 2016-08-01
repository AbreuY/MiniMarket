package com.balinasoft.minimarket.networking.Response;

import com.balinasoft.minimarket.networking.DispatcherFio;

import java.util.ArrayList;

/**
 * Created by Microsoft on 24.06.2016.
 */
public class ResponseDispatchers extends BaseResponse{
    public ArrayList<DispatcherFio> getResult() {
        return result;
    }

    public void setResult(ArrayList<DispatcherFio> result) {
        this.result = result;
    }

    ArrayList<DispatcherFio> result;
}
