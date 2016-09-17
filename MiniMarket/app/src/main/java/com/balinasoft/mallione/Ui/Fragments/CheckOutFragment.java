package com.balinasoft.mallione.Ui.Fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.balinasoft.mallione.Implementations.Basket;
import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Dialogs.DialogFragmentSelectTitle;
import com.balinasoft.mallione.interfaces.Title;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.ProductItems.BasketProductItem;
import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.networking.ApiFactory;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestOrder;
import com.balinasoft.mallione.networking.Response.ResponseAnswer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import info.hoang8f.android.segmented.SegmentedGroup;

/**
 * Created by Microsoft on 15.07.2016.
 */
public class CheckOutFragment extends Basefragment {
    public static final String TAG="CheckOutFragment";
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    EditText edTxAddress, edTxDate, edTxDescription, edTxTime;
    ImageView ivBtnAddress;
    TextView tvPrice;
    SegmentedGroup group;
    Button btnConfrim;
    RequestOrder requestOrder;
    DialogFragmentSelectTitle dialogFragmentSelectTitle = new DialogFragmentSelectTitle().setOnClickButtonsListener(new DialogFragmentSelectTitle.OnClickButtonsListener() {
        @Override
        public void onClickOk(Title title) {
            edTxAddress.setText(title.getTitle());
            requestOrder.setAddress(title.getTitle());
        }

        @Override
        public void onClickCancel() {

        }
    });
    UserListener<Buer> userListener;
    ToolbarListener toolbarListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userListener=(UserListener<Buer>)getActivity();
        toolbarListener=(ToolbarListener)getActivity();
        toolbarListener.setTittle(getString(R.string.order));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null) {
            ArrayList<BasketProductItem> items = getArguments().getParcelableArrayList("ProductsOrder");
            requestOrder = new RequestOrder(userListener.getUser(), items);
        }
        ArrayList<Title> titles = new ArrayList<Title>();

        if (userListener.getUser().getU_address_1() != null && !userListener.getUser().getU_address_1().isEmpty())
            titles.add(new Title() {
                @Override
                public String getTitle() {
                    return userListener.getUser().getU_address_1();
                }
            });
        if (userListener.getUser().getU_address_2() != null && !userListener.getUser().getU_address_2().isEmpty())
            titles.add(new Title() {
                @Override
                public String getTitle() {
                    return userListener.getUser().getU_address_2();
                }
            });
        if (!titles.isEmpty())
            dialogFragmentSelectTitle.setTitles(titles);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.checkout_fragment,null);
        initView(v);

        tvPrice.setText(getString(R.string.totalPrice) + " " + requestOrder.getTotal());

        btnConfrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkForm()) {
                    fillForm();
                    ApiFactory.getService().order(requestOrder).enqueue(new MyCallbackWithMessageError<ResponseAnswer>() {
                        @Override
                        public void onData(ResponseAnswer data) {
                            showToast(data.getResult().getAnswer());
                            new Basket().delete(requestOrder.getShop_id());
                            getActivity().onBackPressed();
                        }

                        @Override
                        public void onRequestEnd() {

                        }
                    });
                }
            }
        });
        ivBtnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialogFragmentSelectTitle.getTitels().size() > 0)
                    dialogFragmentSelectTitle.show(getFragmentManager(), "");
            }
        });
        edTxDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                datePickerDialog = new DatePickerDialog(getActivity(), R.style.dialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        calendar.set(year, monthOfYear, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d,yy");
                        simpleDateFormat.setCalendar(calendar);
                        edTxDate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                datePickerDialog.show();

            }
        });
        edTxTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                timePickerDialog = new TimePickerDialog(getActivity(), R.style.dialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        edTxTime.setText(new SimpleDateFormat("HH:mm").format(calendar.getTime()));
                    }
                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);

                timePickerDialog.show();
            }
        });
        return v;
    }

    private void initView(View v) {
        edTxAddress = (EditText) v.findViewById(R.id.activityCheckOut_edTxAddress);
        edTxDate = (EditText) v.findViewById(R.id.activityCheckOut_edTxDate);
        edTxDescription = (EditText) v.findViewById(R.id.activityCheckOut_edTxDescription);
        ivBtnAddress = (ImageView) v.findViewById(R.id.activityCheckOut_ivBtnDialogAddress);
        tvPrice = (TextView) v.findViewById(R.id.activityCheckOut_tvPrice);
        group = (SegmentedGroup) v.findViewById(R.id.activityCheckOut_segmentedGroup);
        edTxTime = (EditText) v.findViewById(R.id.activityCheckOut_edTxTime);
        edTxTime.setKeyListener(null);
        edTxDate.setKeyListener(null);
        group.setTintColor(Color.GRAY, Color.WHITE);
        btnConfrim = (Button) v.findViewById(R.id.activityCheckOut_btnConfrim);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.activityCheckOut_btnCard:
                        requestOrder.setType_payment(RequestOrder.NON_CASH);
                        break;
                    case R.id.activityCheckOut_btnCash:
                        requestOrder.setType_payment(RequestOrder.CASH);
                        break;
                }
            }
        });
    }
    boolean checkForm() {

        if (edTxAddress.getText()!=null && edTxAddress.getText().toString().isEmpty()) {
            showToast(getString(R.string.enterAddress));
            return false;
        }
        if (requestOrder.getType_payment()!=null && requestOrder.getType_payment().isEmpty()) {
            showToast(getString(R.string.selectPayment));
            return false;
        }

        return true;
    }

    public void fillForm() {
        if (calendar != null) {
            requestOrder.setDate_time_user(calendar.getTime().getTime()/1000);
        }
        requestOrder.setAddress(edTxAddress.getText().toString());
        if (!edTxDescription.getText().toString().isEmpty()) {
            requestOrder.setDescription(edTxDescription.getText().toString());
        }
    }

}
