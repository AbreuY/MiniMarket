package com.balinasoft.mallione.models.modelUsers;


import android.os.Parcel;
import android.os.Parcelable;

public class Provider extends TaxPayer implements Parcelable {
    private String s_address;
    private int s_routes_id;

    protected Provider(Parcel in) {
        super(in);
        s_address = in.readString();
        s_routes_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(s_address);
        dest.writeInt(s_routes_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Provider> CREATOR = new Creator<Provider>() {
        @Override
        public Provider createFromParcel(Parcel in) {
            return new Provider(in);
        }

        @Override
        public Provider[] newArray(int size) {
            return new Provider[size];
        }
    };

    public String getS_address() {
        return s_address;
    }

    public void setS_address(String s_address) {
        this.s_address = s_address;
    }

    public int getS_routes_id() {
        return s_routes_id;
    }

    public void setS_routes_id(int s_routes_id) {
        this.s_routes_id = s_routes_id;
    }
}
