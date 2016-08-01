package com.balinasoft.minimarket.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.balinasoft.minimarket.models.modelUsers.Manager;


public class FullOrder extends Order implements Parcelable{

    private Manager manager;

    private String payment;

    private String dispute_status;

    private String address;

    private String description;


    protected FullOrder(Parcel in) {
        super(in);
        manager = in.readParcelable(Manager.class.getClassLoader());
        payment = in.readString();
        dispute_status = in.readString();
        address = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(manager, flags);
        dest.writeString(payment);
        dest.writeString(dispute_status);
        dest.writeString(address);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<FullOrder> CREATOR = new Creator<FullOrder>() {
        @Override
        public FullOrder createFromParcel(Parcel in) {
            return new FullOrder(in);
        }

        @Override
        public FullOrder[] newArray(int size) {
            return new FullOrder[size];
        }
    };

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDispute_status() {
        return dispute_status;
    }

    public void setDispute_status(String dispute_status) {
        this.dispute_status = dispute_status;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }
}
