package com.balinasoft.mallione.models.modelUsers;

import android.os.Parcel;
import android.os.Parcelable;

import com.balinasoft.mallione.interfaces.Title;

public class Dispatcher extends User implements Title,Parcelable
{   public static final int GROUP_ID=6;
    private String o_inn;
    private String o_organization;
    private String o_address;

    private int dispatcher_id;

    public Dispatcher(){

    }
    protected Dispatcher(Parcel in) {
        super(in);
        o_inn = in.readString();
        o_organization = in.readString();
        o_address = in.readString();
        dispatcher_id = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(o_inn);
        dest.writeString(o_organization);
        dest.writeString(o_address);
        dest.writeInt(dispatcher_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Dispatcher> CREATOR = new Creator<Dispatcher>() {
        @Override
        public Dispatcher createFromParcel(Parcel in) {
            return new Dispatcher(in);
        }

        @Override
        public Dispatcher[] newArray(int size) {
            return new Dispatcher[size];
        }
    };

    public static Creator<Dispatcher> getCREATOR() {
        return CREATOR;
    }

    public static int getGroupId() {
        return GROUP_ID;
    }

    public String getO_address() {
        return o_address;
    }

    public void setO_address(String o_address) {
        this.o_address = o_address;
    }

    public String getO_organization() {
        return o_organization;
    }

    public void setO_organization(String o_organization) {
        this.o_organization = o_organization;
    }

    public String getO_inn ()
    {
        return o_inn;
    }

    public void setO_inn (String o_inn)
    {
        this.o_inn = o_inn;
    }

    public int getDispatcher_id ()
    {
        return dispatcher_id;
    }

    public void setDispatcher_id (int dispatcher_id)
    {
        this.dispatcher_id = dispatcher_id;
    }

    @Override
    public String toString()
    {
        return o_organization.toString();
//        return "ClassPojo [o_inn = "+o_inn+", dispatcher_id = "+dispatcher_id+"]";
    }

    @Override
    public String getTitle() {
        return o_inn;
    }

    @Override
    public int getGroup_id() {
        return super.getGroup_id();
    }
}