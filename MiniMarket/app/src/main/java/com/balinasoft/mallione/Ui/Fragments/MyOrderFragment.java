package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Activities.BasketItemsActivity;
import com.balinasoft.mallione.Ui.Dialogs.AssessDialog;
import com.balinasoft.mallione.adapters.AdapterOrders;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.Order;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestUserData;
import com.balinasoft.mallione.networking.Response.ResponseOrders;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Microsoft on 13.07.2016.
 */
public class MyOrderFragment extends Basefragment {
    public static final String TAG = "MyOrderFragment";
    ViewPager viewPager;
    ProgressBar progressBar;
    ToolbarListener toolbarListener;
    UserListener<? extends User> userListener;
    TabLayout tabLayout;
    RequestUserData userData;
    ShowFragmentListener showFragmentListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarListener = (ToolbarListener) getActivity();
        showFragmentListener = (ShowFragmentListener) getActivity();
        showFragmentListener.showFragmentToolBar(BlurbFragment.TAG, null);
        toolbarListener.setTittle(getString(R.string.myOrders));
        toolbarListener.closeToolbar();
        userListener = (UserListener<? extends User>) getActivity();
        userData = new RequestUserData(userListener.getUser().getSession_id(), userListener.getUser().getId());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.myorder_fragment, null);
        initView(v);

        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        addData();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    PagerAdapter pagerAdapter;

    private void initView(View v) {
        viewPager = (ViewPager) v.findViewById(R.id.myOrderFragment_viewPager);
        progressBar = (ProgressBar) v.findViewById(R.id.myOrderFragment_progressBar);
        tabLayout = (TabLayout) v.findViewById(R.id.myOrderFragment_tabs);
    }

    public void addData() {
        getService().orders(userData).enqueue(new MyCallbackWithMessageError<ResponseOrders>() {
            @Override
            public void onData(ResponseOrders data) {
                try {
                    pagerAdapter.addFrag(FragmentRecyclerView.newInstance(data.getResult().getActive()), getString(R.string.current));
                    pagerAdapter.addFrag(FragmentRecyclerView.newInstance(data.getResult().getComplete()), getString(R.string.finished));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (MyOrderFragment.this.isAdded()) {
                    viewPager.setAdapter(pagerAdapter);
                    tabLayout.setupWithViewPager(viewPager);
                }
            }

            @Override
            public void onRequestEnd() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    public static class PagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public static class FragmentRecyclerView extends Basefragment {
        AssessDialog assessDialog;
        AdapterOrders adapterOrders;

        List<Order> orders;

        public static FragmentRecyclerView newInstance(List<Order> orders) {
            FragmentRecyclerView fragmentRecyclerView = new FragmentRecyclerView();
            fragmentRecyclerView.setOrders(orders);
            return fragmentRecyclerView;
        }

        RecyclerView recyclerView;
        ShowFragmentListener showFragmentListener;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            showFragmentListener = (ShowFragmentListener) getActivity();
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            adapterOrders = new AdapterOrders(getActivity(), orders);
            adapterOrders.setClickItemListener(new AdapterOrders.ClickItemListener() {
                @Override
                public void clickItemImage(Order order) {
                    Intent intent = new Intent(getActivity(), BasketItemsActivity.class);
                    intent.putExtra(Order.class.getCanonicalName(), order);
                    getActivity().startActivity(intent);
                }

                @Override
                public void clickItemBtn(Order order) {
                    new AssessDialog().setOrder(String.valueOf(order.getId()))
//                            .setItemId()
                            .setTypeAssess(AssessDialog.ORDER)
                            .show(getFragmentManager(), "");
                }

            });
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.rectycler_view, null);
            recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
            recyclerView.setAdapter(adapterOrders);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            return v;
        }

        public void setOrders(List<Order> orders) {
            this.orders = orders;
        }

    }
}
