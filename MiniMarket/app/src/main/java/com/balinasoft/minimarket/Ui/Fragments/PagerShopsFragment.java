package com.balinasoft.minimarket.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.adapters.MyFragmentPagerAdapter;
import com.balinasoft.minimarket.interfaces.ShowFragmentListener;
import com.balinasoft.minimarket.interfaces.ToolbarListener;
import com.balinasoft.minimarket.models.Shops.Shop;

import java.util.ArrayList;

/**
 * Created by Microsoft on 02.06.2016.
 */
public class PagerShopsFragment extends Basefragment {

    public static final String TAG = "PagerShopsFragment";
    ViewPager viewPager;
    MyFragmentPagerAdapter pagerAdapter;
    int currentItem;
    private ShowFragmentListener showFragmentListener;
    ToolbarListener toolbarListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        showFragmentListener = (ShowFragmentListener) getActivity();
        toolbarListener = (ToolbarListener) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), new ArrayList<Fragment>());
        if (getArguments() != null) {
            ArrayList<Shop> shops = getArguments().getParcelableArrayList("ShopsList");
            currentItem = getArguments().getInt("CurrentShopIndex");
            shop=shops.get(currentItem);
            for (Shop shop : shops) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Shop.class.getCanonicalName(), shop);
                bundle.putBoolean("tittle", true);
                Fragment fragment = new ShopFragment();
                fragment.setArguments(bundle);
                pagerAdapter.addFragment(fragment);
            }

        }
    }

    Shop shop;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pager_shops_fragment, null);
        viewPager = (ViewPager) v.findViewById(R.id.pagerShopsFragment_viewPager);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(currentItem);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Bundle bundle = pagerAdapter.getItem(position).getArguments();
                shop = bundle.getParcelable(Shop.class.getCanonicalName());

                toolbarListener.setTittle(shop.getShop());
                showFragmentListener.showCollapseFragment(ItemsFragment.TAG, bundle, false);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarListener.openToolbar();
        showFragmentListener.showCollapseFragment(ItemsFragment.TAG, pagerAdapter.getItem(currentItem).getArguments(), false);
        toolbarListener.setTittle(shop.getShop());

        viewPager.setCurrentItem(currentItem);
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        showFragmentListener = null;
        toolbarListener = null;
        pagerAdapter = null;
        viewPager = null;
    }
}
