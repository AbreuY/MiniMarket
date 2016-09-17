package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.balinasoft.mallione.Implementations.Basket;
import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Dialogs.DialogItemFragment;
import com.balinasoft.mallione.Ui.Dialogs.DialogLogin;
import com.balinasoft.mallione.Ui.Dialogs.DialogSelectTime;
import com.balinasoft.mallione.Utils.AuthManager;
import com.balinasoft.mallione.adapters.ItemsAdapterRecyclerView;
import com.balinasoft.mallione.interfaces.BasketListener;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.ProductItems.BasketProductItem;
import com.balinasoft.mallione.models.ProductItems.PreViewProductItem;
import com.balinasoft.mallione.models.ProductItems.SuperProductItem;
import com.balinasoft.mallione.models.Shops.Shop;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestItems;
import com.balinasoft.mallione.networking.Response.ResponseBasketItems;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

/**
 * Created by Microsoft on 03.06.2016.
 */
public class ItemsFragment extends Basefragment {
    public static final String TAG = "ItemsFragment";
    RecyclerView recyclerView;
    ProgressBar progressBar;
    Shop shop;
    ItemsAdapterRecyclerView adapterRecyclerView;
    DialogLogin dialogLogin;
    ShowFragmentListener showFragmentListener;
    DialogItemFragment dialogItemFragment = new DialogItemFragment();
    UserListener<? extends User> userListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        showFragmentListener = (ShowFragmentListener) getActivity();
        try {
            userListener = (UserListener<? extends User>) getActivity();
        } catch (ClassCastException e) {
            userListener = new UserListener<User>() {
                @Override
                public User getUser() {
                    return null;
                }
            };
        }

    }

    BasketListener basketListener = new BasketListener() {
        @Override
        public void onPutBAsket(final SuperProductItem superProductItem) {
            if (adapterRecyclerView.getUser() == null) {
                new AuthManager(getActivity()).setExtractListener(new AuthManager.SimpleExtractListener() {

                    @Override
                    public void onEmpty() {
                        dialogLogin = new DialogLogin().setLoginUserListener(new DialogLogin.StartActivityLogin(getActivity(), superProductItem));
                        dialogLogin.show(getActivity().getSupportFragmentManager(), "");
                    }

                    @Override
                    public void onUser(User user) {
                        adapterRecyclerView.setUser(user);

                    }
                }).extract();
            } else {
                if (superProductItem instanceof BasketProductItem) {
                    BasketProductItem item = ((BasketProductItem) superProductItem);
                    item.setAmountProduct(item.getAmountProduct() + 1);
                }
                new Basket().put(superProductItem, superProductItem.getShop_id());
            }
        }
    };
    DialogSelectTime dialogSelectTime = new DialogSelectTime();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        adapterRecyclerView = new ItemsAdapterRecyclerView(getActivity(), new ArrayList<PreViewProductItem>(), R.layout.item_item);
        if (userListener != null) {
            adapterRecyclerView.setUser(userListener.getUser());
        }
        if (isPesentSwipe()) {
            getSwipe().setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh(SwipyRefreshLayoutDirection direction) {
                    addItems(shop);
                }
            });
        }
        adapterRecyclerView.setBasketListener(basketListener);
        adapterRecyclerView.setEnrollListener(new ItemsAdapterRecyclerView.EnrollListener() {
            @Override
            public void onEnroll(final SuperProductItem productItem) {
                if (adapterRecyclerView.getUser() == null) {
                    new AuthManager(getActivity()).setExtractListener(new AuthManager.SimpleExtractListener() {

                        @Override
                        public void onEmpty() {
                            dialogLogin = new DialogLogin().setLoginUserListener(new DialogLogin.StartActivityLogin(getActivity(), productItem));
                            dialogLogin.show(getActivity().getSupportFragmentManager(), "");
                        }

                        @Override
                        public void onUser(User user) {
                            adapterRecyclerView.setUser(user);

                        }
                    }).extract();
                } else {
                    dialogSelectTime.setProductListener(new DialogItemFragment.ProductListener() {
                        @Override
                        public SuperProductItem getProduct() {
                            return productItem;
                        }
                    });
                    dialogSelectTime.setUserUserListener(new UserListener<User>() {
                        @Override
                        public User getUser() {
                            return adapterRecyclerView.getUser();
                        }
                    });
                    dialogSelectTime.setTitle(getString(R.string.enroll));
                    dialogSelectTime.setSubTitle(getString(R.string.selectTimeEnroll));
                    dialogSelectTime.show(getActivity().getSupportFragmentManager(), "");
                }
            }
        });
        adapterRecyclerView.setClickItemListener(new ItemsAdapterRecyclerView.ClickItemListener() {
            @Override
            public void onClick(final SuperProductItem productItem) {
//                Bundle bundle=new Bundle();
//                bundle.putParcelable(SuperProductItem.class.getCanonicalName(),productItem);
//                showFragmentListener.showFragmentToolBar(PagerItemFragment.TAG,bundle);
                if (!dialogItemFragment.isAdded()) {


                    dialogItemFragment.setProductListener(new DialogItemFragment.ProductListener() {
                        @Override
                        public SuperProductItem getProduct() {
                            return productItem;
                        }
                    });

                    dialogItemFragment.setUser(userListener.getUser());

                    dialogItemFragment.show(getChildFragmentManager(), "");
                }
            }
        });
        if (getArguments() != null) {
            shop = getArguments().getParcelable(Shop.class.getCanonicalName());
            requestItems = new RequestItems(shop.getId());
            addItems(shop);
        }

    }

    RequestItems requestItems;

    void addItems(Shop shop) {
        getService().itemsBasket(requestItems).enqueue(new MyCallbackWithMessageError<ResponseBasketItems>() {
            @Override
            public void onData(final ResponseBasketItems data) {
                try {
                    new Basket().checkBasket(data.getResult(), new Basket.MyValueEventListener.CallBack() {
                        @Override
                        public void onCheckProduct(ArrayList<BasketProductItem> productItem) {
                            adapterRecyclerView.addItems(productItem);
                            requestItems.setOffset(adapterRecyclerView.getItemCount());

                        }
                    });
                } catch (IllegalArgumentException e) {
                    adapterRecyclerView.addItems(data.getResult());
                    requestItems.setOffset(adapterRecyclerView.getItemCount());
                }

            }

            @Override
            public void onRequestEnd() {
                progressBar.setVisibility(View.INVISIBLE);
                if (isPesentSwipe()) {
                    getSwipe().setRefreshing(false);
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.items_fragment, null);
        recyclerView = (RecyclerView) v.findViewById(R.id.itemsFragment_recyclerView);
        recyclerView.setAdapter(adapterRecyclerView);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
        progressBar = (ProgressBar) v.findViewById(R.id.itemsFragment_progressBar);
        return v;
    }


}
