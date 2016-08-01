package com.balinasoft.minimarket.Ui.Dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.CustomViews.CustomNumberPicker;
import com.balinasoft.minimarket.interfaces.Title;
import com.balinasoft.minimarket.interfaces.UserListener;
import com.balinasoft.minimarket.models.modelUsers.Courier;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.balinasoft.minimarket.networking.ApiFactory;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.RequestCouriers;
import com.balinasoft.minimarket.networking.Response.ResponseCourier;

import java.util.HashMap;

/**
 * Created by Anton Kolotsey on 01.08.2016.
 */
public class DialogSelectCouriers extends BaseDialog {
    public interface ClickListener{
        void onClickOk(String courier_id);
    }

    public DialogSelectCouriers setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }
    String courierId;
    ClickListener clickListener;
    UserListener<? extends User> userListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userListener=(UserListener<? extends User>)getActivity();
        requestCouriers.setFull_info("0");
        requestCouriers.setSession_id(userListener.getUser().getSession_id());
        requestCouriers.setUser_id(String.valueOf(userListener.getUser().getId()));
    }
    RequestCouriers requestCouriers=new RequestCouriers();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.dialog_select_couriers,null);
        initView(v);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customNumberPicker.getTitles().size()==1){
                    clickListener.onClickOk(String.valueOf(((Courier)customNumberPicker.getTitles().get(0)).getId()));
                    dismiss();
                }else
                if(clickListener!=null){
                    clickListener.onClickOk(courierId);
                }
                dismiss();
            }
        });


        ApiFactory.getService().couriers(requestCouriers).enqueue(new MyCallback<ResponseCourier>() {
            @Override
            public void onData(ResponseCourier data) {
                customNumberPicker.setTitles(data.getResult());
                customNumberPicker.setSelectListener(new CustomNumberPicker.SelectListener() {
                    @Override
                    public void onSelect(Title title) {
                        if(title.getClass()==Courier.class) {
                            Courier courier = (Courier) title;
                            if (courier != null)
                                courierId = String.valueOf(courier.getId());
                        }
                    }
                });

            }

            @Override
            public void onRequestEnd() {

            }
        });
        return v;
    }
    CustomNumberPicker customNumberPicker;
    Button btnOk;
    private void initView(View v) {
        customNumberPicker=(CustomNumberPicker)v.findViewById(R.id.dialogSelectCourier_numPicker);
        btnOk=(Button)v.findViewById(R.id.dialogSelectCourier_btnOk);
    }
}
