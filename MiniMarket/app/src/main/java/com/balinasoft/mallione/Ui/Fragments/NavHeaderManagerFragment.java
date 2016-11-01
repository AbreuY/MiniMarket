package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.modelUsers.Manager;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestCourierStatus;
import com.balinasoft.mallione.networking.Response.BaseResponse;

/**
 * Created by Microsoft on 27.07.2016.
 */
public class NavHeaderManagerFragment extends Basefragment {
    RequestCourierStatus request = new RequestCourierStatus();
    UserListener<Manager> userListener;
    SharedPreferences sharedPreferences;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userListener = (UserListener<Manager>) getContext();
        sharedPreferences = getActivity().getPreferences(getActivity().MODE_PRIVATE);
//        if (sharedPreferences.getString("c_status", "").equals("1")) {
//            request.setC_status("0");
//        } else if (sharedPreferences.getString("c_status", "").equals("0")) {
//            request.setC_status("1");
//        }
//        try {
        request.setC_status(userListener.getUser().getC_status());
        request.setSession_id(userListener.getUser().getSession_id());
        request.setUserId(String.valueOf(userListener.getUser().getId()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nav_courier, null);
        intiView(v);
        btnStatus.setBackgroundResource(R.drawable.bg_empty);

        sharedPreferences = getActivity().getPreferences(getActivity().MODE_PRIVATE);


        if (sharedPreferences.getString("c_status", "").equals("1")) {
            btnStatus.setText(getString(R.string.available));
            btnStatus.setTextColor(Color.GREEN);
            request.setC_status("0");
        } else if (sharedPreferences.getString("c_status", "").equals("0")) {
            btnStatus.setText(getString(R.string.notAvailable));
            btnStatus.setTextColor(Color.RED);
            request.setC_status("1");
        } else {
            btnStatus.setTextColor(Color.BLACK);
            request.setC_status("0");
        }


        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getService().setCourierStatus(request).enqueue(new MyCallbackWithMessageError<BaseResponse>() {
                    @Override
                    public void onData(BaseResponse data) {
                        if (request.getC_status().equals("0")) { //если отправили оффлайн
                            //if (userListener.getUser().getC_status().equals("1")) { //если мы были онлайн
                            btnStatus.setText(getString(R.string.notAvailable));
                            btnStatus.setTextColor(Color.RED); //то нарисуем оффлайн
                            //  }
                            userListener.getUser().setC_status("0"); //МЫ ОФФЛАЙН
                            request.setC_status("1"); //на будущее, если мы захотим отправить запрос на изменение статуса
                            sharedPreferences = getActivity().getPreferences(getActivity().MODE_PRIVATE);
                            Editor ed = sharedPreferences.edit();
                            ed.putString("c_status", userListener.getUser().getC_status());
                            ed.commit();
                        } else if (request.getC_status().equals("1")) { //если отправили онлайн
                            // if (userListener.getUser().getC_status().equals("0")) { //если мы были оффлайн
                            btnStatus.setText(getString(R.string.available));
                            btnStatus.setTextColor(Color.GREEN); //то нарисуем онлайн
                            //}
                            userListener.getUser().setC_status("1"); //МЫ ОНЛАЙН
                            request.setC_status("0"); //на будущее, если мы захотим отправить запрос на изменение статуса
                            sharedPreferences = getActivity().getPreferences(getActivity().MODE_PRIVATE);
                            Editor ed = sharedPreferences.edit();
                            ed.putString("c_status", userListener.getUser().getC_status());
                            ed.commit();
                        }
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

    NavHeaderFragment headerFragment = new NavHeaderFragment();

    private void intiView(View v) {
        btnStatus = (Button) v.findViewById(R.id.navCourier_btnStatus);
        getChildFragmentManager().beginTransaction().replace(R.id.navCourier_frame, headerFragment).commit();

    }

    public void setLoginListener(NavHeaderFragment.LoginListener loginListener) {
        headerFragment.setLoginListener(loginListener);
    }
}
