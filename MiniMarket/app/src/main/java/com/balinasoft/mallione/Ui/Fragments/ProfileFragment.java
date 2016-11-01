package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Utils.AuthManager;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.FormModel.UpdateUserDataForm;
import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.models.modelUsers.Courier;
import com.balinasoft.mallione.models.modelUsers.Dispatcher;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Response.ResponseAthorization;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Microsoft on 30.06.2016.
 */
public class ProfileFragment extends Basefragment {
    public static final String TAG = "ProfileFragment";
    ToolbarListener toolbarListener;
    Button btnSave;
    BaseFormFragment baseFormFragment = new BaseFormFragment();
    BuyerFormFragment buyerFormFragment = new BuyerFormFragment();
    ManagerFormFragment managerFormFragment = new ManagerFormFragment();
    CourierFormFragment courierFormFragment = new CourierFormFragment();
    UserListener<? extends User> userListener;
    ShowFragmentListener showFragmentListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarListener = (ToolbarListener) getActivity();
        userListener = (UserListener<? extends User>) getActivity();
        showFragmentListener=(ShowFragmentListener)getActivity();
        showFragmentListener.showFragmentToolBar(BlurbFragment.TAG,null);
        toolbarListener.setTittle(getString(R.string.profile));


    }

    UpdateUserDataForm form = new UpdateUserDataForm();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.profile, null);
        initView(v);
        if (userListener.getUser() != null) {
            baseFormFragment.setUiForm(userListener.getUser());

            if (userListener.getUser().getClass() == Buer.class) {
                buyerFormFragment.setUiForm((Buer) userListener.getUser());
            }

            if (userListener.getUser().getClass() == Courier.class) {
                courierFormFragment.setUiForm((Courier)userListener.getUser());
            }
            if (userListener.getUser().getClass() == Dispatcher.class) {
                dispatcherFormFragment.setUiForm((Dispatcher)userListener.getUser());
            }
        }
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    form.setSession_id(userListener.getUser().getSession_id());
                    baseFormFragment.fillForm(form);
                    if (buyerFormFragment.isAdded())
                        buyerFormFragment.fillForm(form);
                    if (courierFormFragment.isAdded())
                        courierFormFragment.fillForm(form);
                    if (dispatcherFormFragment.isAdded()) {
                        dispatcherFormFragment.fillForm(form);
                    }
                    form.setUser_id(String.valueOf(userListener.getUser().getId()));
                    getService().updateUserData(form).enqueue(new MyCallbackWithMessageError<ResponseAthorization>() {
                        @Override
                        public void onData(final ResponseAthorization data) {

                            new AuthManager(getActivity()).logout().setAdapter(new AuthManager.RetentiveAdapter() {
                                @Override
                                public User getUser() {
                                    return data.getResult().getUser();
                                }
                            }).save();
                            showToast(data.getResult().getAnswer());
                        }

                        @Override
                        public void onResponse(Call<ResponseAthorization> call, Response<ResponseAthorization> response) {
                            super.onResponse(call, response);
                        }

                        @Override
                        public void onRequestEnd() {

                        }
                    });
                }
            }
        });
        return v;
    }

    DispatcherFormFragment dispatcherFormFragment = new DispatcherFormFragment();

    private void initView(View v) {
        getChildFragmentManager().beginTransaction().replace(R.id.profileFragment_frame1, baseFormFragment).commit();
        if (userListener.getUser().getClass() == Buer.class)
            getChildFragmentManager().beginTransaction().replace(R.id.profileFragment_frame2, buyerFormFragment).commit();
        if (userListener.getUser().getClass() == Courier.class)
            getChildFragmentManager().beginTransaction().replace(R.id.profileFragment_frame3, courierFormFragment).commit();
        if (userListener.getUser().getClass() == Dispatcher.class) {
            getChildFragmentManager().beginTransaction().replace(R.id.profileFragment_frame4, dispatcherFormFragment).commit();
        }
        btnSave = (Button) v.findViewById(R.id.profileFragment_btnSave);
    }

    private boolean checkForm() {
        if (baseFormFragment.checkForm()) {
            if (buyerFormFragment.isAdded() && buyerFormFragment.checkForm()) {
                return true;
            }
            if (courierFormFragment.isAdded() && courierFormFragment.checkForm()) {
                return true;
            }
            if (managerFormFragment.isAdded() && managerFormFragment.checkForm()) {
                return true;
            }
            if (dispatcherFormFragment.isAdded() && dispatcherFormFragment.checkForm()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarListener.closeToolbar();
    }
}
