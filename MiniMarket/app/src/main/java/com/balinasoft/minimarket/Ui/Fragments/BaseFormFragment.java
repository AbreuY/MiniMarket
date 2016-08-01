package com.balinasoft.minimarket.Ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.Dialogs.DialogFragmentSelectTitle;
import com.balinasoft.minimarket.Utils.NetworkUtil;
import com.balinasoft.minimarket.interfaces.RegistrationFormListener;
import com.balinasoft.minimarket.interfaces.Title;
import com.balinasoft.minimarket.models.FormModel.RegistrationForm;
import com.balinasoft.minimarket.models.modelUsers.Buer;
import com.balinasoft.minimarket.models.modelUsers.City;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Response.ResponseCities;

/**
 * Created by Microsoft on 23.06.2016.
 */
public class BaseFormFragment extends Basefragment implements RegistrationFormListener{
    private EditText edTxNumberPhone;
    private EditText edTxPassword;
    private EditText edTxPasswordConfrim;
    private EditText edTxFio;
    private EditText edTxEmail;
    private EditText edTxCity;


    DialogFragmentSelectTitle dialogFragmentCity = new DialogFragmentSelectTitle();
    private City currentCity;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.form_base, null);
        initView(v);
        if(user!=null){
            edTxNumberPhone.setText(user.getPhone());
            edTxFio.setText(user.getFio());
            edTxEmail.setText(user.getEmail());
            edTxCity.setText(user.getCity().getCity());
        }
        dialogFragmentCity.setTitle(getString(R.string.city)).setSubTitle(getString(R.string.selectCity));
        edTxCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getService().cities().enqueue(new MyCallback<ResponseCities>() {
                    @Override
                    public void onData(ResponseCities data) {
                        dialogFragmentCity.setItems(data.getResult());
                    }

                    @Override
                    public void onRequestEnd() {

                    }
                });
                dialogFragmentCity.show(getFragmentManager(), "fsdf");
            }
        });

        dialogFragmentCity.setOnClickButtonsListener(new DialogFragmentSelectTitle.OnClickButtonsListener() {
            @Override
            public void onClickOk(Title title) {
                edTxCity.setText(((City) title).getCity());
                currentCity = (City) title;
            }

            @Override
            public void onClickCancel() {

            }
        });
        if (savedInstanceState != null) {
            RegistrationForm form = savedInstanceState.getParcelable(RegistrationForm.class.getCanonicalName());
            currentCity = savedInstanceState.getParcelable(City.class.getCanonicalName());
            setRegistrationForm(form);

        }
        return v;
    }

    private void initView(View v) {
        edTxNumberPhone = (EditText) v.findViewById(R.id.buerFormFragment_edTxPhoneNumber);
        edTxPassword = (EditText) v.findViewById(R.id.buerFormFragment_edTxPassword);
        edTxPasswordConfrim = (EditText) v.findViewById(R.id.buerFormFragment_edTxConfrimPassword);
        edTxFio = (EditText) v.findViewById(R.id.buerFormFragment_edTxUserName);
        edTxEmail = (EditText) v.findViewById(R.id.buerFormFragment_edTxEmail);
        edTxCity = (EditText) v.findViewById(R.id.buerFormFragment_edTxCity);
        edTxCity.setKeyListener(null);


    }

    public boolean checkForm() {
        if (edTxNumberPhone.getText().toString().isEmpty()) {
            showToast(getString(R.string.enterPhone));
            startAnimation(edTxNumberPhone);
            return false;
        }
        if(edTxPassword.getText().length()<6){
            showToast(getString(R.string.passwordMore));
            startAnimation(edTxPassword);
            return false;
        }
        if (edTxPassword.getText().toString().isEmpty()) {
            showToast(getString(R.string.enterPassword));
            startAnimation(edTxPassword);
            return false;
        }
        if (edTxPasswordConfrim.getText().toString().isEmpty()) {
            showToast(getString(R.string.enterPasswordConfrim));
            startAnimation(edTxPasswordConfrim);
            return false;
        }
        if (edTxFio.getText().toString().isEmpty()) {
            showToast(getString(R.string.enterUserName));
            startAnimation(edTxFio);
            return false;
        }
        if (edTxEmail.getText().toString().isEmpty()) {
            showToast(getString(R.string.enterEmail));
            startAnimation(edTxEmail);
            return false;
        } else if (!NetworkUtil.isValidEmail(edTxEmail.getText())) {
            showToast(getString(R.string.enterCorrectEmail));
            startAnimation(edTxEmail);
            return false;
        }
        if (edTxCity.getText().toString().isEmpty()) {
            showToast(getString(R.string.enterCity));
            startAnimation(edTxCity);
            return false;
        }

        return true;
    }

    public RegistrationForm getFormBuer() {
        RegistrationForm form = new RegistrationForm();
        fillFormBuer(form);
        return form;
    }
    User user;
    public void setUiForm(User user){
         this.user=user;
    }
    private void fillFormBuer(RegistrationForm form){
        form.setPhone(edTxNumberPhone.getText().toString());
        form.setPassword(edTxPassword.getText().toString());
        form.setFio(edTxFio.getText().toString());
        form.setEmail(edTxEmail.getText().toString());

        form.setGroup_id(Buer.GROUP_ID);
        if (currentCity != null)
            form.setCity_id(currentCity.getCity_id());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(RegistrationForm.class.getCanonicalName(), getFormBuer());
        if (currentCity != null)
            outState.putParcelable(City.class.getCanonicalName(), currentCity);
        super.onSaveInstanceState(outState);
    }




    private void setRegistrationForm(RegistrationForm form) {
        edTxNumberPhone.setText(form.getPhone());
        edTxPassword.setText(form.getPassword());
        edTxPasswordConfrim.setText(form.getPassword());
        edTxFio.setText(form.getFio());
        edTxEmail.setText(form.getEmail());
        edTxCity.setText(currentCity.getCity());

    }

    @Override
    public void fillForm(RegistrationForm form) {
        fillFormBuer(form);
    }
}
