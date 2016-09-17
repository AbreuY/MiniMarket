package com.balinasoft.mallione.models;

import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.models.modelUsers.Manager;

import java.util.ArrayList;

/**
 * Created by Microsoft on 25.07.2016.
 */
public class FullDispute extends Dispute{
    ArrayList<UrlImage> images;
    Buer user;
    Manager manager;

    public void setResult(String result) {
        this.result = result;
    }

    private String result;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;

    public ArrayList<UrlImage> getImages() {
        return images;
    }

    public void setImages(ArrayList<UrlImage> images) {
        this.images = images;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public Buer getUser() {
        return user;
    }

    public void setUser(Buer user) {
        this.user = user;
    }

    public String getResult() {
        return this.result;
    }
}
