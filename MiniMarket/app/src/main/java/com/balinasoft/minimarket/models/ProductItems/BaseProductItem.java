package com.balinasoft.minimarket.models.ProductItems;

import android.os.Parcel;
import android.os.Parcelable;

public class BaseProductItem extends SuperProductItem implements Parcelable {


    private String description;

    private int comment_status;


    private String rating;

public BaseProductItem(){
    super();
}
    protected BaseProductItem(Parcel in) {
        super(in);
        description = in.readString();
        comment_status = in.readInt();
        rating = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(description);
        dest.writeInt(comment_status);
        dest.writeString(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseProductItem> CREATOR = new Creator<BaseProductItem>() {
        @Override
        public BaseProductItem createFromParcel(Parcel in) {
            return new BaseProductItem(in);
        }

        @Override
        public BaseProductItem[] newArray(int size) {
            return new BaseProductItem[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getComment_status() {
        return comment_status;
    }

    public void setComment_status(int comment_status) {
        this.comment_status = comment_status;
    }

    public boolean isCommented() {
        if (comment_status == 1) {
            return true;
        }
        return false;
    }




    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ClassPojo [, description = " + description + ", comment_status = " + comment_status + ", item = " + ", rating = " + rating + "]";
    }
}