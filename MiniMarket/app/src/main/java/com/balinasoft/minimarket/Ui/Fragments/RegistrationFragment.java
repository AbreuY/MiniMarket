package com.balinasoft.minimarket.Ui.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.Dialogs.DialogFragmentSelectTitle;
import com.balinasoft.minimarket.interfaces.Title;
import com.balinasoft.minimarket.models.FormModel.BaseForm;
import com.balinasoft.minimarket.models.FormModel.RegistrationForm;
import com.balinasoft.minimarket.models.modelUsers.Buer;
import com.balinasoft.minimarket.models.modelUsers.Courier;
import com.balinasoft.minimarket.models.modelUsers.Dispatcher;
import com.balinasoft.minimarket.models.modelUsers.GroupUser;
import com.balinasoft.minimarket.models.modelUsers.Manager;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Response.ResponseRegistration;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by Microsoft on 13.06.2016.
 */
public class RegistrationFragment extends Basefragment {

    private GroupUser currentGroup;

    public interface RegistrationListener {
        void onSuccess(BaseForm baseForm);
    }

    public interface OnFinishListener {
        void onFinish();
    }

    public void setOnFinishListener(OnFinishListener onFinishListener) {
        this.onFinishListener = onFinishListener;
    }

    OnFinishListener onFinishListener;

    public void setRegistrationListener(RegistrationListener registrationListener) {
        this.registrationListener = registrationListener;
    }

