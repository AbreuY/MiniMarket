package com.balinasoft.mallione.Ui.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.balinasoft.mallione.R;

/**
 * Created by Microsoft on 25.07.2016.
 */
public class EditTextDialog extends BaseDialog {
    public interface ClickListener{
        void onClick(String message);
    }

    public EditTextDialog setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
        return this;
    }

    ClickListener clickListener;

    public EditTextDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    String title;

    public EditTextDialog setTitleBtn(String titleBtn) {
        this.titleBtn = titleBtn;
        return this;
    }

    String titleBtn;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.dialog_edit_text,null);
        initView(v);
        if(title!=null && !title.isEmpty()){
            tvTitle.setText(title);
        }
        if(titleBtn!=null && !titleBtn.isEmpty()){
            btn.setText(titleBtn);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickListener!=null){
                    dismiss();
                    clickListener.onClick(edTx.getText().toString());
                }
            }
        });
        return v;
    }
    TextView tvTitle;
    EditText edTx;
    Button btn;
    private void initView(View v) {
        tvTitle=(TextView)v.findViewById(R.id.dialogEdTx_tvTitle);
        edTx=(EditText)v.findViewById(R.id.dialogEdTx_edTx);
        btn=(Button)v.findViewById(R.id.dialogEdTx_btn);
    }
}
