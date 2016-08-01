package com.balinasoft.minimarket.Ui.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.balinasoft.minimarket.R;


public class TextDialog extends BaseDialog {

    public interface ClickListener<T>{
        void onClickPositive(T t);
        void onClickNegative();
        T getData();
    }

    public TextDialog setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    public TextDialog setText(String text) {
        this.text = text;
        return this;
    }

    private String text;
    private ClickListener clickListener;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.dialog_text,null);
        initView(v);
        if(text!=null){
            tvText.setText(text);
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null){
                    clickListener.onClickPositive(clickListener.getData());
                }
                dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null){
                    clickListener.onClickNegative();

                }
                dismiss();
            }
        });
        return v;
    }
    TextView tvText;
    Button btnOk,btnCancel;
    private void initView(View v) {
        tvText=(TextView)v.findViewById(R.id.dialogText_tvText);
        btnOk=(Button)v.findViewById(R.id.dialogText_tvBtnOk);
        btnCancel=(Button)v.findViewById(R.id.dialogText_tvBtnCancel);
    }
}
