package com.balinasoft.minimarket.models.modelUsers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Microsoft on 31.05.2016.
 */
public class Manager extends User implements Parcelable{
    public static final int GROUP_ID=3;
    public Manager(){

    }
    protected Manager(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Manager> CREATOR = new Creator<Manager>() {
        @Override
        public Manager createFromParcel(Parcel in) {
            return new Manager(in);
        }

        @Override
        public Manager[] newArray(int size) {
            return new Manager[size];
        }
    };
}
