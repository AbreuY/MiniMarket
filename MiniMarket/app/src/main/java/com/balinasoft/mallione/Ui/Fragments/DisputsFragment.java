package com.balinasoft.mallione.Ui.Fragments;

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

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Activities.BasketItemsActivity;
import com.balinasoft.mallione.adapters.AdapterDisputs;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.Dispute;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestUserData;
import com.balinasoft.mallione.networking.Response.ResponseDisputes;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

/**
 * Created by Microsoft on 22.07.2016.
 */
public class DisputsFragment extends Basefragment {
    public static final String TAG="DisputsFragment";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    AdapterDisputs adapterDisputs;
    UserListener<? extends User> userListener;
    RequestUserData requestUserData;
    ShowFragmentListener showFragmentListener;
    ToolbarListener toolbarListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            userListener=(UserListener<? extends User>)getActivity();
            showFragmentListener=(ShowFragmentListener)getActivity();
            toolbarListener=(ToolbarListener)getActivity();
            showFragmentListener.showFragmentToolBar(BlurbFragment.TAG,null);
        }catch (ClassCastException e){

        }
        toolbarListener.setTittle(getString(R.string.disputs));
        toolbarListener.closeToolbar();
        adapterDisputs=new AdapterDisputs(getActivity(),new ArrayList<Dispute>());
        adapterDisputs.setClickItemListener(new AdapterDisputs.ClickItemListener() {
            @Override
            public void itemClick(String dispute_id) {
                Intent intent=new Intent(getActivity(), BasketItemsActivity.class);
                intent.putExtra("ID_DISPUTE",dispute_id);
                getActivity().startActivity(intent);
            }

            @Override
            public void itemClick(Dispute dispute) {

            }
        });
        adapterDisputs.setClickOrderListener(new AdapterDisputs.ClickOrderListener() {
            @Override
            public void orderClick(String order_id) {
                Intent intent=new Intent(getActivity(),BasketItemsActivity.class);
                intent.putExtra("ORDER_ID",order_id);
                getActivity().startActivity(intent);
            }
        });
        requestUserData=new RequestUserData(userListener.getUser().getSession_id(),userListener.getUser().getId());
        addDispute();

    }

    private void addDispute() {
        getService().disputs(requestUserData).enqueue(new MyCallbackWithMessageError<ResponseDisputes>() {
            @Override
            public void onData(ResponseDisputes data) {
                adapterDisputs.addItems(data.getResult());
            }

            @Override
            public void onRequestEnd() {
                if(DisputsFragment.this.isResumed()){
                    progressBar.setVisibility(View.INVISIBLE);
                    swipyRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.disputs_fragment,null);
        initView(v);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                requestUserData.setOffset(adapterDisputs.getItemCount());
                swipyRefreshLayout.setRefreshing(true);
                addDispute();
            }
        });
        recyclerView.setAdapter(adapterDisputs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    SwipyRefreshLayout swipyRefreshLayout;

    public void initView(View v){
        recyclerView=(RecyclerView)v.findViewById(R.id.disputsFragment_recyclerView);
        progressBar=(ProgressBar)v.findViewById(R.id.disputsFragment_progressBar);
        swipyRefreshLayout=(SwipyRefreshLayout)v.findViewById(R.id.disputsFragment_swipe);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapterDisputs!=null && adapterDisputs.getItemCount()>0){
            adapterDisputs.clear();
            requestUserData.setOffset(0);
            progressBar.setVisibility(View.VISIBLE);
            addDispute();
        }
    }
}
