package com.balinasoft.mallione.Ui.Dialogs;

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

import com.balinasoft.mallione.Implementations.Basket;
import com.balinasoft.mallione.Implementations.MyFavoritesStorage;
import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Activities.BasketItemsActivity;
import com.balinasoft.mallione.adapters.AdapterItemFragment;
import com.balinasoft.mallione.interfaces.BasketListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.ProductItems.BasketProductItem;
import com.balinasoft.mallione.models.ProductItems.FullProductItem;
import com.balinasoft.mallione.models.ProductItems.SuperProductItem;
import com.balinasoft.mallione.models.Shops.Shop;
import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.ApiFactory;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestComment;
import com.balinasoft.mallione.networking.Request.RequestItem;
import com.balinasoft.mallione.networking.Response.ResponseComments;
import com.balinasoft.mallione.networking.Response.ResponseItem;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

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

    Button btnReturnOrAddComment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.item_dialog, container);
        btnReturnOrAddComment = (Button) v.findViewById(R.id.itemDialog_btnCategory);
        btnReturnOrAddComment.setOnClickListener(new View.OnClickListener() {
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

        try {
            userListener = (UserListener<Buer>) getActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        requestComment = new RequestComment(productListener.getProduct().getId());
//        HashMap<String, String> hashMap = new HashMap<>();

//        hashMap.put("item_id", String.valueOf(productListener.getProduct().getId()));

        RequestItem requestItem = new RequestItem();
        requestItem.setItem_id(String.valueOf(productListener.getProduct().getId()));
        if (userListener != null) {
            requestItem.setUser_id(String.valueOf(userListener.getUser().getId()));
            requestItem.setSession_id(String.valueOf(userListener.getUser().getSession_id()));
        }

        ApiFactory.getService().item(requestItem).enqueue(new MyCallbackWithMessageError<ResponseItem>() {
            @Override
            public void onData(final ResponseItem data) {
                if (data.getResult().isCommented()) {
                    btnReturnOrAddComment.setText(R.string.addComment);
                    btnReturnOrAddComment.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            new AssessDialog().setTypeAssess(AssessDialog.ORDER)
                                    .setItemId(String.valueOf(data.getResult().getId()))
                                    .show(getFragmentManager(), "");
                        }
                    });
                } else {
                    btnReturnOrAddComment.setText(R.string.backCategory);
                }

                productItem = data.getResult();
                if (productListener != null && productListener.getProduct().getClass() == BasketProductItem.class)
                    adapterItemFragment = new AdapterItemFragment(productItem, getActivity(), getChildFragmentManager(), ((BasketProductItem) productListener.getProduct()).getAmountProduct());
                else
                    adapterItemFragment = new AdapterItemFragment(productItem, getActivity(), getChildFragmentManager(), 0);

                adapterItemFragment.setUser(user);
                adapterItemFragment.setBasketListener(basketListener);
                try {
                    new MyFavoritesStorage().isFavorites(productItem, new MyFavoritesStorage.Callback() {
                        @Override
                        public void onSuccess() {
                            if (adapterItemFragment != null) {
                                adapterItemFragment.setLike(true);
                            }
                        }

                        @Override
                        public void onFailure() {
                            if (adapterItemFragment != null) {
                                adapterItemFragment.setLike(false);
                            }
                        }
                    });
                } catch (IllegalArgumentException e) {

                }
                initListenersAdapter();
                if (recyclerView != null) {
                    recyclerView.setAdapter(adapterItemFragment);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setHasFixedSize(true);
                }

                addComment(false);

            }

            @Override
            public void onRequestEnd() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.INVISIBLE);
                }
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
        ApiFactory.getService().comments(requestComment).enqueue(new MyCallbackWithMessageError<ResponseComments>() {
            @Override
            public void onData(ResponseComments data) {
                if (adapterItemFragment != null) {

                    int currentSize = adapterItemFragment.getItemCount() - 1;

                    adapterItemFragment.addComments(data.getResult());

                    if (data.getResult().size() > 0)
                        requestComment.setOffset(requestComment.getOffset() + data.getResult().size());


                    if (data.getResult().size() > 0 && isScroll)
                        recyclerView.smoothScrollToPosition(currentSize + 1);
                }
            }

            @Override
            public void onRequestEnd() {
                if (swipyRefreshLayout != null) {
                    swipyRefreshLayout.setRefreshing(false);
                }
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
