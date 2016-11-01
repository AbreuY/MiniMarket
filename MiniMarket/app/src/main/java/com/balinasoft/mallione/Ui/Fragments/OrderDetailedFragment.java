package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Dialogs.AssessDialog;
import com.balinasoft.mallione.Ui.Dialogs.DialogConfrim;
import com.balinasoft.mallione.Ui.Dialogs.DialogSelectCouriers;
import com.balinasoft.mallione.adapters.ItemBasketAdapter;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.Order;
import com.balinasoft.mallione.models.ProductItems.BasketProductItem;
import com.balinasoft.mallione.models.Shops.Shop;
import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.models.modelUsers.Courier;
import com.balinasoft.mallione.models.modelUsers.Dispatcher;
import com.balinasoft.mallione.models.modelUsers.Manager;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.DispatcherFio;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestOrder;
import com.balinasoft.mallione.networking.Request.RequestOrderCourier;
import com.balinasoft.mallione.networking.Request.RequestOrderDispatcher;
import com.balinasoft.mallione.networking.Request.RequestOrderManager;
import com.balinasoft.mallione.networking.Request.RequestRecord;
import com.balinasoft.mallione.networking.Response.BaseResponse;
import com.balinasoft.mallione.networking.Response.ResponseAnswer;
import com.balinasoft.mallione.networking.Response.ResponseOrderBuyer;
import com.balinasoft.mallione.networking.Response.ResponseOrderCourier;
import com.balinasoft.mallione.networking.Response.ResponseOrderManger;
import com.balinasoft.mallione.networking.Result.ResultOrderBuyer;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Microsoft on 15.07.2016.
 */
public class OrderDetailedFragment extends Basefragment {

