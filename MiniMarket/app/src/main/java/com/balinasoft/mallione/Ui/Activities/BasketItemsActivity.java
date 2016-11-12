package com.balinasoft.mallione.Ui.Activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Fragments.BankFragment;
import com.balinasoft.mallione.Ui.Fragments.CheckOutFragment;
import com.balinasoft.mallione.Ui.Fragments.DisputeDetailsFragment;
import com.balinasoft.mallione.Ui.Fragments.ItemsBasketFragment;
import com.balinasoft.mallione.Ui.Fragments.OrderDetailedFragment;
import com.balinasoft.mallione.Ui.Fragments.SendDisputeFragment;
import com.balinasoft.mallione.Ui.Fragments.ShopInformation;
import com.balinasoft.mallione.Utils.AuthManager;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.FireBase.ProductFire;
import com.balinasoft.mallione.models.Order;
import com.balinasoft.mallione.models.Shops.Shop;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.ApiFactory;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Response.ResponseShop;

import java.util.ArrayList;
import java.util.HashMap;

public class BasketItemsActivity extends AppCompatActivity implements ShowFragmentListener, UserListener<User>, ToolbarListener {
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket_items);
        initView();
        new AuthManager(this).setExtractListener(new AuthManager.SimpleExtractListener() {
            @Override
            public void onEmpty() {

            }

            @Override
            public void onUser(User user) {
                BasketItemsActivity.this.user = user;
            }
        }).extract();
        if (getIntent().getParcelableExtra("DISPUTE_ORDER") != null) {
            Order order = getIntent().getParcelableExtra("DISPUTE_ORDER");
            Bundle bundle = new Bundle();
            bundle.putParcelable(Order.class.getCanonicalName(), order);
            showFragment(SendDisputeFragment.TAG, bundle, false);
        }
        if (getIntent().getParcelableArrayListExtra("fireProduct") != null) {
            ArrayList<ProductFire> productFires = getIntent().getParcelableArrayListExtra("fireProduct");
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList("fireProduct", productFires);
            bundle.putString("ORDER_ID", getIntent().getStringExtra("ORDER_ID"));
            showFragment(ItemsBasketFragment.TAG, bundle, false);
        }
        if (getIntent().getStringExtra("ID_DISPUTE") != null) {
            Bundle bundle = new Bundle();
            bundle.putString("ID_DISPUTE", getIntent().getStringExtra("ID_DISPUTE"));

            showFragment(DisputeDetailsFragment.TAG, bundle, false);
        }
        if (getIntent().getStringExtra("ORDER_ID") != null) {
            Bundle bundle = new Bundle();
            bundle.putString("ORDER_ID", getIntent().getStringExtra("ORDER_ID"));
//            bundle.putParcelable(order)
            showFragment(OrderDetailedFragment.TAG, bundle, false);
        }

        if (getIntent().getParcelableExtra(Order.class.getCanonicalName()) != null) {

            ///  ApiFactory.getService().getFullOrder();

            Order order = getIntent().getParcelableExtra(Order.class.getCanonicalName());
            Bundle bundle = new Bundle();
            bundle.putParcelable(Order.class.getCanonicalName(), order);
            showFragment(OrderDetailedFragment.TAG, bundle, false);
        }
        if (getIntent().getParcelableExtra(Shop.class.getCanonicalName()) != null) {
            Shop shop = getIntent().getParcelableExtra(Shop.class.getCanonicalName());

            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("shop_id", String.valueOf(shop.getId()));
            ApiFactory.getService().shop(hashMap).enqueue(new MyCallbackWithMessageError<ResponseShop>() {
                @Override
                public void onData(ResponseShop data) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Shop.class.getCanonicalName(), data.getResult());
                    showFragment(ShopInformation.TAG, bundle, false);
                }

                @Override
                public void onRequestEnd() {

                }
            });
        }

    }

    Toolbar toolbar;

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.activityBacketItem_toolBar);
        setSupportActionBar(toolbar);
        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);

        upArrow.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        toolbar.setNavigationIcon(upArrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }


    @Override
    public void showCollapseFragment(String fragmentTag, Bundle data, boolean backStack) {

    }

    @Override
    public void showFragmentToolBar(String fragmentTag, Bundle data) {

    }

    @Override
    public void showFragment(String fragmentTag, Bundle data, boolean backStack) {
        Fragment fragment = null;
        switch (fragmentTag) {
            case ItemsBasketFragment.TAG:
                fragment = new ItemsBasketFragment();
                break;
            case CheckOutFragment.TAG:
                fragment = new CheckOutFragment();
                break;
            case OrderDetailedFragment.TAG:
                fragment = new OrderDetailedFragment();
                break;
            case ShopInformation.TAG:
                fragment = new ShopInformation();
                break;
            case DisputeDetailsFragment.TAG:
                fragment = new DisputeDetailsFragment();
                break;
            case SendDisputeFragment.TAG:
                fragment = new SendDisputeFragment();
                break;
            case BankFragment.TAG:
                fragment = new BankFragment();
                break;
        }
        if (data != null) {
            fragment.setArguments(data);
        }
        if (backStack)
            getSupportFragmentManager().beginTransaction().replace(R.id.activityBacketItem_frameConteiner, fragment).addToBackStack(null).commit();
        else
            getSupportFragmentManager().beginTransaction().replace(R.id.activityBacketItem_frameConteiner, fragment).commit();
    }

    @Override
    public void onBackPressed() {
//        FragmentManager fm = getSupportFragmentManager();
//        OnBackPressedListener backPressedListener = null;
//        for (Fragment fragment : fm.getFragments()) {
//            if (fragment instanceof OnBackPressedListener) {
//                backPressedListener = (OnBackPressedListener) fragment;
//                break;
//            }
//        }
//        int check = 0;
//        if (backPressedListener != null) {
//            check = backPressedListener.onBackPressed();
//        }
//        if (check == 1) {
            super.onBackPressed();
//        }
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public void setTittle(String tittle) {
        getSupportActionBar().setTitle(tittle);
    }

    @Override
    public void setSubTittle(String subTittle) {

    }

    @Override
    public void openToolbar() {

    }

    @Override
    public void closeToolbar() {

    }
}