    RegistrationListener registrationListener;
    Button btnRegistration, btnCustomer;
    TextView tvAccountType;
    ProgressBar progressBar;
    BaseFormFragment baseFormFragment = new BaseFormFragment();
    BuyerFormFragment buyerFormFragment = new BuyerFormFragment();
    ManagerFormFragment managerFormFragment = new ManagerFormFragment();
    CourierFormFragment courierFormFragment = new CourierFormFragment();
    DialogFragmentSelectTitle dialofFragmentGroupUser = new DialogFragmentSelectTitle();
    RegistrationForm registrationForm = new RegistrationForm();
    ArrayList<GroupUser> groupUsers = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupUsers.clear();
        groupUsers.add(new GroupUser(Manager.GROUP_ID, getString(R.string.manager)));
        groupUsers.add(new GroupUser(Courier.GROUP_ID, getString(R.string.courier)));
        groupUsers.add(new GroupUser(Dispatcher.GROUP_ID,getString(R.string.dispatcher)));

    }

    DispatcherFormFragment dispatcherFormFragment = new DispatcherFormFragment();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.registration_fragment_buer, null);
        initView(v);
        tvAccountType.setText(getString(R.string.buyer));
        getChildFragmentManager().beginTransaction().replace(R.id.registrationFragmentBuer_frame2, buyerFormFragment).commit();
        dialofFragmentGroupUser.setTitle(getString(R.string.groupUser)).setSubTitle(getString(R.string.selectUserGroup));

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForms()) {
                    progressBar.setVisibility(View.VISIBLE);
                    getService().registration(getForm(registrationForm)).enqueue(new MyCallback<ResponseRegistration>() {
                        @Override
                        public void onData(ResponseRegistration data) {
                            Toast.makeText(getActivity(), data.getResult().getAnswer(), Toast.LENGTH_SHORT).show();
                            if (registrationListener != null) {
                                registrationListener.onSuccess(registrationForm);
                                FirebaseAuth.getInstance().createUserWithEmailAndPassword(registrationForm.getEmail(), registrationForm.getPassword()).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                    }
                                });
                            }
                            if (onFinishListener != null) {
                                onFinishListener.onFinish();
                            }
                        }

                        @Override
                        public void onRequestEnd() {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });


        dialofFragmentGroupUser.setOnClickButtonsListener(new DialogFragmentSelectTitle.OnClickButtonsListener() {
            @Override
            public void onClickOk(Title title) {
                currentGroup = (GroupUser) title;
                tvAccountType.setText(currentGroup.getTitle());
                switch (currentGroup.getId()) {
                    case Courier.GROUP_ID:
                        if (managerFormFragment.isAdded()) {
                            getChildFragmentManager().beginTransaction().remove(managerFormFragment).commit();
                        }
                        if (buyerFormFragment.isAdded()) {
                            getChildFragmentManager().beginTransaction().remove(buyerFormFragment).commit();
                        }
                        getChildFragmentManager().beginTransaction().replace(R.id.registrationFragmentBuer_frame4, courierFormFragment).commit();
                        break;
                    case Manager.GROUP_ID:
                        if (buyerFormFragment.isAdded()) {
                            getChildFragmentManager().beginTransaction().remove(buyerFormFragment).commit();
                        }
                        if (courierFormFragment.isAdded()) {
                            getChildFragmentManager().beginTransaction().remove(courierFormFragment).commit();
                        }
                        getChildFragmentManager().beginTransaction().replace(R.id.registrationFragmentBuer_frame3, managerFormFragment).commit();
                        break;
                    case Buer.GROUP_ID:
                        if (managerFormFragment.isAdded())
                            getChildFragmentManager().beginTransaction().remove(managerFormFragment).commit();
                        if (courierFormFragment.isAdded())
                            getChildFragmentManager().beginTransaction().remove(courierFormFragment).commit();
                        getChildFragmentManager().beginTransaction().replace(R.id.registrationFragmentBuer_frame2, buyerFormFragment).commit();
                        break;
                    case Dispatcher.GROUP_ID:
                        if (managerFormFragment.isAdded())
                            getChildFragmentManager().beginTransaction().remove(managerFormFragment).commit();
                        if (courierFormFragment.isAdded())
                            getChildFragmentManager().beginTransaction().remove(courierFormFragment).commit();
                        if (buyerFormFragment.isAdded())
                            getChildFragmentManager().beginTransaction().remove(buyerFormFragment).commit();
                        getChildFragmentManager().beginTransaction().replace(R.id.registrationFragmentBuer_frame4, dispatcherFormFragment).commit();
                        break;
                }

            }

            @Override
            public void onClickCancel() {

            }
        });

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialofFragmentGroupUser.setTitles(groupUsers);
                dialofFragmentGroupUser.show(getFragmentManager(), "gsdfg");


            }
        });
        llLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickLoginListener!=null){
                    clickLoginListener.clickLogin();
                }
            }
        });
        return v;
    }
    public interface ClickLoginListener{
        void clickLogin();
    }

    public void setClickLoginListener(ClickLoginListener clickLoginListener) {
        this.clickLoginListener = clickLoginListener;
    }

    ClickLoginListener clickLoginListener;

    LinearLayout llLogin;
    private void initView(View v) {
        llLogin=(LinearLayout)v.findViewById(R.id.registrationFragmentBuer_llLogin);
        btnRegistration = (Button) v.findViewById(R.id.registrationFragmentBuer_btnRegistration);
        btnCustomer = (Button) v.findViewById(R.id.registrationFragmentBuer_btnCustomer);
        progressBar = (ProgressBar) v.findViewById(R.id.registrationFragmentBuer_progressBar);
        tvAccountType = (TextView) v.findViewById(R.id.registrationFragmentBuer_tvTypeAccount);
        progressBar.setVisibility(View.INVISIBLE);
        getChildFragmentManager().beginTransaction().replace(R.id.registrationFragmentBuer_frame1, baseFormFragment).commit();

    }

    public boolean checkForms() {
        if (baseFormFragment.checkForm()) {
            if (buyerFormFragment.isAdded()) {
                if (buyerFormFragment.checkForm()) {
                    return true;
                }
            }
            if (managerFormFragment.isAdded()) {
                if (managerFormFragment.checkForm()) {
                    return true;
                }
            }
            if (courierFormFragment.isAdded()) {
                if (courierFormFragment.checkForm()) {
                    return true;
                }
            }
            if(dispatcherFormFragment.isAdded()){
                if(dispatcherFormFragment.checkForm()){
                    return true;
                }
            }
        }
        return false;
    }

    private RegistrationForm getForm(RegistrationForm registrationForm) {
        if (baseFormFragment.isAdded()) {
            baseFormFragment.fillForm(registrationForm);
        }
        if (managerFormFragment.isAdded()) {
            managerFormFragment.fillForm(registrationForm);
        }
        if (courierFormFragment.isAdded()) {
            courierFormFragment.fillForm(registrationForm);
        }
        if(dispatcherFormFragment.isAdded()){
            dispatcherFormFragment.fillForm(registrationForm);
        }
        if (currentGroup != null) {
            registrationForm.setGroup_id(currentGroup.getId());
        }
        return registrationForm;
    }
}
