package com.balinasoft.minimarket.networking.Response;

import com.balinasoft.minimarket.models.modelUsers.GroupUser;

import java.util.ArrayList;

/**
 * Created by Microsoft on 22.06.2016.
 */
public class ResponseGroupUser extends BaseResponse {
    ArrayList<GroupUser> result;

    public ArrayList<GroupUser> getResult() {
        return result;
    }

    public void setResult(ArrayList<GroupUser> result) {
        this.result = result;
    }
}
