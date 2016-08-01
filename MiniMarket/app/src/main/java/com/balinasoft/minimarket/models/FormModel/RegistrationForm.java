package com.balinasoft.minimarket.models.FormModel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Microsoft on 22.06.2016.
 */
public class RegistrationForm extends BaseForm implements Parcelable {
    private String fio;
    private Integer group_id;
    private Integer city_id;
    private Integer m_supplier_id;
    private String u_address_1;
    private String u_address_2;
    private String c_driver_license;
    private String c_car_brand;
    private String c_car_number;
    private String c_car_color;
    private String s_address;
    private String s_inn;
    private String o_inn;
    private String o_address;
    private String o_organization;

    protected RegistrationForm(Parcel in) {
        fio = in.readString();
        u_address_1 = in.readString();
        u_address_2 = in.readString();
        c_driver_license = in.readString();
        c_car_brand = in.readString();
        c_car_number = in.readString();
        c_car_color = in.readString();
        s_address = in.readString();
        s_inn = in.readString();
        o_inn = in.readString();
        o_address = in.readString();
        o_organization = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(fio);
        dest.writeString(u_address_1);
        dest.writeString(u_address_2);
        dest.writeString(c_driver_license);
        dest.writeString(c_car_brand);
        dest.writeString(c_car_number);
        dest.writeString(c_car_color);
        dest.writeString(s_address);
        dest.writeString(s_inn);
        dest.writeString(o_inn);
        dest.writeString(o_address);
        dest.writeString(o_organization);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RegistrationForm> CREATOR = new Creator<RegistrationForm>() {
        @Override
        public RegistrationForm createFromParcel(Parcel in) {
            return new RegistrationForm(in);
        }

        @Override
        public RegistrationForm[] newArray(int size) {
            return new RegistrationForm[size];
        }
    };

    public static Creator<RegistrationForm> getCREATOR() {
        return CREATOR;
    }

    public Integer getC_dispatcher_id() {
        return c_dispatcher_id;
    }

    public void setC_dispatcher_id(Integer c_dispatcher_id) {
        this.c_dispatcher_id = c_dispatcher_id;
    }

    public void setCity_id(Integer city_id) {
        this.city_id = city_id;
    }

    public void setGroup_id(Integer group_id) {
        this.group_id = group_id;
    }

    public void setM_supplier_id(Integer m_supplier_id) {
        this.m_supplier_id = m_supplier_id;
    }

    public String getO_address() {
        return o_address;
    }

    public void setO_address(String o_address) {
        this.o_address = o_address;
    }

    public String getO_inn() {
        return o_inn;
    }

    public void setO_inn(String o_inn) {
        this.o_inn = o_inn;
    }

    public String getO_organization() {
        return o_organization;
    }

    public void setO_organization(String o_organization) {
        this.o_organization = o_organization;
    }

    public int getDispatcher_id() {
        return c_dispatcher_id;
    }

    public void setDispatcher_id(int dispatcher_id) {
        this.c_dispatcher_id = dispatcher_id;
    }

    private Integer c_dispatcher_id;

    public RegistrationForm() {

    }

    public int getM_supplier_id() {
        return m_supplier_id;
    }

    public void setM_supplier_id(int m_supplier_id) {
        this.m_supplier_id = m_supplier_id;
    }


    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }


    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getU_address_1() {
        return u_address_1;
    }

    public void setU_address_1(String u_address_1) {
        this.u_address_1 = u_address_1;
    }

    public String getU_address_2() {
        return u_address_2;
    }

    public void setU_address_2(String u_address_2) {
        this.u_address_2 = u_address_2;
    }

    public String getC_driver_license() {
        return c_driver_license;
    }

    public void setC_driver_license(String c_driver_license) {
        this.c_driver_license = c_driver_license;
    }

    public String getC_car_brand() {
        return c_car_brand;
    }

    public void setC_car_brand(String c_car_brand) {
        this.c_car_brand = c_car_brand;
    }

    public String getC_car_number() {
        return c_car_number;
    }

    public void setC_car_number(String c_car_number) {
        this.c_car_number = c_car_number;
    }

    public String getC_car_color() {
        return c_car_color;
    }

    public void setC_car_color(String c_car_color) {
        this.c_car_color = c_car_color;
    }

    public String getS_address() {
        return s_address;
    }

    public void setS_address(String s_address) {
        this.s_address = s_address;
    }

    public String getS_inn() {
        return s_inn;
    }

    public void setS_inn(String s_inn) {
        this.s_inn = s_inn;
    }
}
