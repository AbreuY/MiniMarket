package com.balinasoft.minimarket.models.ProductItems;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Microsoft on 01.07.2016.
 */
public class SuperProductItem implements Parcelable{
    public static final String PRODUCT="1";
    public static final String ORDER="2";
    private int id;
    private String price;
    private String item;


    protected SuperProductItem(Parcel in) {
        id = in.readInt();
        price = in.readString();
        item = in.readString();
        route_id = in.readString();
        shop_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(price);
        dest.writeString(item);
        dest.writeString(route_id);
        dest.writeString(shop_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SuperProductItem> CREATOR = new Creator<SuperProductItem>() {
        @Override
        public SuperProductItem createFromParcel(Parcel in) {
            return new SuperProductItem(in);
        }

        @Override
        public SuperProductItem[] newArray(int size) {
            return new SuperProductItem[size];
        }
    };

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }

    private String route_id;

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    String shop_id;
    public SuperProductItem(){

    }
    public SuperProductItem(int id, String price, String item,String shop_id,String route_id) {
        this.id = id;
        this.price = price;
        this.item = item;
        this.shop_id=shop_id;
        this.route_id=route_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
