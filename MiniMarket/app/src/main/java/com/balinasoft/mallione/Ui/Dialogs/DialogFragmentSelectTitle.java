package com.balinasoft.mallione.Ui.Dialogs;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.CustomViews.CustomNumberPicker;
import com.balinasoft.mallione.interfaces.Title;

import java.util.ArrayList;


public class DialogFragmentSelectTitle extends BaseDialog {

    public ArrayList<? extends Title> getTitels() {
        return this.titles;
    }

    public interface OnSelectItemListener {
        void onItem(Title title);
    }

    public interface OnClickButtonsListener {
        void onClickOk(Title title);

        void onClickCancel();
    }


    public OnSelectItemListener getOnSelectItemListener() {
        return onSelectItemListener;
    }

    public void setOnSelectItemListener(OnSelectItemListener onSelectItemListener) {
        this.onSelectItemListener = onSelectItemListener;
    }

    OnSelectItemListener onSelectItemListener = new OnSelectItemListener() {
        @Override
        public void onItem(Title title) {

        }
    };

    public OnClickButtonsListener getOnClickButtonsListener() {
        return onClickButtonsListener;
    }

    public DialogFragmentSelectTitle setOnClickButtonsListener(OnClickButtonsListener onClickButtonsListener) {
        this.onClickButtonsListener = onClickButtonsListener;
        return this;
    }

    OnClickButtonsListener onClickButtonsListener = new OnClickButtonsListener() {
        @Override
        public void onClickOk(Title title) {

        }

        @Override
        public void onClickCancel() {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    CustomNumberPicker customNumberPicker;
    Button btnOk, btnCancel;
    TextView tvTitle, tvSubTitle;

    public DialogFragmentSelectTitle setTitle(String title) {
        this.title = title;
        return this;
    }

    public DialogFragmentSelectTitle setSubTitle(String subTitle) {
        this.subTitle = subTitle;
        return this;
    }

    String title, subTitle;

    public boolean isWheel() {
        return wheel;
    }

    public void setWheel(boolean wheel) {
        this.wheel = wheel;
    }

    boolean wheel = false;

    public void setTitles(ArrayList<? extends Title> titles) {
        this.titles = titles;
    }

    ArrayList<? extends Title> titles=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.dialog_fragment_select_city, null);
        initView(v);
        if(titles.size()>0){
            setItems(titles);
        }
        setVisibleTextView(title,tvTitle);
        setVisibleTextView(subTitle,tvSubTitle);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonsListener.onClickOk(customNumberPicker.getSelectValue());
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickButtonsListener.onClickCancel();
                dismiss();
            }
        });
        customNumberPicker.setSelectListener(new CustomNumberPicker.SelectListener() {
            @Override
            public void onSelect(Title title) {
                onSelectItemListener.onItem(title);
            }
        });
        return v;
    }

    private void initView(View v) {
        customNumberPicker = (CustomNumberPicker) v.findViewById(R.id.dialogFragmentSelectCity_picker);
        btnOk = (Button) v.findViewById(R.id.dialogFragmentSelectCity_btnOk);
        btnCancel = (Button) v.findViewById(R.id.dialogFragmentSelectCity_btnCancel);
        tvTitle = (TextView) v.findViewById(R.id.dialogFragmentSelectCity_tvTitle);
        tvSubTitle = (TextView) v.findViewById(R.id.dialogFragmentSelectCity_subTitle);
    }

    public void setItems(ArrayList<? extends Title> titles) {
            customNumberPicker.setTitles(titles);
            customNumberPicker.setSelectorWheel(isWheel());


    }
    private void setVisibleTextView(String s,TextView textView){
        if(s!=null && !s.isEmpty()){
            textView.setText(s);
        }else textView.setVisibility(View.GONE);
    }
}
