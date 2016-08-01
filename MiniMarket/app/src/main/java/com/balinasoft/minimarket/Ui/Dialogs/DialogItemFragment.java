package com.balinasoft.minimarket.Ui.Dialogs;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.balinasoft.minimarket.Implementations.Basket;
import com.balinasoft.minimarket.Implementations.MyFavoritesStorage;
import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.Activities.BasketItemsActivity;
import com.balinasoft.minimarket.Utils.AuthManager;
import com.balinasoft.minimarket.adapters.AdapterItemFragment;
import com.balinasoft.minimarket.interfaces.BasketListener;
import com.balinasoft.minimarket.interfaces.UserListener;
import com.balinasoft.minimarket.models.ProductItems.BasketProductItem;
import com.balinasoft.minimarket.models.ProductItems.FullProductItem;
import com.balinasoft.minimarket.models.ProductItems.SuperProductItem;
import com.balinasoft.minimarket.models.Shops.Shop;
import com.balinasoft.minimarket.models.modelUsers.Buer;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.balinasoft.minimarket.networking.ApiFactory;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.RequestComment;
import com.balinasoft.minimarket.networking.Response.ResponseComments;
import com.balinasoft.minimarket.networking.Response.ResponseItem;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.HashMap;

/**
 * Created by Microsoft on 02.07.2016.
 */
public class DialogItemFragment extends FullScreenDialog {
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    public interface ProductListener {
        SuperProductItem getProduct();
    }

    ProductListener productListener;
    RecyclerView recyclerView;
    AdapterItemFragment adapterItemFragment;
    FullProductItem productItem;
    SwipyRefreshLayout swipyRefreshLayout;
    RequestComment requestComment;
    UserListener<? extends User> userListener;

