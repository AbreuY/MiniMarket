package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.balinasoft.mallione.Implementations.Basket;
import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Dialogs.DialogItemFragment;
import com.balinasoft.mallione.Ui.Dialogs.TextDialog;
import com.balinasoft.mallione.adapters.ItemBasketAdapter;
import com.balinasoft.mallione.adapters.ItemsAdapterRecyclerView;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.models.FireBase.ProductFire;
import com.balinasoft.mallione.models.ProductItems.BasketProductItem;
import com.balinasoft.mallione.models.ProductItems.SuperProductItem;
import com.balinasoft.mallione.networking.ApiFactory;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestItems;
import com.balinasoft.mallione.networking.Response.ResponseBasketItems;

import java.util.ArrayList;


public class ItemsBasketFragment extends Basefragment {

    public static final String TAG = "ItemsBasketFragment";

    RecyclerView recyclerView;
    ProgressBar progressBar;
    ImageView ivBack;
    private Button btnOrder;
    DialogItemFragment dialogItemFragment = new DialogItemFragment();
    ItemBasketAdapter itemBasketAdapter;
    RequestItems requestItems = new RequestItems(new ArrayList<Integer>());
    TextDialog dialogText = new TextDialog();
    ToolbarListener toolbarListener;
    ShowFragmentListener showFragmentListener;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarListener=(ToolbarListener)getActivity();
        toolbarListener.setTittle(getString(R.string.basket));
        showFragmentListener=(ShowFragmentListener)getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogText = new TextDialog();
        dialogText.setText(getString(R.string.deleteItem));
        itemBasketAdapter = new ItemBasketAdapter(getActivity());

        itemBasketAdapter.setControlAmountListener(new ItemBasketAdapter.ControlAmountListener() {
            @Override
            public void add(BasketProductItem basketProductItem) {
                new Basket().put(basketProductItem, basketProductItem.getShop_id());
            }

            @Override
            public void delete(BasketProductItem basketProductItem) {
                new Basket().put(basketProductItem, basketProductItem.getShop_id());

            }

            @Override
            public void requestDelete(final BasketProductItem basketProductItem) {
                dialogText.setClickListener(new TextDialog.ClickListener<BasketProductItem>() {
                    @Override
                    public void onClickPositive(BasketProductItem productItem) {
                        new Basket().delete(productItem, productItem.getShop_id());
                        itemBasketAdapter.delete(productItem);

                        if(itemBasketAdapter.getItemCount()==0){
                            getActivity().onBackPressed();
                        }

                    }

                    @Override
                    public void onClickNegative() {

                    }

                    @Override
                    public BasketProductItem getData() {
                        return basketProductItem;
                    }
                }).show(getFragmentManager(), "");
            }
        });
        itemBasketAdapter.setClickItemListener(new ItemsAdapterRecyclerView.ClickItemListener() {
            @Override
            public void onClick(final SuperProductItem productItem) {
                dialogItemFragment.setProductListener(new DialogItemFragment.ProductListener() {
                    @Override
                    public SuperProductItem getProduct() {
                        return productItem;
                    }
                });
                dialogItemFragment.show(getFragmentManager(), "");
            }
        });
        itemBasketAdapter.setAmountShangeListener(new ItemBasketAdapter.AmountShangeListener() {
            @Override
            public void onChangeAmount() {
                calculatePrice();
            }
        });
        ArrayList<ProductFire> productFires = getArguments().getParcelableArrayList("fireProduct");
        for (ProductFire productFire : productFires) {
            requestItems.addItemsId(productFire.getId());
        }

        ApiFactory.getService().itemsBasket(requestItems).enqueue(new MyCallbackWithMessageError<ResponseBasketItems>() {
            @Override
            public void onData(ResponseBasketItems data) {
                new Basket().checkBasket(data.getResult(), new Basket.MyValueEventListener.CallBack() {
                    @Override
                    public void onCheckProduct(ArrayList<BasketProductItem> productItem) {
                        itemBasketAdapter.addItems(productItem);
                        calculatePrice();
                    }
                });

            }

            @Override
            public void onRequestEnd() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.item_basket_fragment, null);
        initView(v);
        recyclerView.setAdapter(itemBasketAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        return v;
    }

    private void initView(View v) {
        progressBar = (ProgressBar) v.findViewById(R.id.activityBacketItem_progressBar);
        recyclerView = (RecyclerView) v.findViewById(R.id.activityBacketItem_recyclerView);

        btnOrder = (Button) v.findViewById(R.id.activityBacketItem_btnOrder);
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<BasketProductItem> basketProductItems = new ArrayList<BasketProductItem>();
                basketProductItems.addAll(itemBasketAdapter.getProductItems());

                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("ProductsOrder",basketProductItems);

                showFragmentListener.showFragment(CheckOutFragment.TAG,bundle,false);
            }
        });

    }

    public Double calculatePrice() {
        Double price = 0d;
        for (BasketProductItem p : itemBasketAdapter.getProductItems()) {
            price += Double.valueOf(p.getPrice()) * p.getAmountProduct();
        }
        btnOrder.setText(String.format("%s | %.2f", getString(R.string.order), price));
        return price;
    }
}
