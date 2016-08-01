package com.balinasoft.minimarket.models.Shops;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Microsoft on 04.07.2016.
 */
public class ShopBasket extends Shop implements Parcelable {
    public int getAmountProduct() {
        return amountProduct;
    }

    public void setAmountProduct(int amountProduct) {
        this.amountProduct = amountProduct;
    }

    int amountProduct;

    protected ShopBasket(Parcel in) {
        super(in);
        amountProduct = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(amountProduct);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ShopBasket> CREATOR = new Creator<ShopBasket>() {
        @Override
        public ShopBasket createFromParcel(Parcel in) {
            return new ShopBasket(in);
        }

        @Override
        public ShopBasket[] newArray(int size) {
            return new ShopBasket[size];
        }
    };
    public String getIdToStrind(){
        return String.valueOf(getId());
    }

    public void addAmountProduct(int amount) {
        amountProduct=amount+amountProduct;
    }
}
