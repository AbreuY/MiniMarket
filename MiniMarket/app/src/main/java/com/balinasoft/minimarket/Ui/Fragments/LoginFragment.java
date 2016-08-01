package com.balinasoft.minimarket.Ui.Fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.models.FormModel.LoginForm;
import com.balinasoft.minimarket.networking.Services.RegistrationIntentService;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Microsoft on 23.06.2016.
 */
public class LoginFragment extends Basefragment {
    public interface ButtonsListener {
        void onClickLogin(LoginForm loginForm);

        void onClickForgotPassword();

        void onClickRegister();
    }

    public ButtonsListener getButtonsListener() {
        return buttonsListener;
    }

    public void setButtonsListener(ButtonsListener buttonsListener) {
        this.buttonsListener = buttonsListener;
    }

    ButtonsListener buttonsListener = new ButtonsListener() {
        @Override
        public void onClickLogin(LoginForm loginForm) {

        }

        @Override
        public void onClickForgotPassword() {

        }

        @Override
        public void onClickRegister() {

        }
    };
    Button btnLogin;
    EditText edTxNumberPhone, edTxPassword;
    TextView tvForgotPassword;
    LinearLayout linearLRegister;
    String token;
    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            token=intent.getStringExtra(RegistrationIntentService.ACTION_TOKEN);
        }
    };
    IntentFilter intentFilter=new IntentFilter(RegistrationIntentService.ACTION_TOKEN);
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.login_fragment, null);
        initView(v);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    buttonsListener.onClickLogin(getForm());
                }
            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsListener.onClickForgotPassword();
            }
        });
        linearLRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonsListener.onClickRegister();
            }
        });
        return v;
    }

    private void initView(View v) {
        btnLogin = (Button) v.findViewById(R.id.loginFragment_btnLogin);
        edTxPassword = (EditText) v.findViewById(R.id.loginFragment_edTxPassword);
        edTxNumberPhone = (EditText) v.findViewById(R.id.loginFragment_edTxPhone);
        tvForgotPassword = (TextView) v.findViewById(R.id.loginFragment_tvForgotPassword);
        linearLRegister = (LinearLayout) v.findViewById(R.id.loginFragment_linearLayoutRegisterNow);
    }

    public boolean checkForm() {
        if (edTxNumberPhone.getText().toString().isEmpty()) {
            startAnimation(edTxNumberPhone);
            showToast(getString(R.string.enterPhone));
            return false;
        }
        if (edTxPassword.getText().toString().isEmpty()) {
            startAnimation(edTxPassword);
            showToast(getString(R.string.enterPassword));
            return false;
        }
        return true;
    }

    private LoginForm getForm() {
        LoginForm loginForm = new LoginForm();
        loginForm.setPassword(edTxPassword.getText().toString());
        loginForm.setPhone(edTxNumberPhone.getText().toString());
        loginForm.setToken(FirebaseInstanceId.getInstance().getToken());
        return loginForm;
    }

    public void setPassword(String password) {
        edTxPassword.setText(password);
    }

    public void setPhone(String phone) {
        edTxNumberPhone.setText(phone);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver,intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(broadcastReceiver);
    }

}
