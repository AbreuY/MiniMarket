package com.balinasoft.mallione.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Category implements Parcelable {
    private int id;

    private String route_id;

    private String category;

    private String image;



    protected Category(Parcel in) {
        if (in != null) {
            id = in.readInt();
            category = in.readString();
            image = in.readString();

        }
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel in) {
            return new Category(in);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoute() {
        return category;
    }

    public void setRoute(String route) {
        this.category = route;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }





    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", route = " + category + ", image = " + image + ", rating = " +"]";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (dest != null) {
            dest.writeInt(id);
            dest.writeString(category);
            dest.writeString(image);

        }
    }

    public String getRoute_id() {
        return route_id;
    }

    public void setRoute_id(String route_id) {
        this.route_id = route_id;
    }
}