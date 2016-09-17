package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.balinasoft.mallione.interfaces.Swipeable;
import com.balinasoft.mallione.networking.API;
import com.balinasoft.mallione.networking.ApiFactory;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;

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
    private Swipeable swipeable;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            swipeable = (Swipeable) getActivity();
        }catch (ClassCastException e){

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MyCallbackWithMessageError.setContext(getActivity());
    }
    static public void startAnimation(View view) {
        YoYo.with(Techniques.Tada).playOn(view);
    }

    public void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
    public SwipyRefreshLayout getSwipe(){
        if(swipeable!=null){
            return swipeable.getSwipyRefreshLayout();
        }
        return null;
    }
    public boolean isPesentSwipe(){
        if(getSwipe()!=null){
            return true;
        }
        return false;
    }
}
