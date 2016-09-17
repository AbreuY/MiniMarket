package com.balinasoft.mallione.models.modelUsers;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private City city;
    private int group_id;
    private int id;
    private String fio;
    private String phone;
    private String email;
    private String session_id;
    public User(){

    }
    protected User(Parcel in) {
        city = in.readParcelable(City.class.getClassLoader());
        group_id = in.readInt();
        id = in.readInt();
        fio = in.readString();
        phone = in.readString();
        email = in.readString();
        session_id = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(city, flags);
        dest.writeInt(group_id);
        dest.writeInt(id);
        dest.writeString(fio);
        dest.writeString(phone);
        dest.writeString(email);
        dest.writeString(session_id);
    }
}
