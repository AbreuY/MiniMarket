package com.balinasoft.mallione.Ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Dialogs.DialogFragmentSelectTitle;
import com.balinasoft.mallione.interfaces.RegistrationFormListener;
import com.balinasoft.mallione.interfaces.Title;
import com.balinasoft.mallione.models.FormModel.RegistrationForm;
import com.balinasoft.mallione.models.modelUsers.Manager;
import com.balinasoft.mallione.models.modelUsers.Supplier;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Response.ResponseSuppliers;


public class ManagerFormFragment extends Basefragment implements RegistrationFormListener {
    EditText edTxSupplier;
    DialogFragmentSelectTitle dialogFragmentSelectSupplier = new DialogFragmentSelectTitle();
    Supplier currentSupplier;
    private Manager manager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.form_manager, null);
        initView(v);
        if(manager!=null){

        }
        dialogFragmentSelectSupplier.setTitle(getString(R.string.TINTaxpayer)).setSubTitle(getString(R.string.selectSupplier));
        dialogFragmentSelectSupplier.setOnClickButtonsListener(new DialogFragmentSelectTitle.OnClickButtonsListener() {
            @Override
            public void onClickOk(Title title) {
                currentSupplier = (Supplier) title;
                edTxSupplier.setText(title.getTitle());
            }

            @Override
            public void onClickCancel() {

            }
        });
        edTxSupplier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogFragmentSelectSupplier.show(getFragmentManager(), "fdsf");
                getService().getSuppliers().enqueue(new MyCallbackWithMessageError<ResponseSuppliers>() {
                    @Override
                    public void onData(ResponseSuppliers data) {
                        dialogFragmentSelectSupplier.setItems(data.getResult());
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
        edTxSupplier = (EditText) v.findViewById(R.id.formManager_edTxSupplier);
        edTxSupplier.setKeyListener(null);
    }

    public boolean checkForm() {
        if (edTxSupplier.getText().toString().isEmpty()) {
            showToast(getString(R.string.enterSupplier));
            startAnimation(edTxSupplier);
            return false;
        }
        return true;
    }

    @Override
    public void fillForm(RegistrationForm form) {
        if (currentSupplier != null)
            form.setM_supplier_id(currentSupplier.getSupplier_id());
    }

    public void setUiForm(Manager manager) {
        this.manager = manager;
    }
}
