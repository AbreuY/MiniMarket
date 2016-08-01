package com.balinasoft.minimarket.models.ProductItems;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Microsoft on 31.05.2016.
 */
@IgnoreExtraProperties
public class PreViewProductItem extends BaseProductItem implements Parcelable {
    public PreViewProductItem(){
        super();
    }
    protected PreViewProductItem(Parcel in) {
        super(in);
        image = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PreViewProductItem> CREATOR = new Creator<PreViewProductItem>() {
        @Override
        public PreViewProductItem createFromParcel(Parcel in) {
            return new PreViewProductItem(in);
        }

        @Override
        public PreViewProductItem[] newArray(int size) {
            return new PreViewProductItem[size];
        }
    };

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    String image;


}
