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

import com.balinasoft.minimarket.Implementations.Basket;
import com.balinasoft.minimarket.Implementations.MyFavoritesStorage;
import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.Dialogs.DialogItemFragment;
import com.balinasoft.minimarket.adapters.ItemsAdapterRecyclerView;
import com.balinasoft.minimarket.interfaces.BasketListener;
import com.balinasoft.minimarket.interfaces.ShowFragmentListener;
import com.balinasoft.minimarket.interfaces.ToolbarListener;
import com.balinasoft.minimarket.interfaces.TransactionListener;
import com.balinasoft.minimarket.models.FireBase.ProductFire;
import com.balinasoft.minimarket.models.ProductItems.BasketProductItem;
import com.balinasoft.minimarket.models.ProductItems.PreViewProductItem;
import com.balinasoft.minimarket.models.ProductItems.SuperProductItem;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.RequestItems;
import com.balinasoft.minimarket.networking.Response.ResponseBasketItems;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Microsoft on 04.07.2016.
 */
public class MyFavoritesFragment extends Basefragment {
    public static final String TAG = "MyFavoritesFragment";
    ItemsAdapterRecyclerView itemsAdapterRecyclerView;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    DialogItemFragment dialogItemFragment = new DialogItemFragment();
    ToolbarListener toolbarListener;
    ShowFragmentListener showFragmentListener;
    BasketListener basketListener = new BasketListener() {
        @Override
        public void onPutBAsket(SuperProductItem superProductItem) {
            if (superProductItem instanceof BasketProductItem) {
                BasketProductItem item = (BasketProductItem) superProductItem;
                item.setAmountProduct(item.getAmountProduct() + 1);
            }
            new Basket().put(superProductItem, superProductItem.getShop_id());

        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarListener = (ToolbarListener) getActivity();
        toolbarListener.closeToolbar();
        toolbarListener.setTittle(getString(R.string.myFavorites));
        showFragmentListener =(ShowFragmentListener)getActivity();
        showFragmentListener.showFragmentToolBar(BlurbFragment.TAG,null);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        itemsAdapterRecyclerView = new ItemsAdapterRecyclerView(getActivity(), new ArrayList<PreViewProductItem>(), R.layout.item_item_favorites);
        itemsAdapterRecyclerView.setBasketListener(basketListener);
        itemsAdapterRecyclerView.setDeleteItemListener(new ItemsAdapterRecyclerView.DeleteItemListener() {
            @Override
            public void onDelete(SuperProductItem productItem) {
                myFavoritesStorage.delete(productItem, "");
                itemsAdapterRecyclerView.deleteItem(productItem);
            }
        });
        itemsAdapterRecyclerView.setClickItemListener(new ItemsAdapterRecyclerView.ClickItemListener() {
            @Override
            public void onClick(final SuperProductItem productItem) {
                dialogItemFragment.setBasketListener(basketListener);
                dialogItemFragment.setProductListener(new DialogItemFragment.ProductListener() {
                    @Override
                    public SuperProductItem getProduct() {
                        return productItem;
                    }
                });
                if (!dialogItemFragment.isAdded())
                    dialogItemFragment.show(getFragmentManager(), "");
            }
        });
        myFavoritesStorage = new MyFavoritesStorage();

    }

    MyFavoritesStorage myFavoritesStorage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.my_favorites_fragment, null);
        initView(v);
        myFavoritesStorage.setTransactionListener(new TransactionListener<List<ProductFire>>() {
            @Override
            public List<ProductFire> getList() {
                return null;
            }

            @Override
            public void onList(List<ProductFire> list) {
                ArrayList<Integer> integers = new ArrayList<Integer>();
                for (ProductFire productFire : list) {
                    integers.add(productFire.getId());
                }
                RequestItems requestItem = new RequestItems(integers);
                requestItem.setLimit(integers.size());

                getService().itemsBasket(requestItem).enqueue(new MyCallback<ResponseBasketItems>() {


                    @Override
                    public void onData(final ResponseBasketItems data) {
                        for (BasketProductItem item : data.getResult()) {
                            new Basket().checkProduct(item, new Basket.SingleValueListener.Callback() {
                                @Override
                                public void onCheckProduct(BasketProductItem productItem) {
                                    itemsAdapterRecyclerView.addItem(productItem);
                                    if (data.getResult().size() == itemsAdapterRecyclerView.getItemCount()) {
                                        itemsAdapterRecyclerView.notifyDataSetChanged();
                                    }
                                }
                            });
                        }

                    }

                    @Override
                    public void onRequestEnd() {
                        if (MyFavoritesFragment.this.isAdded())
                            progressBar.setVisibility(View.INVISIBLE);
                    }
                });

            }
        }).getListId();
        recyclerView.setAdapter(itemsAdapterRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setNestedScrollingEnabled(false);
        return v;
    }

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.favorites_recyclerView);
        progressBar = (ProgressBar) v.findViewById(R.id.favorites_progressBar);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
