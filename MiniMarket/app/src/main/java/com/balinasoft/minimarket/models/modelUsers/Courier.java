package com.balinasoft.minimarket.models.modelUsers;


import android.os.Parcel;
import android.os.Parcelable;

import com.balinasoft.minimarket.interfaces.Title;

public class Courier extends User implements Parcelable,Title{
    public static final int GROUP_ID = 4;
    private String c_driver_license;
    private String c_car_brand;
    private String c_car_number;
    private String c_car_color;
    private String c_status;
    private float c_rating;
    public Courier(){

    }
    protected Courier(Parcel in) {
        super(in);
        c_driver_license = in.readString();
        c_car_brand = in.readString();
        c_car_number = in.readString();
        c_car_color = in.readString();
        c_status = in.readString();
        c_rating = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(c_driver_license);
        dest.writeString(c_car_brand);
        dest.writeString(c_car_number);
        dest.writeString(c_car_color);
        dest.writeString(c_status);
        dest.writeFloat(c_rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Courier> CREATOR = new Creator<Courier>() {
        @Override
        public Courier createFromParcel(Parcel in) {
            return new Courier(in);
        }

        @Override
        public Courier[] newArray(int size) {
            return new Courier[size];
        }
    };

    public String getC_status() {
        return c_status;
    }

    public void setC_status(String c_status) {
        this.c_status = c_status;
    }

    public float getC_rating() {
        return c_rating;
    }

    public void setC_rating(float c_rating) {
        this.c_rating = c_rating;
    }

    public String getC_driver_license() {
        return c_driver_license;
    }

    public void setC_driver_license(String c_driver_license) {
        this.c_driver_license = c_driver_license;
    }

    public String getC_car_brand() {
        return c_car_brand;
    }

    public void setC_car_brand(String c_car_brand) {
        this.c_car_brand = c_car_brand;
    }

    public String getC_car_number() {
        return c_car_number;
    }

    public void setC_car_number(String c_car_number) {
        this.c_car_number = c_car_number;
    }

    public String getC_car_color() {
        return c_car_color;
    }

    public void setC_car_color(String c_car_color) {
        this.c_car_color = c_car_color;
    }

    @Override
    public String getTitle() {
        return getFio();
    }
}
