package com.balinasoft.mallione.models.Shops;

import android.os.Parcel;
import android.os.Parcelable;

import com.balinasoft.mallione.models.modelUsers.City;
import com.balinasoft.mallione.models.modelUsers.Manager;

public class Shop implements Parcelable{
    private int id;

    private String shop;

    private Manager manager;

    private String image;

    private String rating;

    private City city;

    private String phone;

    private String time_work;

    private String address;
    protected Shop(Parcel in) {
        id = in.readInt();

        shop = in.readString();
        manager = in.readParcelable(Manager.class.getClassLoader());
        image = in.readString();
        rating = in.readString();
        city = in.readParcelable(City.class.getClassLoader());
        phone = in.readString();
        time_work = in.readString();
        address = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(shop);
        dest.writeParcelable(manager, flags);
        dest.writeString(image);
        dest.writeString(rating);
        dest.writeParcelable(city, flags);
        dest.writeString(phone);
        dest.writeString(time_work);
        dest.writeString(address);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Shop> CREATOR = new Creator<Shop>() {
        @Override
        public Shop createFromParcel(Parcel in) {
            return new Shop(in);
        }

        @Override
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ClassPojo [id = " + id + ", shop = " + shop + ", manager = " + manager + ", image = " + image + ", rating = " + rating + ", city = " + city + "]";
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public static Creator<Shop> getCREATOR() {
        return CREATOR;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime_work() {
        return time_work;
    }

    public void setTime_work(String time_work) {
        this.time_work = time_work;
    }
}