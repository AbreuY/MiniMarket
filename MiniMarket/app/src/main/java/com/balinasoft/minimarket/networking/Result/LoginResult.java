package com.balinasoft.minimarket.networking.Result;

import com.balinasoft.minimarket.models.modelUsers.Buer;
import com.balinasoft.minimarket.models.modelUsers.Courier;
import com.balinasoft.minimarket.models.modelUsers.Dispatcher;
import com.balinasoft.minimarket.models.modelUsers.Manager;
import com.balinasoft.minimarket.models.modelUsers.Provider;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

/**
 * Created by Microsoft on 30.05.2016.
 */
public class LoginResult extends AnswerResult {
    //GROUP_TAGS
    private static final String PROVIDER = "2";
    private static final String MANAGER = "3";
    private static final String COURIER = "4";
    private static final String BUER = "5";
    private static final String GROUP_ID = "group_id";
    public static final String DISPATCHER="6";
    private JsonObject user;
    public User getUser() {

        if (isBuer()) {
            return new Gson().fromJson(user, Buer.class);
        }
        if (isCourier()) {
            return new Gson().fromJson(user, Courier.class);
        }
        if (isProvider()) {
            return new Gson().fromJson(user, Provider.class);
        }
        if (isManager()) {
            return new Gson().fromJson(user, Manager.class);
        }
        if(isDispatcher()){
            return new Gson().fromJson(user, Dispatcher.class);
        }
        return null;
    }

    public boolean isProvider() {
        return checkGroup(PROVIDER);
    }

    public boolean isManager() {
        return checkGroup(MANAGER);
    }

    public boolean isCourier() {
        return checkGroup(COURIER);
    }

    public boolean isBuer() {
        return checkGroup(BUER);
    }

    private boolean checkGroup(String GROUP_TAG) {//ADMIN,PROVIDER,MANAGER,COURIER,BUER
        if (user != null && user.get(GROUP_ID).getAsString().equals(GROUP_TAG)) {
            return true;
        }
        return false;
    }

    public boolean isDispatcher() {
        return checkGroup(DISPATCHER);
    }
}
