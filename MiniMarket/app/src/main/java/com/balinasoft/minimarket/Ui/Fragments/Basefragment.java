package com.balinasoft.minimarket.Ui.Fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.balinasoft.minimarket.networking.API;
import com.balinasoft.minimarket.networking.ApiFactory;
import com.balinasoft.minimarket.networking.MyCallback;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

/**
 * Created by Microsoft on 01.06.2016.
 */
public class Basefragment extends Fragment {

    public static API getService(){
       return ApiFactory.getService();
    }
    public boolean argumentIsEmpty(){
        return getArguments()==null;
    }
    @Override
    public void onResume() {
        super.onResume();
        MyCallback.setContext(getActivity());
    }
    static public void startAnimation(View view) {
        YoYo.with(Techniques.Tada).playOn(view);
    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

}
