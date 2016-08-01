package com.balinasoft.minimarket.models.ProductItems;

import android.os.Parcel;
import android.os.Parcelable;

import com.balinasoft.minimarket.models.Image;
import com.balinasoft.minimarket.models.Shops.Shop;

import java.util.ArrayList;

/**
 * Created by Microsoft on 31.05.2016.
 */
public class FullProductItem extends BaseProductItem implements Parcelable {


    ArrayList<Image> images;

    protected FullProductItem(Parcel in) {
        super(in);
        images = in.createTypedArrayList(Image.CREATOR);
        shop = in.readParcelable(Shop.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(images);
        dest.writeParcelable(shop, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FullProductItem> CREATOR = new Creator<FullProductItem>() {
        @Override
        public FullProductItem createFromParcel(Parcel in) {
            return new FullProductItem(in);
        }

        @Override
        public FullProductItem[] newArray(int size) {
            return new FullProductItem[size];
        }
    };

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    Shop shop;
    public ArrayList<Image> getImages() {
        return images;
    }
    public void setImages(ArrayList<Image> images) {
        this.images = images;
    }
}
