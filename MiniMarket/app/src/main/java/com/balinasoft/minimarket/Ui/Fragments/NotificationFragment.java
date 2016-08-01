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
import android.widget.ProgressBar;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.Activities.BasketItemsActivity;
import com.balinasoft.minimarket.adapters.AdapterNotification;
import com.balinasoft.minimarket.interfaces.ShowFragmentListener;
import com.balinasoft.minimarket.interfaces.ToolbarListener;
import com.balinasoft.minimarket.interfaces.UserListener;
import com.balinasoft.minimarket.models.Notification;
import com.balinasoft.minimarket.models.modelUsers.Buer;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.RequestUserData;
import com.balinasoft.minimarket.networking.Response.ResponseNotification;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

/**
 * Created by Microsoft on 11.07.2016.
 */
public class NotificationFragment extends Basefragment {
    public static final String TAG="NotificationFragment";
    RecyclerView recyclerView;
    SwipyRefreshLayout swipyRefreshLayout;
    ToolbarListener toolbarListener;
    AdapterNotification adapterNotification;
    UserListener<? extends User> usersListener;
    RequestUserData requestUserData;
    ShowFragmentListener showFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarListener=(ToolbarListener)getActivity();
        toolbarListener.closeToolbar();
        toolbarListener.setTittle(getString(R.string.notifications));
        usersListener=(UserListener<Buer>)getActivity();
        showFragmentListener=(ShowFragmentListener)getActivity();
        showFragmentListener.showFragmentToolBar(BlurbFragment.TAG,null);
        requestUserData=new RequestUserData(usersListener.getUser().getSession_id(),usersListener.getUser().getId());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapterNotification=new AdapterNotification(getActivity(),new ArrayList<Notification>());
        adapterNotification.setOnItemClickListener(new AdapterNotification.OnItemClickListener() {
            @Override
            public void onOrder(Notification notification) {
                Intent intent=new Intent(getActivity(), BasketItemsActivity.class);
                intent.putExtra("ORDER_ID",notification.getOrder_id());
                getActivity().startActivity(intent);
            }

            @Override
            public void onRecord(Notification notification) {
                showFragmentListener.showFragment(MyServicesFragment.TAG,null,true);
            }

            @Override
            public void onOther(Notification notification) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.notification_fragment,null);
        initView(v);
        recyclerView.setAdapter(adapterNotification);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      //  recyclerView.setNestedScrollingEnabled(false);
        addNotification(false);
        return v;
    }
ProgressBar progressBar;
    private void initView(View v) {
        recyclerView=(RecyclerView)v.findViewById(R.id.notificationFragment_recyclerView);
        swipyRefreshLayout=(SwipyRefreshLayout)v.findViewById(R.id.notificationFragment_swipyRefresh);
        progressBar=(ProgressBar)v.findViewById(R.id.notificationFragment_progressBar);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                swipyRefreshLayout.setRefreshing(true);
                addNotification(true);
            }
        });
    }
    public void addNotification(final boolean flagSmooth){
        getService().notifications(requestUserData).enqueue(new MyCallback<ResponseNotification>() {
            @Override
            public void onData(ResponseNotification data) {

                adapterNotification.addItems(data.getResult());
                int count=adapterNotification.getItemCount();
                if(data.getResult().size()>0) {
                    requestUserData.setOffset(adapterNotification.getItemCount());

                    if (flagSmooth){
                        recyclerView.smoothScrollToPosition(count+1);
                    }
                }

            }

            @Override
            public void onRequestEnd() {
                swipyRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
