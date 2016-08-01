package com.balinasoft.minimarket.models.FormModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Microsoft on 28.07.2016.
 */
public class UpdateUserDataForm extends RegistrationForm implements Parcelable{
    String session_id;

    public UpdateUserDataForm(){

    }
    protected UpdateUserDataForm(Parcel in) {
        super(in);
        session_id = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(session_id);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UpdateUserDataForm> CREATOR = new Creator<UpdateUserDataForm>() {
        @Override
        public UpdateUserDataForm createFromParcel(Parcel in) {
            return new UpdateUserDataForm(in);
        }

        @Override
        public UpdateUserDataForm[] newArray(int size) {
            return new UpdateUserDataForm[size];
        }
    };

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
