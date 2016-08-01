package com.balinasoft.minimarket.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.adapters.ShopsRecyclerAdapter;
import com.balinasoft.minimarket.interfaces.ShowFragmentListener;
import com.balinasoft.minimarket.interfaces.UserListener;
import com.balinasoft.minimarket.models.Category;
import com.balinasoft.minimarket.models.Shops.Shop;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.RequestShops;
import com.balinasoft.minimarket.networking.Response.ResponseShops;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;

import java.util.ArrayList;

/**
 * Created by Microsoft on 02.06.2016.
 */
public class ShopsFragment extends Basefragment {
    public static final String TAG = "ShopFragment";
    RecyclerView recyclerView;
    ShopsRecyclerAdapter shopsRecyclerAdapter;
    RequestShops requestShops;
    ProgressBar progressBar;
    ShowFragmentListener showFragmentListener;
    UserListener<? extends User> userListener;
    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        showFragmentListener = (ShowFragmentListener) getActivity();
        try {
            userListener=(UserListener<? extends User>)getActivity();

        }catch (ClassCastException e){

        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopsRecyclerAdapter = new ShopsRecyclerAdapter(new ArrayList<Shop>(), getActivity()).setOnClickItemListener(new ShopsRecyclerAdapter.OnClickItemListener() {
            @Override
            public void onClikItem(Shop shop) {
                Bundle bundle = new Bundle();
                bundle.putInt("CurrentShopIndex", shopsRecyclerAdapter.getShops().indexOf(shop));
                bundle.putParcelableArrayList("ShopsList", shopsRecyclerAdapter.getShops());
                showFragmentListener.showFragmentToolBar(PagerShopsFragment.TAG,bundle);
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.shops_fragment, null);

        progressBar = (ProgressBar) v.findViewById(R.id.shopsFragment_progressBar);
        recyclerView = (RecyclerView) v.findViewById(R.id.shopsFragment_recyclerView);
        recyclerView.setAdapter(shopsRecyclerAdapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);

        if (getArguments() != null) {
            Category category = getArguments().getParcelable(Category.class.getCanonicalName());
            requestShops = new RequestShops(category.getId());
            if(userListener!=null && userListener.getUser()!=null){
                requestShops.setUser_id(String.valueOf(userListener.getUser().getId()));
                requestShops.setSession_id(String.valueOf(userListener.getUser().getSession_id()));
            }
            getService().shops(requestShops).enqueue(new MyCallback<ResponseShops>() {
                @Override
                public void onData(ResponseShops data) {
                    ArrayList<Shop> shops=new ArrayList<Shop>();
                    shops.addAll(data.getResult());
                    shopsRecyclerAdapter.addShops(shops);
                }

                @Override
                public void onRequestEnd() {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }

        return v;
    }
}
