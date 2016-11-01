package com.balinasoft.mallione.Ui.Dialogs;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.CustomViews.CustomNumberPicker;
import com.balinasoft.mallione.interfaces.Title;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.modelUsers.Courier;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.ApiFactory;
import com.balinasoft.mallione.networking.DispatcherFio;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestCouriers;
import com.balinasoft.mallione.networking.Response.ResponseCourier;
import com.balinasoft.mallione.networking.Response.ResponseDispatchers;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Created by Anton Kolotsey on 26.07.2016.
 */
public class DialogConfrim extends BaseDialog {

    String shop_id;
    Boolean firstOpening;
    String taxiTime;
    Calendar calendar;
    TimePickerDialog timePickerDialog;

    public DialogConfrim setTypeUserListener(TypeUserListener typeUserListener) {
        this.typeUserListener = typeUserListener;
        return this;
    }

    public interface TypeUserListener {
        void onDispatcher(DispatcherFio dispatcher);

        void onCourier(Courier courier);

        void onManager(String response);
    }

    TypeUserListener typeUserListener;
    UserListener<? extends User> userListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (getArguments() != null) {
            shop_id = getArguments().getString("shop_id");
        }
        userListener = (UserListener<? extends User>) getActivity();
    }

    RequestCouriers requestCouriers = new RequestCouriers();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_confrim, null);
        initView(v);
        firstOpening = true;
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("shop_id", shop_id);
        requestCouriers.setFull_info("0");
        requestCouriers.setSession_id(userListener.getUser().getSession_id());
        requestCouriers.setUser_id(String.valueOf(userListener.getUser().getId()));
        btnCourier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customNumberPicker.setVisibility(View.VISIBLE);
                linTaxiTime.setVisibility(View.INVISIBLE);
                SpannableString content = new SpannableString("Задать время");
                content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                tvDeliveryTime.setText(content);

                ApiFactory.getService().couriers(requestCouriers).enqueue(new MyCallbackWithMessageError<ResponseCourier>() {
                    @Override
                    public void onData(ResponseCourier data) {
                        customNumberPicker.setTitles(data.getResult());
                    }

                    @Override
                    public void onRequestEnd() {

                    }
                });
            }
        });
        btnDispatcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customNumberPicker.setVisibility(View.VISIBLE);
                linTaxiTime.setVisibility(View.INVISIBLE);
                tvDeliveryTime.setText("Задать время");
                ApiFactory.getService().dispatcher(hashMap).enqueue(new MyCallbackWithMessageError<ResponseDispatchers>() {
                    @Override
                    public void onData(ResponseDispatchers data) {
                        customNumberPicker.setTitles(data.getResult());
                    }

                    @Override
                    public void onRequestEnd() {

                    }
                });
            }
        });

        tvDeliveryTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calendar = Calendar.getInstance();
                timePickerDialog = new TimePickerDialog(getActivity(), R.style.dialogTheme, new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String hours, minutes;
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        calendar.set(Calendar.MINUTE, minute);
                        taxiTime = (new SimpleDateFormat("H:m").format(calendar.getTime()));
                        StringTokenizer timeToken = new StringTokenizer(taxiTime, ":");
                        hours = timeToken.nextToken();
                        minutes = timeToken.nextToken();

                        if (!hours.equals("0") && !minutes.equals("0")) {
                            if (hours.equals("1") || (hours.equals("21"))) {
                                taxiTime = hours + " час " + minutes + " минут";
                            } else if (hours.equals("2") || hours.equals("3") || hours.equals("4") ||
                                    hours.equals("22") || hours.equals("23")) {
                                taxiTime = hours + " часa " + minutes + " минут";
                            } else
                                taxiTime = hours + " часов " + minutes + " минут";

                        } else if ((hours.equals("0")) && !(minutes.equals("0"))) {
                            taxiTime = minutes + " минут";

                        } else if (!(hours).equals("0") && (minutes.equals("0")))
                            if (hours.equals("1") || (hours.equals("21"))) {
                                taxiTime = hours + " час ";
                            } else if (hours.equals("2") || hours.equals("3") || hours.equals("4") ||
                                    hours.equals("22") || hours.equals("23")) {
                                taxiTime = hours + " часa";
                            } else
                                taxiTime = hours + " часов";

                        if (hours.equals("0") && minutes.equals("0")) {
                            Toast.makeText(getContext(), "Укажите правильное время выполнения заказа", Toast.LENGTH_SHORT).show();
                            tvDeliveryTime.callOnClick();
                        } else {
                            SpannableString content = new SpannableString(taxiTime);
                            content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
                            tvDeliveryTime.setText(content);
                        }


                    }
                }, 0, 0, true);

                timePickerDialog.show();
            }
        });

        btnTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (firstOpening) {
//                    firstOpening = false;
////                    tvDeliveryTime.callOnClick();
//                }
                customNumberPicker.setVisibility(View.INVISIBLE);
                linTaxiTime.setVisibility(View.VISIBLE);
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (customNumberPicker.getTitles().size() == 1) {
                    Title title = customNumberPicker.getTitles().get(0);
                    if (title.getClass() == Courier.class) {
                        typeUserListener.onCourier((Courier) title);
                    }
                    if (title.getClass() == DispatcherFio.class) {
                        typeUserListener.onDispatcher((DispatcherFio) title);
                    }
                    dismiss();
                } else if (typeUserListener != null) {

                    if (courier != null) {

                        typeUserListener.onCourier(courier);
                        dismiss();
                    }

                    if (dispatcher != null) {
                        typeUserListener.onDispatcher(dispatcher);
                        dismiss();
                    }

                }

                if (!tvDeliveryTime.getText().equals("Задать время")) {
                    typeUserListener.onManager(taxiTime);
                    dismiss();
                }

            }
        });
        btnDispatcher.callOnClick();
        return v;
    }

    Courier courier;
    DispatcherFio dispatcher;
    CustomNumberPicker customNumberPicker;
    LinearLayout linTaxiTime;
    TextView tvDeliveryTime;
    Button btnCourier, btnDispatcher, btnOk, btnTaxi;

    private void initView(View v) {
        tvDeliveryTime = (TextView) v.findViewById(R.id.dialogConfrim_tvDeliveryTime);
        linTaxiTime = (LinearLayout) v.findViewById(R.id.dialogConfrim_linTaxiTime);
        btnCourier = (Button) v.findViewById(R.id.dialogConfrim_btnCourier);
        btnDispatcher = (Button) v.findViewById(R.id.dialogConfrim_btnDispatcher);
        btnTaxi = (Button) v.findViewById(R.id.dialogConfrim_btnTaxi);
        btnOk = (Button) v.findViewById(R.id.dialogConfrim_btnOk);
        customNumberPicker = (CustomNumberPicker) v.findViewById(R.id.dialogConfrim_numPicker);
        customNumberPicker.setSelectListener(new CustomNumberPicker.SelectListener() {
            @Override
            public void onSelect(Title title) {
                if (title.getClass() == Courier.class) {
                    courier = (Courier) title;
                }
                if (title.getClass() == DispatcherFio.class) {
                    dispatcher = (DispatcherFio) title;
                }
            }
        });
    }
}
