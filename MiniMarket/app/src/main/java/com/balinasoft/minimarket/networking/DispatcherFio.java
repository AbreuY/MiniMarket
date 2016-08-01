package com.balinasoft.minimarket.networking;

import android.os.Parcel;
import android.os.Parcelable;

import com.balinasoft.minimarket.models.modelUsers.Dispatcher;

/**
 * Created by Microsoft on 26.07.2016.
 */
public class DispatcherFio extends Dispatcher implements Parcelable{

    protected DispatcherFio(Parcel in) {
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

    public static final Creator<DispatcherFio> CREATOR = new Creator<DispatcherFio>() {
        @Override
        public DispatcherFio createFromParcel(Parcel in) {
            return new DispatcherFio(in);
        }

        @Override
        public DispatcherFio[] newArray(int size) {
            return new DispatcherFio[size];
        }
    };

    @Override
    public String getTitle() {
        return getFio();
    }
}
