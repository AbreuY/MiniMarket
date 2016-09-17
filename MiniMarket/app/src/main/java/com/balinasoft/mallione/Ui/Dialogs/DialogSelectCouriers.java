package com.balinasoft.mallione.Ui.Dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.CustomViews.CustomNumberPicker;
import com.balinasoft.mallione.interfaces.Title;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.modelUsers.Courier;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.ApiFactory;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestCouriers;
import com.balinasoft.mallione.networking.Response.ResponseCourier;

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


        ApiFactory.getService().couriers(requestCouriers).enqueue(new MyCallbackWithMessageError<ResponseCourier>() {
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