    ProgressBar progressBar;
    int amountProduct;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            userListener = (UserListener<? extends User>) getActivity();
        } catch (ClassCastException e) {

        }

    }
    Button btnCategory;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.item_dialog, container);
        btnCategory=(Button)v.findViewById(R.id.itemDialog_btnCategory);
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                getActivity().onBackPressed();
            }
        });
        recyclerView = (RecyclerView) v.findViewById(R.id.itemDialog_recyclerView);
        swipyRefreshLayout = (SwipyRefreshLayout) v.findViewById(R.id.itemDialog_swipyRefreshLayout);
        progressBar = (ProgressBar) v.findViewById(R.id.itemDialog_progressBar);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                swipyRefreshLayout.setRefreshing(true);
                addComment(true);
            }
        });
        requestComment = new RequestComment(productListener.getProduct().getId());
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put("item_id", String.valueOf(productListener.getProduct().getId()));

        ApiFactory.getService().item(hashMap).enqueue(new MyCallback<ResponseItem>() {
            @Override
            public void onData(ResponseItem data) {
                productItem = data.getResult();
                if (productListener != null && productListener.getProduct().getClass() == BasketProductItem.class)
                    adapterItemFragment = new AdapterItemFragment(productItem, getActivity(), getChildFragmentManager(),((BasketProductItem)productListener.getProduct()).getAmountProduct() );
               else  adapterItemFragment = new AdapterItemFragment(productItem, getActivity(), getChildFragmentManager(),0);

                adapterItemFragment.setUser(user);
                adapterItemFragment.setBasketListener(basketListener);
                try {
                    new MyFavoritesStorage().isFavorites(productItem, new MyFavoritesStorage.Callback() {
                        @Override
                        public void onSuccess() {
                            adapterItemFragment.setLike(true);
                        }

                        @Override
                        public void onFailure() {
                            adapterItemFragment.setLike(false);
                        }
                    });
                } catch (IllegalArgumentException e) {

                }
                initListenersAdapter();
                recyclerView.setAdapter(adapterItemFragment);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setHasFixedSize(true);

                addComment(false);

            }

            @Override
            public void onRequestEnd() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        return v;
    }

    GestureViewDialog gestureViewDialog = new GestureViewDialog();

    private void initListenersAdapter() {
        adapterItemFragment.setBackListener(new AdapterItemFragment.BackListener() {
            @Override
            public void onBackPress() {
                dismiss();
            }
        });
        adapterItemFragment.setClickPageListener(new AdapterItemFragment.ClickPageListener() {
            @Override
            public void onClick(final String urlImage) {
                if (!gestureViewDialog.isAdded())
                    gestureViewDialog.setUrlListener(new GestureViewDialog.UrlListener() {
                        @Override
                        public String getUrl() {
                            return urlImage;
                        }
                    }).show(getFragmentManager(), "");
            }
        });

        adapterItemFragment.setBasketListener(basketListener);
        adapterItemFragment.setClickLikeListener(new AdapterItemFragment.ClickLikeListener() {
            @Override
            public boolean onClick(SuperProductItem productItem) {
                try {
                    new MyFavoritesStorage().put(productItem, productItem.getShop_id());
                } catch (IllegalArgumentException e) {
                    new DialogLogin().setLoginUserListener(new DialogLogin.StartActivityLogin(getActivity())).show(getFragmentManager(), "");
                }

                return true;
            }

            @Override
            public boolean unClick(SuperProductItem productItem) {
                try {
                    new MyFavoritesStorage().delete(productItem, productItem.getShop_id());
                } catch (IllegalArgumentException e) {
                    new DialogLogin().setLoginUserListener(new DialogLogin.StartActivityLogin(getActivity())).show(getFragmentManager(), "");
                }
                return true;
            }
        });
        adapterItemFragment.setEnrollListener(new AdapterItemFragment.EnrollListener() {
            @Override
            public void onClickEnroll(final SuperProductItem superProductItem) {
                if (userListener != null) {
                    new DialogSelectTime().setProductListener(new ProductListener() {
                        @Override
                        public SuperProductItem getProduct() {
                            return superProductItem;
                        }
                    }).show(getFragmentManager(), "");
                } else {
                    new DialogLogin().setLoginUserListener(new DialogLogin.StartActivityLogin(getActivity(), superProductItem)).show(getFragmentManager(), "");
                }
            }
        });
        adapterItemFragment.setClickShopListener(new AdapterItemFragment.ClickShopListener() {
            @Override
            public boolean onClick(Shop shop) {
                Intent intent = new Intent(getActivity(), BasketItemsActivity.class);
                intent.putExtra(Shop.class.getCanonicalName(), shop);
                getActivity().startActivity(intent);
                return true;
            }
        });
    }

    public void setProductListener(ProductListener productListener) {
        this.productListener = productListener;
    }

    public void addComment(final boolean isScroll) {
        ApiFactory.getService().comments(requestComment).enqueue(new MyCallback<ResponseComments>() {
            @Override
            public void onData(ResponseComments data) {

                int currentSize = adapterItemFragment.getItemCount() - 1;

                adapterItemFragment.addComments(data.getResult());

                if (data.getResult().size() > 0)
                    requestComment.setOffset(requestComment.getOffset() + data.getResult().size());


                if (data.getResult().size() > 0 && isScroll)
                    recyclerView.smoothScrollToPosition(currentSize + 1);
            }

            @Override
            public void onRequestEnd() {
                swipyRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        swipyRefreshLayout = null;
        progressBar = null;
        recyclerView = null;
        productListener = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        adapterItemFragment = null;
        requestComment = null;
        basketListener = null;

    }

    BasketListener basketListener = new BasketListener() {
        @Override
        public void onPutBAsket(final SuperProductItem superProductItem) {
            if (adapterItemFragment.getUser() == null) {
                new DialogLogin().setLoginUserListener(new DialogLogin.StartActivityLogin(getActivity(), superProductItem)).show(getActivity().getSupportFragmentManager(), "");
            } else {
                if (superProductItem.getClass() == BasketProductItem.class) {
                    BasketProductItem item = ((BasketProductItem) superProductItem);
                    item.setAmountProduct(item.getAmountProduct() + 1);
                    new Basket().put(superProductItem, superProductItem.getShop_id());
                }

            }
            adapterItemFragment.notifyItemChanged(2);

        }
    };

    public void setBasketListener(BasketListener basketListener) {
        this.basketListener = basketListener;
    }
}
