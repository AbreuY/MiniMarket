package com.balinasoft.minimarket.models.FireBase;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Microsoft on 28.06.2016.
 */
@IgnoreExtraProperties
public class ProductFire implements Parcelable{
    int id;

    protected ProductFire(Parcel in) {
        id = in.readInt();
        amount = in.readInt();
    }

    public static final Creator<ProductFire> CREATOR = new Creator<ProductFire>() {
        @Override
        public ProductFire createFromParcel(Parcel in) {
            return new ProductFire(in);
        }

        @Override
        public ProductFire[] newArray(int size) {
            return new ProductFire[size];
        }
    };

    public ProductFire(int id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    int amount;
    public ProductFire(){

    }
    public ProductFire(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(amount);
    }
}
