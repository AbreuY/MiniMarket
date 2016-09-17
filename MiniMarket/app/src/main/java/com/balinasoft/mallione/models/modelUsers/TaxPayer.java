package com.balinasoft.mallione.models.modelUsers;

import android.os.Parcel;
import android.os.Parcelable;

// налогоплательщик
public class TaxPayer extends User implements Parcelable {
    protected TaxPayer(Parcel in) {
        super(in);
        s_inn = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(s_inn);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TaxPayer> CREATOR = new Creator<TaxPayer>() {
        @Override
        public TaxPayer createFromParcel(Parcel in) {
            return new TaxPayer(in);
        }

        @Override
        public TaxPayer[] newArray(int size) {
            return new TaxPayer[size];
        }
    };

    public String getS_inn() {
        return s_inn;
    }

    public void setS_inn(String s_inn) {
        this.s_inn = s_inn;
    }

    String s_inn;
}
