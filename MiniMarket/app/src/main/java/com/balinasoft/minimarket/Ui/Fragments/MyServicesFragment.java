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
import com.balinasoft.minimarket.adapters.ServiceAdapter;
import com.balinasoft.minimarket.interfaces.ShowFragmentListener;
import com.balinasoft.minimarket.interfaces.ToolbarListener;
import com.balinasoft.minimarket.interfaces.UserListener;
import com.balinasoft.minimarket.models.ProductItems.PreViewProductItem;
import com.balinasoft.minimarket.models.Service;
import com.balinasoft.minimarket.models.Shops.Shop;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.RequestUserData;
import com.balinasoft.minimarket.networking.Response.ResponseRecords;

import java.util.ArrayList;

/**
 * Created by Microsoft on 13.07.2016.
 */
public class MyServicesFragment extends Basefragment{
    public static final String TAG="MyServicesFragment";
    RecyclerView recyclerView;
    ServiceAdapter serviceAdapter;
    UserListener<? extends User> userListener;
    ToolbarListener toolbarListener;
    ShowFragmentListener showFragmentListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userListener=(UserListener<? extends User>)getActivity();
        ShowFragmentListener showFragmentListener=(ShowFragmentListener)getActivity() ;
        showFragmentListener.showFragmentToolBar(BlurbFragment.TAG,null);
        toolbarListener=(ToolbarListener)getActivity();
        toolbarListener.setTittle(getString(R.string.myServices));
        toolbarListener.closeToolbar();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        serviceAdapter=new ServiceAdapter(getActivity(),new ArrayList<Service>());
        serviceAdapter.setOnClickItemListener(new ServiceAdapter.OnClickItemListener() {
            @Override
            public void onShop(Shop shop) {
                Intent intent=new Intent(getActivity(), BasketItemsActivity.class);
                intent.putExtra(Shop.class.getCanonicalName(),shop);
                getActivity().startActivity(intent);
            }

            @Override
            public void onProduct(PreViewProductItem preViewProductItem) {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.my_services_fragment,null);
        initView(v);
        recyclerView.setAdapter(serviceAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        addRecord();
        return v;
    }

    private void addRecord() {
        getService().records(new RequestUserData(userListener.getUser().getSession_id(),userListener.getUser().getId())).enqueue(new MyCallback<ResponseRecords>() {
            @Override
            public void onData(ResponseRecords data) {
                serviceAdapter.addItems(data.getResult());
            }

            @Override
            public void onRequestEnd() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
    ProgressBar progressBar;
    private void initView(View v) {
        recyclerView=(RecyclerView)v.findViewById(R.id.myServicesFragment_RecyclerView);
        progressBar=(ProgressBar)v.findViewById(R.id.myServicesFragment_progressBar);
    }
}
