package com.balinasoft.mallione.Ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.interfaces.RegistrationFormListener;
import com.balinasoft.mallione.models.FormModel.RegistrationForm;
import com.balinasoft.mallione.models.modelUsers.Buer;

/**
 * Created by Microsoft on 24.06.2016.
 */
public class BuyerFormFragment extends Basefragment implements RegistrationFormListener {
    private EditText edTxAddress1;
    private EditText edTxAddress2;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.buyer_form,null);
        initView(v);
        if(buer!=null){
            if(!buer.getU_address_1().isEmpty()){
                edTxAddress1.setText(buer.getU_address_1());

            }
            if(!buer.getU_address_2().isEmpty()){
                edTxAddress2.setText(buer.getU_address_2());
            }
        }
        return v;
    }

    private void initView(View v) {
        edTxAddress1 = (EditText) v.findViewById(R.id.buerFormFragment_edTxAddress1);
        edTxAddress2 = (EditText) v.findViewById(R.id.buerFormFragment_edTxAddress2);
    }
    public boolean checkForm(){
        return true;
    }
    @Override
    public void fillForm(RegistrationForm form) {
        form.setU_address_1(edTxAddress1.getText().toString());
        form.setU_address_2(edTxAddress2.getText().toString());
    }
    Buer buer;
    public void setUiForm(Buer buer){
        this.buer=buer;
    }
}