    public static final String TAG = "OrderDetailedFragment";
    TextView tvDate, tvStatusOrder, tvAddress, tvTime, tvTypePayment, tvStatusPayment, tvTotalPrice, tvComment;
    Button btnShop, btnComment, btnDispute;
    RecyclerView recyclerView;
    ItemBasketAdapter basketAdapter;
    RequestRecord requestRecord;
    UserListener<? extends User> userListener;
    Order order;
    ToolbarListener toolbarListener;
    ShowFragmentListener showFragmentListener;
    Shop shop;
    DialogConfrim dialogConfrim = new DialogConfrim();
    View vBlock;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userListener = (UserListener<? extends User>) getActivity();
        requestRecord = new RequestRecord("", userListener.getUser().getSession_id(), String.valueOf(userListener.getUser().getId()));
        if (getArguments() != null) {
            if (getArguments().getParcelable(Order.class.getCanonicalName()) != null) {
                order = getArguments().getParcelable(Order.class.getCanonicalName());
                shop = order.getShop();
                requestRecord.setOrder_id(String.valueOf(order.getId()));
            }
            if (getArguments().getString("ORDER_ID") != null) {
                requestRecord.setOrder_id(String.valueOf(getArguments().getString("ORDER_ID")));
            }
        }
        toolbarListener = (ToolbarListener) getActivity();
        toolbarListener.setTittle(getString(R.string.id) + " " + String.valueOf(requestRecord.getOrder_id()));
        showFragmentListener = (ShowFragmentListener) getActivity();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        basketAdapter = new ItemBasketAdapter(getActivity(), new ArrayList<BasketProductItem>());
        basketAdapter.setVisibilityControl(false);
        dialogConfrim.setTypeUserListener(new DialogConfrim.TypeUserListener() {
            @Override
            public void onDispatcher(DispatcherFio dispatcher) {
                requestOrderManager.setResponse("1");
                requestOrderManager.setDispatcher_id(String.valueOf(dispatcher.getId()));

                orderManager();
            }

            @Override
            public void onCourier(Courier courier) {
                requestOrderManager.setResponse("1");
                requestOrderManager.setCourier_id(String.valueOf(courier.getId()));
                orderManager();
            }

            @Override
            public void onManager(String response) {
                requestOrderManager.setResponse(response);
                orderManager();
            }

        });

    }

    private void orderManager() {
//        String json = new Gson().toJson(requestOrderManager);

        getService().orderManagerResponse(requestOrderManager).enqueue(new MyCallbackWithMessageError<ResponseAnswer>() {
            @Override
            public void onData(ResponseAnswer data) {
                showToast(data.getResult().getAnswer());
                getActivity().onBackPressed();
            }

            @Override
            public void onResponse(Call<ResponseAnswer> call, Response<ResponseAnswer> response) {
                super.onResponse(call, response);
            }

            @Override
            public void onFailure(Call<ResponseAnswer> call, Throwable t) {
                super.onFailure(call, t);
            }

            @Override
            public void onRequestEnd() {

            }
        });
    }

    RequestOrderManager requestOrderManager = new RequestOrderManager();
    RequestOrderCourier requestOrderCourier = new RequestOrderCourier();
    RequestOrderDispatcher requestOrderDispatcher = new RequestOrderDispatcher();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.order_detailed, null);
        initView(v);
        if (userListener != null && userListener.getUser().getClass() != Buer.class) {

            btnDispute.setVisibility(View.GONE);
            btnComment.setVisibility(View.GONE);

            if (userListener.getUser().getClass() == Manager.class) {

                requestOrderManager.setSession_id(userListener.getUser().getSession_id());
                requestOrderManager.setOrder_id(requestRecord.getOrder_id());
                requestOrderManager.setManager_id(String.valueOf(userListener.getUser().getId()));
//                btnOrderDelivered.setVisibility(View.GONE);
                btnPickedUp.setVisibility(View.GONE);
                addOrderManager();
            }
            if (userListener.getUser().getClass() == Courier.class) {

                requestOrderCourier.setSession_id(userListener.getUser().getSession_id());
                requestOrderCourier.setCourier_id(String.valueOf(userListener.getUser().getId()));
                requestOrderCourier.setOrder_id(requestRecord.getOrder_id());
                addOrderCourier();
            }
            if (userListener.getUser().getClass() == Dispatcher.class) {

                requestOrderDispatcher.setSession_id(userListener.getUser().getSession_id());
                requestOrderDispatcher.setDispatcher_id(String.valueOf(userListener.getUser().getId()));
                requestOrderDispatcher.setOrder_id(requestRecord.getOrder_id());
                btnOrderDelivered.setVisibility(View.GONE);
                btnPickedUp.setVisibility(View.GONE);
                addOrderDispatcher();
            }

        } else {

            btnConfrimOrder.setVisibility(View.GONE);
            btnCancel.setVisibility(View.GONE);
            btnConfrimOrder.setVisibility(View.GONE);
            btnOrderDelivered.setVisibility(View.GONE);
            btnPickedUp.setVisibility(View.GONE);
            addOrderBuyer();
        }

        recyclerView.setAdapter(basketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        return v;
    }

    private void addOrderDispatcher() {
        getService().orderManager(requestRecord).enqueue(new MyCallbackWithMessageError<ResponseOrderManger>() {
            @Override
            public void onData(ResponseOrderManger data) {
                addOrder(data.getResult());
                if (data.getResult().getResponse_status_cancel().equals("1")) {
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            requestOrderDispatcher.setResponse("0");
                            getService().orderDispatcherResponse(requestOrderDispatcher).enqueue(new MyCallbackWithMessageError<ResponseAnswer>() {
                                @Override
                                public void onData(ResponseAnswer data) {
                                    showToast(data.getResult().getAnswer());
                                    getActivity().onBackPressed();
                                }

                                @Override
                                public void onRequestEnd() {

                                }
                            });
                        }
                    });
                } else btnCancel.setVisibility(View.GONE);
                if (data.getResult().getResponse_status_confirm().equals("1")) {
                    btnConfrimOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            requestOrderDispatcher.setResponse("1");

                            new DialogSelectCouriers().setClickListener(new DialogSelectCouriers.ClickListener() {
                                @Override
                                public void onClickOk(String courier_id) {
                                    requestOrderDispatcher.setCourier_id(courier_id);
                                    getService().orderDispatcherResponse(requestOrderDispatcher).enqueue(new MyCallbackWithMessageError<ResponseAnswer>() {
                                        @Override
                                        public void onData(ResponseAnswer data) {
                                            showToast(data.getResult().getAnswer());
                                            getActivity().onBackPressed();
                                        }

                                        @Override
                                        public void onRequestEnd() {

                                        }
                                    });
                                }
                            }).show(getFragmentManager(), "");
                        }
                    });
                } else btnConfrimOrder.setVisibility(View.GONE);

            }

            @Override
            public void onRequestEnd() {

            }
        });
    }

    private void addOrderCourier() {
        getService().orderCourier(requestRecord).enqueue(new MyCallbackWithMessageError<ResponseOrderCourier>() {
            @Override
            public void onData(ResponseOrderCourier data) {
                addOrder(data.getResult());
                if (data.getResult().getResponse_status_confirm().equals("1")) {
                    btnConfrimOrder.setVisibility(View.VISIBLE);
                    btnConfrimOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestOrderCourier.setResponse("1");
                            getService().orderCourierResponse(requestOrderCourier).enqueue(new MyCallbackWithMessageError<ResponseAnswer>() {
                                @Override
                                public void onData(ResponseAnswer data) {
                                    showToast(data.getResult().getAnswer());
                                    getActivity().onBackPressed();
                                }

                                @Override
                                public void onRequestEnd() {

                                }
                            });
                        }
                    });

                } else {
                    btnConfrimOrder.setVisibility(View.GONE);

                }
                if (data.getResult().getResponse_status_cancel().equals("1")) {
                    btnCancel.setVisibility(View.VISIBLE);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestOrderCourier.setResponse("0");
                            getService().orderCourierResponse(requestOrderCourier).enqueue(new MyCallbackWithMessageError<ResponseAnswer>() {
                                @Override
                                public void onData(ResponseAnswer data) {
                                    showToast(data.getResult().getAnswer());
                                    getActivity().onBackPressed();
                                }

                                @Override
                                public void onRequestEnd() {

                                }
                            });
                        }
                    });
                } else btnCancel.setVisibility(View.GONE);

                if (data.getResult().getCourier_finish().equals("1")) {
                    btnOrderDelivered.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestOrderCourier.setResponse(null);
                            getService().orderCourierFinish(requestOrderCourier).enqueue(new MyCallbackWithMessageError<BaseResponse>() {
                                @Override
                                public void onData(BaseResponse data) {
                                    getActivity().onBackPressed();
                                }

                                @Override
                                public void onRequestEnd() {

                                }
                            });
                        }
                    });
                } else {
                    btnOrderDelivered.setVisibility(View.GONE);
                }
                if (data.getResult().getCourier_confirm().equals("1")) {
                    btnPickedUp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestOrderCourier.setResponse(null);

                            getService().orderCourierConfrim(requestOrderCourier).enqueue(new MyCallbackWithMessageError<ResponseAnswer>() {
                                @Override
                                public void onData(ResponseAnswer data) {
                                    showToast(data.getResult().getAnswer());
                                    getActivity().onBackPressed();
                                }

                                @Override
                                public void onRequestEnd() {

                                }
                            });
                        }
                    });
                } else {
                    btnPickedUp.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRequestEnd() {

            }
        });
    }

    private void addOrderManager() {
        getService().orderManager(requestRecord).enqueue(new MyCallbackWithMessageError<ResponseOrderManger>() {
            @Override
            public void onData(ResponseOrderManger data) {
                addOrder(data.getResult());
                if (data.getResult().getManager_finish().equals("1")) {
                    btnOrderDelivered.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestOrderCourier.setSession_id(requestOrderManager.getSession_id());
                            requestOrderCourier.setCourier_id(requestOrderManager.getManager_id());
                            requestOrderCourier.setOrder_id(requestOrderManager.getOrder_id());
                            getService().orderCourierFinish(requestOrderCourier).enqueue(new MyCallbackWithMessageError<BaseResponse>() {
                                @Override
                                public void onData(BaseResponse data) {
                                    Toast.makeText(getContext(), "Статус заказа изменен на \"Доставлен\"", Toast.LENGTH_SHORT).show();
                                    getActivity().onBackPressed();
                                }

                                @Override
                                public void onRequestEnd() {

                                }
                            });
                        }
                    });
                } else {
                    btnOrderDelivered.setVisibility(View.GONE);
                }

                if (data.getResult().getResponse_status_confirm().equals("1")) {
                    btnConfrimOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!dialogConfrim.isAdded()) {
                                Bundle bundle = new Bundle();
                                bundle.putString("shop_id", String.valueOf(shop.getId()));
                                dialogConfrim.setArguments(bundle);
                                dialogConfrim.show(getFragmentManager(), "");
                            }
                        }
                    });

                } else {
                    btnConfrimOrder.setVisibility(View.GONE);

                }
                if (data.getResult().getResponse_status_cancel().equals("1")) {
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestOrderManager.setResponse("0");
                            requestOrderManager.setManager_id(String.valueOf(userListener.getUser().getId()));
                            getService().orderManagerResponse(requestOrderManager).enqueue(new MyCallbackWithMessageError<ResponseAnswer>() {
                                @Override
                                public void onData(ResponseAnswer data) {
                                    showToast(data.getResult().getAnswer());
                                    getActivity().onBackPressed();
                                }

                                @Override
                                public void onRequestEnd() {

                                }
                            });
                        }
                    });
                } else btnCancel.setVisibility(View.GONE);

                btnShop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Shop.class.getCanonicalName(), shop);
                        showFragmentListener.showFragment(ShopInformation.TAG, bundle, true);
                    }
                });
            }

            @Override
            public void onRequestEnd() {

            }
        });
    }

    private void addOrderBuyer() {
        getService().getOrderBuyer(requestRecord).enqueue(new MyCallbackWithMessageError<ResponseOrderBuyer>() {
            @Override
            public void onData(ResponseOrderBuyer data) {
                addOrder(data.getResult());
                btnShop.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Shop.class.getCanonicalName(), shop);
                        showFragmentListener.showFragment(ShopInformation.TAG, bundle, true);
                    }
                });
                if (data.getResult().getDispute_status().equals("1"))
                    btnDispute.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                            Intent intent = new Intent(getActivity(), BasketItemsActivity.class);
