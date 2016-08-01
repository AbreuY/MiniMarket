package com.balinasoft.minimarket.Ui.Dialogs;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.balinasoft.minimarket.Implementations.Day;
import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.CustomViews.CustomNumberPicker;
import com.balinasoft.minimarket.interfaces.Title;
import com.balinasoft.minimarket.interfaces.UserListener;
import com.balinasoft.minimarket.models.Data;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.balinasoft.minimarket.networking.ApiFactory;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.RequestAddRecord;
import com.balinasoft.minimarket.networking.Request.RequestItemTime;
import com.balinasoft.minimarket.networking.Response.ResponseAnswer;
import com.balinasoft.minimarket.networking.Response.ResponseItemTime;
import com.balinasoft.minimarket.networking.Result.ResultTime;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Microsoft on 11.07.2016.
 */
public class DialogSelectTime extends BaseDialog {
    TextView tvTitle, tvSubTitle;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTvSubTitle(TextView tvSubTitle) {
        this.tvSubTitle = tvSubTitle;
    }

    String title, subTitle;
    Button btnOk, btnCancel;
    CustomNumberPicker numPickerDays, numPickerHours;
    Data currentDay;
    RequestAddRecord requestAddRecord=new RequestAddRecord();
    public void setUserUserListener(UserListener<User> userUserListener) {
        this.userUserListener = userUserListener;
    }

    UserListener<? extends User> userUserListener;

    public DialogSelectTime setProductListener(DialogItemFragment.ProductListener productListener) {
        this.productListener = productListener;
        return this;
    }


    DialogItemFragment.ProductListener productListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        userUserListener=(UserListener<?extends User>)getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestItemTime = new RequestItemTime(productListener.getProduct().getId(), userUserListener.getUser().getSession_id(), userUserListener.getUser().getId());
    }

    RequestItemTime requestItemTime;
    HashMap<Long, ArrayList<Data>> hashMap = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_select_time, null);
        initView(v);

        if (title != null && !title.isEmpty()) {
            tvTitle.setText(title);
        }
        if (subTitle != null && !title.isEmpty()) {
            tvSubTitle.setText(subTitle);
        }
        numPickerDays.setSelectListener(new CustomNumberPicker.SelectListener() {
            @Override
            public void onSelect(Title title) {
                Day day = (Day) title;
//                if () {
                if(hashMap.get(day.getDay())==null || hashMap.get(day.getDay()).size()==0){
                    currentDay=null;
                }
                numPickerHours.setTitles(hashMap.get(day.getDay()));
                // }

            }
        });
        numPickerDays.setFistItemListener(new CustomNumberPicker.FistItemListener() {
            @Override
            public void onFistItem() {
                requestItemTime.incrementOffset();
                addItems();
            }
        });
        numPickerHours.setSelectListener(new CustomNumberPicker.SelectListener() {
            @Override
            public void onSelect(Title title) {
                if (title instanceof Data && !title.getTitle().equals(" ")) {
                    currentDay = (Data) title;
                } else currentDay = null;
            }
        });
        addItems();
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentDay == null) {
                    Toast.makeText(getActivity(), getString(R.string.noFreeTime), Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    ApiFactory.getService().addRecord
                            (new RequestAddRecord(userUserListener.getUser().getSession_id()
                                    ,currentDay.getTime()/1000,currentDay.getId(),userUserListener.getUser().getId())).enqueue(new MyCallback<ResponseAnswer>() {
                        @Override
                        public void onData(ResponseAnswer data) {
                            Toast.makeText(getActivity(),data.getResult().getAnswer(),Toast.LENGTH_SHORT).show();
                            dismiss();
                        }

                        @Override
                        public void onRequestEnd() {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    ProgressBar progressBar;

    private void initView(View v) {
        tvTitle = (TextView) v.findViewById(R.id.dialogSelectTime_tvTitle);
        tvSubTitle = (TextView) v.findViewById(R.id.dialogSelectTime_tvSubTitle);
        btnOk = (Button) v.findViewById(R.id.dialogSelectTime_btnOk);
        btnCancel = (Button) v.findViewById(R.id.dialogSelectTime_btnCancel);
        numPickerDays = (CustomNumberPicker) v.findViewById(R.id.dialogSelectTime_numPickerDays);
        numPickerHours = (CustomNumberPicker) v.findViewById(R.id.dialogSelectTime_numPickerHours);


        progressBar = (ProgressBar) v.findViewById(R.id.dialogSelectTime_progressBar);
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void addItems() {
        ApiFactory.getService().itemTime(requestItemTime).enqueue(new MyCallback<ResponseItemTime>() {
            @Override
            public void onData(final ResponseItemTime data) {
                ArrayList<ResultTime> results = data.getResult();
                ArrayList<Title> days = new ArrayList<Title>();
                for (final ResultTime resultTime : results) {
                    hashMap.put(resultTime.getDay(), resultTime.getData());
                    days.add(new Day(resultTime.getDay()));
                }
                numPickerDays.addTitles(days);

            }

            @Override
            public void onRequestEnd() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
