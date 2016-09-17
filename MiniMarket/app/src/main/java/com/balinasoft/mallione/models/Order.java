package com.balinasoft.mallione.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.balinasoft.mallione.models.Shops.Shop;

/**
 * Created by Microsoft on 14.07.2016.
 */
public class Order implements Parcelable{
    private String date_time_start;

    private String total;

    private int id;

    private Shop shop;

    private String type_payment;

    private String date_time_approximate;

    private String status;

    private String review_status;

    private String date_time_end;


    protected Order(Parcel in) {
        date_time_start = in.readString();
        total = in.readString();
        id = in.readInt();
        shop = in.readParcelable(Shop.class.getClassLoader());
        type_payment = in.readString();
        date_time_approximate = in.readString();
        status = in.readString();
        review_status = in.readString();
        date_time_end = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date_time_start);
        dest.writeString(total);
        dest.writeInt(id);
        dest.writeParcelable(shop, flags);
        dest.writeString(type_payment);
        dest.writeString(date_time_approximate);
        dest.writeString(status);
        dest.writeString(review_status);
        dest.writeString(date_time_end);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getDate_time_approximate() {
        return date_time_approximate;
    }

    public void setDate_time_approximate(String date_time_approximate) {
        this.date_time_approximate = date_time_approximate;
    }

    public String getDate_time_end() {
        return date_time_end;
    }

    public void setDate_time_end(String date_time_end) {
        this.date_time_end = date_time_end;
    }

    public String getDate_time_start() {
        return date_time_start;
    }

    public void setDate_time_start(String date_time_start) {
        this.date_time_start = date_time_start;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReview_status() {
        return review_status;
    }

    public void setReview_status(String review_status) {
        this.review_status = review_status;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getType_payment() {
        return type_payment;
    }

    public void setType_payment(String type_payment) {
        this.type_payment = type_payment;
    }

}
