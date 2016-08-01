package com.balinasoft.minimarket.models.ProductItems;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Microsoft on 05.07.2016.
 */
public class BasketProductItem extends PreViewProductItem implements Parcelable {
    @SerializedName("quantity")
    int amountProduct;
    public BasketProductItem(int id, String price, String item,String shop_id,String route_id) {
        super();
        setId(id);
        setPrice(price);
        setItem(item);
        setShop_id(shop_id);
        setRoute_id(route_id);
    }


    protected BasketProductItem(Parcel in) {
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

    public static final Creator<BasketProductItem> CREATOR = new Creator<BasketProductItem>() {
        @Override
        public BasketProductItem createFromParcel(Parcel in) {
            return new BasketProductItem(in);
        }

        @Override
        public BasketProductItem[] newArray(int size) {
            return new BasketProductItem[size];
        }
    };

    public int getAmountProduct() {
        return amountProduct;
    }

    public void incrementAmount() {
        amountProduct++;
    }

    public void decrementAmount() {
        if (amountProduct > 0)
            amountProduct--;
    }

    public void setAmountProduct(int amountProduct) {
        this.amountProduct = amountProduct;
    }
}
