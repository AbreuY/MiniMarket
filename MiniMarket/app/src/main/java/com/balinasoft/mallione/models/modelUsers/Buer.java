package com.balinasoft.mallione.models.modelUsers;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Microsoft on 30.05.2016.
 */
public class Buer extends User implements Parcelable {
    public static final int GROUP_ID = 5;
    private String u_address_1;
    private String u_address_2;

    public Buer(){

    }
    protected Buer(Parcel in) {
        super(in);
        u_address_1 = in.readString();
        u_address_2 = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(u_address_1);
        dest.writeString(u_address_2);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Buer> CREATOR = new Creator<Buer>() {
        @Override
        public Buer createFromParcel(Parcel in) {
            return new Buer(in);
        }

        @Override
        public Buer[] newArray(int size) {
            return new Buer[size];
        }
    };

    public String getU_address_1() {
        return u_address_1;
    }

    public void setU_address_1(String u_address_1) {
        this.u_address_1 = u_address_1;
    }

    public String getU_address_2() {
        return u_address_2;
    }

    public void setU_address_2(String u_address_2) {
        this.u_address_2 = u_address_2;
    }

}
