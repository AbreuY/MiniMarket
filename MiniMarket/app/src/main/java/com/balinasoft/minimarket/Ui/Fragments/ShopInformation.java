package com.balinasoft.minimarket.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.adapters.AdapterShopInfo;
import com.balinasoft.minimarket.interfaces.ToolbarListener;
import com.balinasoft.minimarket.interfaces.UserListener;
import com.balinasoft.minimarket.models.Shops.Shop;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.balinasoft.minimarket.networking.ApiFactory;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.RequestReviews;
import com.balinasoft.minimarket.networking.Response.ResponseShop;
import com.balinasoft.minimarket.networking.Response.ResposeReviewsShopForBuyer;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.HashMap;

/**
 * Created by Microsoft on 19.07.2016.
 */
public class ShopInformation extends Basefragment {
    public static final String TAG = "ShopInformation";
    Shop shop;
    AdapterShopInfo adapterShopInfo;
    RequestReviews requestReviews;
    UserListener<? extends User> userListener;
    ToolbarListener toolbarListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            userListener = (UserListener<? extends User>) getActivity();
            toolbarListener = (ToolbarListener) getActivity();
        } catch (ClassCastException e) {

        }
        shop = getArguments().getParcelable(Shop.class.getCanonicalName());
        if (shop!=null && shop.getShop() != null)
            toolbarListener.setTittle(shop.getShop());


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shop_info, null);
        initView(v);

        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("shop_id", String.valueOf(shop.getId()));
        ApiFactory.getService().shop(hashMap).enqueue(new MyCallback<ResponseShop>() {
            @Override
            public void onData(ResponseShop data) {
                shop = data.getResult();
                adapterShopInfo = new AdapterShopInfo(getActivity(), data.getResult());
                recyclerView.setAdapter(adapterShopInfo);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                if (userListener != null && userListener.getUser() != null) {
                    requestReviews = new RequestReviews
                            (userListener.getUser().getSession_id(),
                                    String.valueOf(userListener.getUser().getId()),
                                    String.valueOf(shop.getId()));
                    addReview();
                }
            }

            @Override
            public void onRequestEnd() {

            }
        });

        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {

            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                swipyRefreshLayout.setRefreshing(true);
                addReview();
            }
        });
        return v;
    }

    ProgressBar progressBar;
    RecyclerView recyclerView;
    SwipyRefreshLayout swipyRefreshLayout;

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.shopInfo_recyclerView);
        progressBar = (ProgressBar) v.findViewById(R.id.shopInfo_progressBar);
        swipyRefreshLayout = (SwipyRefreshLayout) v.findViewById(R.id.shopInfo_swipy);

    }

    public void addReview() {
        getService().reviewForBuyer(requestReviews).enqueue(new MyCallback<ResposeReviewsShopForBuyer>() {

            @Override
            public void onData(ResposeReviewsShopForBuyer data) {
                if (data.getResult().size() > 0) {
                    requestReviews.setOffset(requestReviews.getOffset() + data.getResult().size());
                    adapterShopInfo.addItems(data.getResult());
                }

            }

            @Override
            public void onRequestEnd() {
                progressBar.setVisibility(View.INVISIBLE);
                swipyRefreshLayout.setRefreshing(false);
            }
        });
    }
}

