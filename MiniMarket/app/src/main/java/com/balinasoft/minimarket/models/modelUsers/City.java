package com.balinasoft.minimarket.models.modelUsers;


import android.os.Parcel;
import android.os.Parcelable;

import com.balinasoft.minimarket.interfaces.Title;

public class City  implements Title,Parcelable {
    private int id;
    private String city;
    public City(){

    }

    protected City(Parcel in) {
        id = in.readInt();
        city = in.readString();
    }

    public static final Creator<City> CREATOR = new Creator<City>() {
        @Override
        public City createFromParcel(Parcel in) {
            return new City(in);
        }

        @Override
        public City[] newArray(int size) {
            return new City[size];
        }
    };

    public int getCity_id() {
        return id;
    }

    public void setCity_id(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String getTitle() {
        return city;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(city);
    }
}
