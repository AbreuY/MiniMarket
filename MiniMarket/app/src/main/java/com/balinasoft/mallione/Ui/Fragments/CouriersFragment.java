package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.adapters.AdapterCouriers;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.modelUsers.Courier;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestCouriers;
import com.balinasoft.mallione.networking.Response.ResponseCourier;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

/**
 * Created by Anton Kolotsey on 01.08.2016.
 */
public class CouriersFragment extends Basefragment {
    public static final String TAG = "CouriersFragment";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    AdapterCouriers adapterCouriers;
    UserListener<? extends User> userListener;
    RequestCouriers requestCouriers=new RequestCouriers();
    SwipyRefreshLayout swipyRefreshLayout;
    ToolbarListener toolbarListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            userListener = (UserListener<? extends User>) getActivity();
            toolbarListener = (ToolbarListener) getActivity();
        } catch (ClassCastException e) {

        }
        toolbarListener.setTittle(getString(R.string.couriers));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestCouriers.setSession_id(userListener.getUser().getSession_id());
        requestCouriers.setUser_id(String.valueOf(userListener.getUser().getId()));
        requestCouriers.setFull_info("1");
        adapterCouriers = new AdapterCouriers(new ArrayList<Courier>(), getActivity());
        addCouriers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.couriers_fragment, null);
        initView(v);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                swipyRefreshLayout.setRefreshing(true);
                addCouriers();
            }
        });
        recyclerView.setAdapter(adapterCouriers);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.couriersFragment_recyclrerView);
        progressBar = (ProgressBar) v.findViewById(R.id.couriersFragment_progressBar);
        swipyRefreshLayout = (SwipyRefreshLayout) v.findViewById(R.id.couriersFragment_swipe);
    }

    public void addCouriers() {
        getService().couriers(requestCouriers).enqueue(new MyCallbackWithMessageError<ResponseCourier>() {
            @Override
            public void onData(ResponseCourier data) {

                adapterCouriers.addItems(data.getResult());
            }

            @Override
            public void onRequestEnd() {
                if (isAdded()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    swipyRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
}