//                            intent.putExtra("DISPUTE_ORDER", order);
//                            getActivity().startActivity(intent);
                            Bundle bundle = new Bundle();
                            bundle.putString("order_id", String.valueOf(requestRecord.getOrder_id()));
//                            bundle.putParcelable(Order.class.getCanonicalName(), order);
                            showFragmentListener.showFragment(SendDisputeFragment.TAG, bundle, false);

                        }
                    });
                else btnDispute.setVisibility(View.GONE);

//                if (data.getResult().getStatus().equals("Доставлен")) {
                if (data.getResult().getReview_status().equals("0")) {
                    btnComment.setVisibility(View.VISIBLE);
                    btnComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
//                                Order order = new Order();
//                                order.setId(Integer.parseInt(requestOrderManager.getOrder_id()));
                            new AssessDialog().setTypeAssess(AssessDialog.ORDER).setOrder(requestRecord.getOrder_id()).show(getFragmentManager(), "");
                        }
                    });
//                    } else btnComment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onRequestEnd() {

            }
        });
    }

    public void addOrder(ResultOrderBuyer data) {
        basketAdapter.addItems(data.getItems());
        shop = data.getShop();
        tvDate.setText(getString(R.string.dateOrder) + " " + data.getDate_time_start());
        tvAddress.setText(getString(R.string.addressPostage) + " " + data.getAddress());
        tvStatusOrder.setText(getString(R.string.statusOrder) + " " + data.getStatus());
        tvTime.setText(getString(R.string.timeOrder) + " " + data.getDate_time_approximate());
        if (data.getType_payment().equals(RequestOrder.CASH)) {
            tvTypePayment.setText(getString(R.string.typePayment) + " " + getString(R.string.byCash));
        } else
            tvTypePayment.setText(getString(R.string.typePayment) + " " + getString(R.string.card));
        if (data.getPayment().equals("1"))
            tvStatusPayment.setText(getString(R.string.statusPayment) + " " + getString(R.string.paid));
        else
            tvStatusPayment.setText(getString(R.string.statusPayment) + " " + getString(R.string.unpaid));
        tvTotalPrice.setText(getString(R.string.totalPriceOrder) + " " + data.getTotal());
        if (!data.getDescription().isEmpty()) {
            vBlock.setVisibility(View.VISIBLE);
            tvComment.setVisibility(View.VISIBLE);
            tvComment.setText(getString(R.string.commentOrder) + " " + data.getDescription());
        } else {
            vBlock.setVisibility(View.GONE);
            tvComment.setVisibility(View.GONE);
        }

//        ApiFactory.getBankService().getOrderStatusExtended(String.valueOf(data.getId()), APIBank.PASSWORD, APIBank.USER_NAME).enqueue(new Callback<ResponseBankStatusExtended>() {
//            @Override
//            public void onResponse(Call<ResponseBankStatusExtended> call, Response<ResponseBankStatusExtended> response) {
//                if (response.body().getOrderStatus() == "2") {
//                    tvStatusPayment.setText(getString(R.string.statusPayment) + " " + getString(R.string.paid));
//                } else {
//                    tvStatusPayment.setText(getString(R.string.statusPayment) + " " + getString(R.string.unpaid));
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBankStatusExtended> call, Throwable t) {
//
//            }
//        });
    }

    private boolean checkDispute() {
        return order != null && order.getReview_status() != null && order.getReview_status().equals("1");
    }

    private boolean checkComment() {
        return order != null && order.getReview_status() != null && order.getReview_status().equals("1");
    }

    Button btnConfrimOrder, btnCancel, btnPickedUp, btnOrderDelivered;

    private void initView(View v) {
        tvDate = (TextView) v.findViewById(R.id.orderDetailed_tvDate);
        tvStatusOrder = (TextView) v.findViewById(R.id.orderDetailed_tvStatus);
        tvAddress = (TextView) v.findViewById(R.id.orderDetailed_tvAddress);
        tvTime = (TextView) v.findViewById(R.id.orderDetailed_tvTime);
        tvTypePayment = (TextView) v.findViewById(R.id.orderDetailed_tvTypePayment);
        tvStatusPayment = (TextView) v.findViewById(R.id.orderDetailed_tvStatusPaymanet);
        tvTotalPrice = (TextView) v.findViewById(R.id.orderDetailed_tvTotalPrice);
        tvComment = (TextView) v.findViewById(R.id.orderDetailed_tvComment);
        btnShop = (Button) v.findViewById(R.id.orderDetailed_btnShop);
        btnComment = (Button) v.findViewById(R.id.orderDetailed_btnAssess);
        btnDispute = (Button) v.findViewById(R.id.orderDetailed_btnDispute);
        recyclerView = (RecyclerView) v.findViewById(R.id.orderDetailed_recyclerView);
        btnConfrimOrder = (Button) v.findViewById(R.id.orderDetailed_btnConfrimOrder);
        btnCancel = (Button) v.findViewById(R.id.orderDetailed_btnCancel);
        btnPickedUp = (Button) v.findViewById(R.id.orderDetailed_btnPickedUp);
        btnOrderDelivered = (Button) v.findViewById(R.id.orderDetailed_btnOrderDelivered);
        vBlock = (View) v.findViewById(R.id.orderDetailed_block);
    }
}
