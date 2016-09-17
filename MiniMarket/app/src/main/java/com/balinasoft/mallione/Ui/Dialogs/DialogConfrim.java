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
import com.balinasoft.mallione.networking.DispatcherFio;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestCouriers;
import com.balinasoft.mallione.networking.Response.ResponseCourier;
import com.balinasoft.mallione.networking.Response.ResponseDispatchers;

import java.util.HashMap;

/**
 * Created by Anton Kolotsey on 26.07.2016.
 */
public class DialogConfrim extends BaseDialog{

    String shop_id;

    public DialogConfrim setTypeUserListener(TypeUserListener typeUserListener) {
        this.typeUserListener = typeUserListener;
        return this;
    }

    public interface TypeUserListener{
        void onDispatcher(DispatcherFio dispatcher);
        void onCourier(Courier courier);
    }
    TypeUserListener typeUserListener;
    UserListener<?extends User> userListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if(getArguments()!=null){
            shop_id=getArguments().getString("shop_id");
        }
        userListener=(UserListener<?extends User>)getActivity();
    }
    RequestCouriers requestCouriers=new RequestCouriers();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.dialog_confrim,null);
        initView(v);
        final HashMap<String,String> hashMap=new HashMap<String, String>();
        hashMap.put("shop_id",shop_id);
        requestCouriers.setFull_info("0");
        requestCouriers.setSession_id(userListener.getUser().getSession_id());
        requestCouriers.setUser_id(String.valueOf(userListener.getUser().getId()));
        btnCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiFactory.getService().couriers(requestCouriers).enqueue(new MyCallbackWithMessageError<ResponseCourier>() {
                    @Override
                    public void onData(ResponseCourier data) {
                        customNumberPicker.setTitles(data.getResult());

                    }

                    @Override
                    public void onRequestEnd() {

                    }
                });
            }
        });
        btnDispatcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ApiFactory.getService().dispatcher(hashMap).enqueue(new MyCallbackWithMessageError<ResponseDispatchers>() {
                    @Override
                    public void onData(ResponseDispatchers data) {
                        customNumberPicker.setTitles(data.getResult());
                    }

                    @Override
                    public void onRequestEnd() {

                    }
                });
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(customNumberPicker.getTitles().size()==1){
                  Title title=  customNumberPicker.getTitles().get(0);
                    if(title.getClass()==Courier.class){
                        typeUserListener.onCourier((Courier) title);
                    }
                    if(title.getClass()==DispatcherFio.class){
                        typeUserListener.onDispatcher((DispatcherFio)title);
                    }
                    dismiss();
                }else
                if(typeUserListener!=null) {

                    if (courier != null) {

                        typeUserListener.onCourier(courier);
                        dismiss();
                    }
                    if(dispatcher!=null){
                        typeUserListener.onDispatcher(dispatcher);
                        dismiss();
                    }

                }

            }
        });
        return v;
    }
    Courier courier;
    DispatcherFio dispatcher;
    CustomNumberPicker customNumberPicker;
    Button btnCourier,btnDispatcher,btnOk;
    private void initView(View v) {
        btnCourier=(Button)v.findViewById(R.id.dialogConfrim_btnCourier);
        btnDispatcher=(Button)v.findViewById(R.id.dialogConfrim_btnDispatcher);
        btnOk=(Button)v.findViewById(R.id.dialogConfrim_btnOk);
        customNumberPicker=(CustomNumberPicker)v.findViewById(R.id.dialogConfrim_numPicker);
        customNumberPicker.setSelectListener(new CustomNumberPicker.SelectListener() {
            @Override
            public void onSelect(Title title) {
                if(title.getClass() == Courier.class){
                    courier=(Courier) title;
                }
                if(title.getClass() == DispatcherFio.class){
                    dispatcher=(DispatcherFio) title;
                }
            }
        });
    }
}
