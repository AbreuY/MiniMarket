package com.balinasoft.minimarket.Ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.Dialogs.DialogFragmentSelectTitle;
import com.balinasoft.minimarket.interfaces.RegistrationFormListener;
import com.balinasoft.minimarket.interfaces.Title;
import com.balinasoft.minimarket.models.FormModel.RegistrationForm;
import com.balinasoft.minimarket.models.modelUsers.Courier;
import com.balinasoft.minimarket.models.modelUsers.Dispatcher;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Response.ResponseDispatchers;

/**
 * Created by Microsoft on 24.06.2016.
 */
public class CourierFormFragment extends Basefragment implements RegistrationFormListener {
    EditText edTxDriverLincense, edTxCarBrand, edTxCarNumber, edTxCarColor, edTxTINTaxpayer;
    Dispatcher currentDispatcher;
    DialogFragmentSelectTitle dialogFragmentSelectDispatcher=new DialogFragmentSelectTitle();
    private Courier courier;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogFragmentSelectDispatcher.setTitle(getString(R.string.TINTaxpayer)).setSubTitle(getString(R.string.select)+" "+getString(R.string.TINTaxpayer));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.form_courier, null);
        initView(v);
        if(courier!=null){
            edTxCarBrand.setText(courier.getC_car_brand());
            edTxDriverLincense.setText(courier.getC_driver_license());
            edTxCarNumber.setText(courier.getC_car_number());
            edTxCarColor.setText(courier.getC_car_color());
        }
        dialogFragmentSelectDispatcher.setOnClickButtonsListener(new DialogFragmentSelectTitle.OnClickButtonsListener() {
            @Override
            public void onClickOk(Title title) {
                currentDispatcher=(Dispatcher)title;
                edTxTINTaxpayer.setText(title.getTitle());
            }

            @Override
            public void onClickCancel() {

            }
        });
        edTxTINTaxpayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragmentSelectDispatcher.show(getFragmentManager(),"dsgfd");
                getService().dispatchers().enqueue(new MyCallback<ResponseDispatchers>() {
                    @Override
                    public void onData(ResponseDispatchers data) {
                        dialogFragmentSelectDispatcher.setItems(data.getResult());
                    }

                    @Override
                    public void onRequestEnd() {

                    }
                });
            }
        });
        return v;
    }

    private void initView(View v) {
        edTxDriverLincense = (EditText) v.findViewById(R.id.formCourie_edTxDriverLincense);
        edTxCarBrand = (EditText) v.findViewById(R.id.formCourie_edTxCarBrand);
        edTxCarNumber = (EditText) v.findViewById(R.id.formCourie_edTxCarNumber);
        edTxCarColor = (EditText) v.findViewById(R.id.formCourie_edTxCarColor);
        edTxTINTaxpayer = (EditText) v.findViewById(R.id.formCourie_edTxTINTaxpayer);
        edTxTINTaxpayer.setKeyListener(null);
    }

    public boolean checkForm() {
        return true;
    }

    @Override
    public void fillForm(RegistrationForm form) {
        form.setC_driver_license(edTxDriverLincense.getText().toString());
        form.setC_car_brand(edTxCarBrand.getText().toString());
        form.setC_car_number(edTxCarNumber.getText().toString());
        form.setC_car_color(edTxCarColor.getText().toString());
        if(currentDispatcher!=null){
            form.setDispatcher_id(currentDispatcher.getDispatcher_id());
        }

    }

    public void setUiForm(Courier courier) {
        this.courier = courier;
    }
}
