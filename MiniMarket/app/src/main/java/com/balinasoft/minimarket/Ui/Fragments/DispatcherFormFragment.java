package com.balinasoft.minimarket.Ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.interfaces.RegistrationFormListener;
import com.balinasoft.minimarket.models.FormModel.RegistrationForm;
import com.balinasoft.minimarket.models.modelUsers.Dispatcher;

/**
 * Created by Microsoft on 28.07.2016.
 */
public class DispatcherFormFragment extends Basefragment implements RegistrationFormListener {
    EditText edTxOrganization,edTxInn,edTxAddress;
    private Dispatcher dispatcher;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.dispatcher_form,null);
        initView(v);
        if(dispatcher!=null){
            edTxOrganization.setText(dispatcher.getO_organization());
            edTxInn.setText(dispatcher.getO_inn());
            edTxAddress.setText(dispatcher.getO_address());
        }
        return v;
    }

    private void initView(View v) {
        edTxOrganization=(EditText)v.findViewById(R.id.dispatcherForm_edTxOrganization);
        edTxInn=(EditText)v.findViewById(R.id.dispatcherForm_edTxInn);
        edTxAddress=(EditText)v.findViewById(R.id.dispatcherForm_edTxAddress);
    }

    @Override
    public void fillForm(RegistrationForm form) {
        form.setO_address(edTxAddress.getText().toString());
        form.setO_inn(edTxInn.getText().toString());
        form.setO_organization(edTxOrganization.getText().toString());
    }

    public boolean checkForm() {

        if(edTxOrganization.getText().toString().isEmpty()){
            showToast(getString(R.string.enterOrganization));
            startAnimation(edTxOrganization);
            return false;
        }
        if(edTxInn.getText().toString().isEmpty()){
            showToast(getString(R.string.enterTINOrg));
            startAnimation(edTxInn);
            return false;
        }
        if(edTxAddress.getText().toString().isEmpty()){
            showToast(getString(R.string.enterAddress));
            startAnimation(edTxAddress);
            return false;
        }
        return true;
    }

    public void setUiForm(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }
}
