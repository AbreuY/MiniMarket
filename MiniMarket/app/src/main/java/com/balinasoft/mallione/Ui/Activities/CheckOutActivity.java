//package com.balinasoft.mallione.Ui.Activities;
//
//import android.app.DatePickerDialog;
//import android.app.TimePickerDialog;
//import android.graphics.Color;
//import android.graphics.PorterDuff;
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.Button;
//import android.widget.DatePicker;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RadioGroup;
//import android.widget.TextView;
//import android.widget.TimePicker;
//import android.widget.Toast;
//
//import com.balinasoft.mallione.Implementations.Basket;
//import com.balinasoft.mallione.R;
//import com.balinasoft.mallione.Ui.Dialogs.DialogFragmentSelectTitle;
//import com.balinasoft.mallione.Utils.AuthManager;
//import com.balinasoft.mallione.interfaces.Title;
//import com.balinasoft.mallione.models.ProductItems.BasketProductItem;
//import com.balinasoft.mallione.models.modelUsers.Buer;
//import com.balinasoft.mallione.models.modelUsers.User;
//import com.balinasoft.mallione.networking.ApiFactory;
//import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
//import com.balinasoft.mallione.networking.Request.RequestOrder;
//import com.balinasoft.mallione.networking.Response.ResponseAnswer;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//
//import info.hoang8f.android.segmented.SegmentedGroup;
//
//public class CheckOutActivity extends AppCompatActivity {
//    Toolbar toolbar;
//    RequestOrder requestOrder;
//    DialogFragmentSelectTitle dialogFragmentSelectTitle = new DialogFragmentSelectTitle().setOnClickButtonsListener(new DialogFragmentSelectTitle.OnClickButtonsListener() {
//        @Override
//        public void onClickOk(Title title) {
//            edTxAddress.setText(title.getTitle());
//            requestOrder.setAddress(title.getTitle());
//        }
//
//        @Override
//        public void onClickCancel() {
//
//        }
//    });
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_check_out);
//        initView();
//
//        if (getIntent() != null) {
//            new AuthManager(this).setExtractListener(new AuthManager.SimpleExtractListener() {
//                @Override
//                public void onEmpty() {
//
//                }
//
//                @Override
//                public void onUser(final User user) {
//                    if (user instanceof Buer) {
//                        ArrayList<Title> titles = new ArrayList<Title>();
//                        if (((Buer) user).getU_address_1() != null && !((Buer) user).getU_address_1().isEmpty())
//                            titles.add(new Title() {
//                                @Override
//                                public String getTitle() {
//                                    return ((Buer) user).getU_address_1();
//                                }
//                            });
//                        if (((Buer) user).getU_address_2() != null && !((Buer) user).getU_address_2().isEmpty())
//                            titles.add(new Title() {
//                                @Override
//                                public String getTitle() {
//                                    return ((Buer) user).getU_address_2();
//                                }
//                            });
//                        if (!titles.isEmpty())
//                            dialogFragmentSelectTitle.setTitles(titles);
//                    }
//                    ArrayList<BasketProductItem> items = getIntent().getParcelableArrayListExtra("ProductsOrder");
//                    requestOrder = new RequestOrder(user, items);
//                    tvPrice.setText(getString(R.string.totalPrice) + " " + requestOrder.getTotal());
//                }
//            }).extract();
//
//        }
//        btnConfrim.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (checkForm()) {
//                    fillForm();
//                    ApiFactory.getService().order(requestOrder).enqueue(new MyCallbackWithMessageError<ResponseAnswer>() {
//                        @Override
//                        public void onData(ResponseAnswer data) {
//                            Toast.makeText(CheckOutActivity.this, data.getResult().getAnswer(), Toast.LENGTH_SHORT).show();
//                            new Basket().delete(requestOrder.getShop_id());
//                            onBackPressed();
//
//                        }
//
//                        @Override
//                        public void onRequestEnd() {
//                        }
//                    });
//                }
//            }
//        });
//        ivBtnAddress.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (dialogFragmentSelectTitle.getTitels().size() > 0)
//                    dialogFragmentSelectTitle.show(getSupportFragmentManager(), "");
//            }
//        });
//        edTxDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calendar = Calendar.getInstance();
//                datePickerDialog = new DatePickerDialog(CheckOutActivity.this, R.style.dialogTheme, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//
//                        calendar.set(year, monthOfYear, dayOfMonth);
//                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, MMM d,yy");
//                        simpleDateFormat.setCalendar(calendar);
//                        edTxDate.setText(simpleDateFormat.format(calendar.getTime()));
//                    }
//                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
//                datePickerDialog.show();
//
//            }
//        });
//        edTxTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                calendar = Calendar.getInstance();
//                timePickerDialog = new TimePickerDialog(CheckOutActivity.this, R.style.dialogTheme, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//
//                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
//                        calendar.set(Calendar.MINUTE, minute);
//                        edTxTime.setText(new SimpleDateFormat("HH:mm").format(calendar.getTime()));
//                    }
//                }, calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE), true);
//
//                timePickerDialog.show();
//            }
//        });
//    }
//
//    Calendar calendar;
//    DatePickerDialog datePickerDialog;
//    TimePickerDialog timePickerDialog;
//    EditText edTxAddress, edTxDate, edTxDescription, edTxTime;
//    ImageView ivBtnAddress;
//    TextView tvPrice;
//    SegmentedGroup group;
//    Button btnConfrim;
//
//    private void initView() {
//        toolbar = (Toolbar) findViewById(R.id.activityCheckOut_toolBar);
//        setSupportActionBar(toolbar);
//        edTxAddress = (EditText) findViewById(R.id.activityCheckOut_edTxAddress);
//        edTxDate = (EditText) findViewById(R.id.activityCheckOut_edTxDate);
//        edTxDescription = (EditText) findViewById(R.id.activityCheckOut_edTxDescription);
//        ivBtnAddress = (ImageView) findViewById(R.id.activityCheckOut_ivBtnDialogAddress);
//        tvPrice = (TextView) findViewById(R.id.activityCheckOut_tvPrice);
//        group = (SegmentedGroup) findViewById(R.id.activityCheckOut_segmentedGroup);
//        edTxTime = (EditText) findViewById(R.id.activityCheckOut_edTxTime);
//        edTxTime.setKeyListener(null);
//        edTxDate.setKeyListener(null);
//        group.setTintColor(Color.GRAY, Color.WHITE);
//        btnConfrim = (Button) findViewById(R.id.activityCheckOut_btnConfrim);
//        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.activityCheckOut_btnCard:
//                        requestOrder.setType_payment(RequestOrder.NON_CASH);
//                        break;
//                    case R.id.activityCheckOut_btnCash:
//                        requestOrder.setType_payment(RequestOrder.CASH);
//                        break;
//                }
//            }
//        });
//
//        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
//        upArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
//
//        toolbar.setNavigationIcon(upArrow);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//    }
//
//    boolean checkForm() {
//        if (edTxAddress.getText().toString().isEmpty()) {
//            Toast.makeText(this, getString(R.string.enterAddress), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//        if (requestOrder.getType_payment().isEmpty()) {
//            Toast.makeText(this, getString(R.string.selectPayment), Toast.LENGTH_SHORT).show();
//            return false;
//        }
//
//        return true;
//    }
//
//    public void fillForm() {
//        if (calendar != null) {
//            requestOrder.setDate_time_user(calendar.getTime().getTime()/1000);
//        }
//        requestOrder.setAddress(edTxAddress.getText().toString());
//        if (!edTxDescription.getText().toString().isEmpty()) {
//            requestOrder.setDescription(edTxDescription.getText().toString());
//        }
//    }
//}
