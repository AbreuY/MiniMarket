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

import com.balinasoft.mallione.Implementations.Basket;
import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Activities.BasketItemsActivity;
import com.balinasoft.mallione.adapters.ShopBasketAdapter;
import com.balinasoft.mallione.adapters.ShopsRecyclerAdapter;
import com.balinasoft.mallione.interfaces.MenuListener;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.TransactionListener;
import com.balinasoft.mallione.models.FireBase.ProductFire;
import com.balinasoft.mallione.models.Shops.Shop;
import com.balinasoft.mallione.models.Shops.ShopBasket;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestShops;
import com.balinasoft.mallione.networking.Response.ResponseShops;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Microsoft on 04.07.2016.
 */
public class BasketFragment extends Basefragment {
    public static final String TAG = "BasketFragment";
    HashMap<String, HashMap<String, ProductFire>> listShops;
    ShopBasketAdapter shopBasketAdapter;
    ToolbarListener toolbarListener;
    ShowFragmentListener showFragmentListener;
    MenuListener menuListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarListener = (ToolbarListener) getActivity();
        toolbarListener.closeToolbar();
        showFragmentListener = (ShowFragmentListener) getActivity();
        showFragmentListener.showFragmentToolBar(BlurbFragment.TAG,null);


        menuListener = (MenuListener) getActivity();
        menuListener.addNotification(getString(R.string.basket), 0);
        toolbarListener.setTittle(getString(R.string.basket));
    }

    Basket basket;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        shopBasketAdapter = new ShopBasketAdapter(getActivity(), new ArrayList<ShopBasket>());
        shopBasketAdapter.setOnClickItemListener(new ShopsRecyclerAdapter.OnClickItemListener() {
            @Override
            public void onClikItem(Shop shop) {

                ArrayList<ProductFire> productFires = new ArrayList<ProductFire>();
                for (String s : listShops.get(String.valueOf(shop.getId())).keySet()) {
                    productFires.add(listShops.get(String.valueOf(shop.getId())).get(s));
                }
                Intent intent=new Intent(getActivity(), BasketItemsActivity.class);
                intent.putParcelableArrayListExtra("fireProduct",productFires);
                getActivity().startActivity(intent);
            }
        });
        basket = new Basket().setTransactionListener(new TransactionListener<HashMap<String, HashMap<String, ProductFire>>>() {
            @Override
            public HashMap<String, HashMap<String, ProductFire>> getList() {
                return null;
            }

            @Override
            public void onList(final HashMap<String, HashMap<String, ProductFire>> list) {
                listShops = list;
                final List<String> strings = new ArrayList<String>();
                strings.addAll(list.keySet());
                if (strings.size() > 0)
                    getService().shops(new RequestShops(strings)).enqueue(new MyCallbackWithMessageError<ResponseShops>() {
                        @Override
                        public void onData(ResponseShops data) {
                            for (ShopBasket shopBasket : data.getResult()) {
                                for (String s : list.get(shopBasket.getIdToStrind()).keySet()) {
                                    shopBasket.addAmountProduct(list.get(shopBasket.getIdToStrind()).get(s).getAmount());
                                }
                            }
                            shopBasketAdapter.addItems(data.getResult());

                        }

                        @Override
                        public void onRequestEnd() {
                            progressBar.setVisibility(View.INVISIBLE);
                        }
                    });
                else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        }).getDataBasket();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.basket_fragment, null);
        initView(v);
        recyclerView.setAdapter(shopBasketAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    RecyclerView recyclerView;
    ProgressBar progressBar;

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.basketFragment_recyclerView);
        progressBar = (ProgressBar) v.findViewById(R.id.basketFragment_progressBar);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (shopBasketAdapter.getItemCount() > 0) {
            shopBasketAdapter.clear();
            basket.getDataBasket();
        }


    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
