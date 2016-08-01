package com.balinasoft.minimarket.Ui.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.Activities.BasketItemsActivity;
import com.balinasoft.minimarket.Ui.Dialogs.AssessDialog;
import com.balinasoft.minimarket.Ui.Dialogs.DialogConfrim;
import com.balinasoft.minimarket.Ui.Dialogs.DialogSelectCouriers;
import com.balinasoft.minimarket.adapters.ItemBasketAdapter;
import com.balinasoft.minimarket.interfaces.ShowFragmentListener;
import com.balinasoft.minimarket.interfaces.ToolbarListener;
import com.balinasoft.minimarket.interfaces.UserListener;
import com.balinasoft.minimarket.models.Order;
import com.balinasoft.minimarket.models.ProductItems.BasketProductItem;
import com.balinasoft.minimarket.models.Shops.Shop;
import com.balinasoft.minimarket.models.modelUsers.Buer;
import com.balinasoft.minimarket.models.modelUsers.Courier;
import com.balinasoft.minimarket.models.modelUsers.Dispatcher;
import com.balinasoft.minimarket.models.modelUsers.Manager;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.balinasoft.minimarket.networking.DispatcherFio;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.RequestOrder;
import com.balinasoft.minimarket.networking.Request.RequestOrderCourier;
import com.balinasoft.minimarket.networking.Request.RequestOrderDispatcher;
import com.balinasoft.minimarket.networking.Request.RequestOrderManager;
import com.balinasoft.minimarket.networking.Request.RequestRecord;
import com.balinasoft.minimarket.networking.Request.RequestUserData;
import com.balinasoft.minimarket.networking.Response.BaseResponse;
import com.balinasoft.minimarket.networking.Response.ResponseAnswer;
import com.balinasoft.minimarket.networking.Response.ResponseOrderBuyer;
import com.balinasoft.minimarket.networking.Response.ResponseOrderCourier;
import com.balinasoft.minimarket.networking.Response.ResponseOrderManger;
import com.balinasoft.minimarket.networking.Result.ResultOrderBuyer;

import java.util.ArrayList;

/**
 * Created by Microsoft on 15.07.2016.
 */
public class OrderDetailedFragment extends Basefragment {

    public static final String TAG = "OrderDetailedFragment";
    TextView tvDate, tvStatusOrder, tvAddress, tvTime, tvTypePayment, tvStatusPayment, tvTotalPrice;
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

        });

    }

    private void orderManager() {
        getService().orderManagerResponse(requestOrderManager).enqueue(new MyCallback<ResponseAnswer>() {
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
                btnOrderDelivered.setVisibility(View.GONE);
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
        getService().orderManager(requestRecord).enqueue(new MyCallback<ResponseOrderManger>() {
            @Override
            public void onData(ResponseOrderManger data) {
                addOrder(data.getResult());
                if (data.getResult().getResponse_status_cancel().equals("1")) {
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            requestOrderDispatcher.setResponse("0");
                            getService().orderDispatcherResponse(requestOrderDispatcher).enqueue(new MyCallback<ResponseAnswer>() {
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
                                    getService().orderDispatcherResponse(requestOrderDispatcher).enqueue(new MyCallback<ResponseAnswer>() {
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
        getService().orderCourier(requestRecord).enqueue(new MyCallback<ResponseOrderCourier>() {
            @Override
            public void onData(ResponseOrderCourier data) {
                addOrder(data.getResult());
                if (data.getResult().getResponse_status_confirm().equals("1")) {
                    btnConfrimOrder.setVisibility(View.VISIBLE);
                    btnConfrimOrder.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            requestOrderCourier.setResponse("1");
                            getService().orderCourierResponse(requestOrderCourier).enqueue(new MyCallback<ResponseAnswer>() {
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
                            getService().orderCourierResponse(requestOrderCourier).enqueue(new MyCallback<ResponseAnswer>() {
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
                            getService().orderCourierFinish(requestOrderCourier).enqueue(new MyCallback<BaseResponse>() {
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

                            getService().orderCourierConfrim(requestOrderCourier).enqueue(new MyCallback<ResponseAnswer>() {
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
        getService().orderManager(requestRecord).enqueue(new MyCallback<ResponseOrderManger>() {
            @Override
            public void onData(ResponseOrderManger data) {
                addOrder(data.getResult());
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
                            getService().orderManagerResponse(requestOrderManager).enqueue(new MyCallback<ResponseAnswer>() {
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
            }

            @Override
            public void onRequestEnd() {

            }
        });
    }

    private void addOrderBuyer() {
        getService().getOrderBuyer(requestRecord).enqueue(new MyCallback<ResponseOrderBuyer>() {
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
                            Intent intent = new Intent(getActivity(), BasketItemsActivity.class);
                            intent.putExtra("DISPUTE_ORDER", order);
                            getActivity().startActivity(intent);

                        }
                    });
                else btnDispute.setVisibility(View.GONE);

                if (data.getResult().getReview_status().equals("1")) {
                    btnComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AssessDialog().setTypeAssess(AssessDialog.ORDER).setOrder(order).show(getFragmentManager(), "");
                        }
                    });
                } else btnComment.setVisibility(View.GONE);
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
            tvTypePayment.setText(getString(R.string.typePayment) + " " + getString(R.string.card));
        } else
            tvTypePayment.setText(getString(R.string.typePayment) + " " + getString(R.string.byCash));
        if (data.getPayment().equals("1"))
            tvStatusPayment.setText(getString(R.string.statusPayment) + " " + getString(R.string.paid));
        else
            tvStatusPayment.setText(getString(R.string.statusPayment) + " " + getString(R.string.unpaid));
        tvTotalPrice.setText(getString(R.string.totalPriceOrder) + " " + data.getTotal());
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
        btnShop = (Button) v.findViewById(R.id.orderDetailed_btnShop);
        btnComment = (Button) v.findViewById(R.id.orderDetailed_btnAssess);
        btnDispute = (Button) v.findViewById(R.id.orderDetailed_btnDispute);
        recyclerView = (RecyclerView) v.findViewById(R.id.orderDetailed_recyclerView);
        btnConfrimOrder = (Button) v.findViewById(R.id.orderDetailed_btnConfrimOrder);
        btnCancel = (Button) v.findViewById(R.id.orderDetailed_btnCancel);
        btnPickedUp = (Button) v.findViewById(R.id.orderDetailed_btnPickedUp);
        btnOrderDelivered = (Button) v.findViewById(R.id.orderDetailed_btnOrderDelivered);
    }
}
