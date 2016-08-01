package com.balinasoft.minimarket.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.interfaces.UserListener;
import com.balinasoft.minimarket.models.modelUsers.Courier;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.RequestCourierStatus;
import com.balinasoft.minimarket.networking.Response.BaseResponse;

/**
 * Created by Microsoft on 27.07.2016.
 */
public class NavHeaderCourierFragment extends Basefragment {
    RequestCourierStatus request=new RequestCourierStatus();
    UserListener<Courier> userListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userListener=(UserListener<Courier>)getActivity();
        request.setC_status(userListener.getUser().getC_status());
        request.setSession_id(userListener.getUser().getSession_id());
        request.setCourier_id(String.valueOf(userListener.getUser().getId()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nav_courier, null);
        intiView(v);

        if(userListener.getUser().getC_status().equals("1")){
            btnStatus.setText(getString(R.string.available));
        }else {
            btnStatus.setText(getString(R.string.notAvailable));
        }
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userListener.getUser().getC_status().equals("1")){
                    request.setC_status("0");
                    userListener.getUser().setC_status(request.getC_status());
                    btnStatus.setText(getString(R.string.notAvailable));
                }else {
                    btnStatus.setText(getString(R.string.available));
                    request.setC_status("1");
                    userListener.getUser().setC_status(request.getC_status());
                }
                getService().setCourierStatus(request).enqueue(new MyCallback<BaseResponse>() {
                    @Override
                    public void onData(BaseResponse data) {

                    }

                    @Override
                    public void onRequestEnd() {

                    }
                });
            }
        });
        return v;
    }

    Button btnStatus;
    NavHeaderFragment headerFragment=new NavHeaderFragment();
    private void intiView(View v) {
        btnStatus = (Button) v.findViewById(R.id.navCourier_btnStatus);
        getChildFragmentManager().beginTransaction().replace(R.id.navCourier_frame,headerFragment).commit();

    }
    public void setLoginListener(NavHeaderFragment.LoginListener loginListener){
        headerFragment.setLoginListener(loginListener);
    }
}
