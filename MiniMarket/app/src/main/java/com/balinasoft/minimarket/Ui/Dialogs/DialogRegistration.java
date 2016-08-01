package com.balinasoft.minimarket.Ui.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.Fragments.RegistrationFragment;

/**
 * Created by Microsoft on 23.06.2016.
 */
public class DialogRegistration extends FullScreenDialog {

    public void setRegistrationListener(RegistrationFragment.RegistrationListener registrationListener) {

        this.registrationListener = registrationListener;
    }

    public void setIvClose(ImageView ivClose) {
        this.ivClose = ivClose;
    }

    RegistrationFragment.RegistrationListener registrationListener;
    ImageView ivClose;
    RegistrationFragment registrationFragment = new RegistrationFragment();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (registrationListener != null) {
            registrationFragment.setRegistrationListener(registrationListener);
        }
        registrationFragment.setOnFinishListener(new RegistrationFragment.OnFinishListener() {
            @Override
            public void onFinish() {
                dismiss();
            }
        });
        registrationFragment.setClickLoginListener(new RegistrationFragment.ClickLoginListener() {
            @Override
            public void clickLogin() {
               dismiss();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        View v = inflater.inflate(R.layout.dialog_registration, null);
        initView(v);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getChildFragmentManager().beginTransaction().replace(R.id.dialogRegistration_frame, registrationFragment).commit();
        return v;
    }

    private void initView(View v) {
        ivClose = (ImageView) v.findViewById(R.id.dialogRegistration_ivClose);
    }
}
