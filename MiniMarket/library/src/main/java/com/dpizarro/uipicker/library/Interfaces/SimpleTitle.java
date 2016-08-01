package com.dpizarro.uipicker.library.Interfaces;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Microsoft on 16.06.2016.
 */
public class SimpleTitle implements Title,Parcelable{
    public SimpleTitle(){

    }
    protected SimpleTitle(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SimpleTitle> CREATOR = new Creator<SimpleTitle>() {
        @Override
        public SimpleTitle createFromParcel(Parcel in) {
            return new SimpleTitle(in);
        }

        @Override
        public SimpleTitle[] newArray(int size) {
            return new SimpleTitle[size];
        }
    };

    @Override
    public String getTitle() {
        return "";
    }

}
