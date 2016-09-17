package com.balinasoft.mallione.models;

/**
 * Created by Microsoft on 20.07.2016.
 */
public class Blurb {
    private String content;

    private String title;

    private int shop_id;

    private int item_id;

    private String image;

    public String getExternal_url() {
        return external_url;
    }

    private String external_url;

    public void setExternal_url(String external_url) {
        this.external_url = external_url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public int getShop_id() {
        return shop_id;
    }

    public void setShop_id(int shop_id) {
        this.shop_id = shop_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
