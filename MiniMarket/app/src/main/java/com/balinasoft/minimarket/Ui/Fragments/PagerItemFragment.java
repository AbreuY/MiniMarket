package com.balinasoft.minimarket.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.adapters.MyFragmentPagerAdapter;
import com.balinasoft.minimarket.interfaces.ShowFragmentListener;
import com.balinasoft.minimarket.interfaces.ToolbarListener;
import com.balinasoft.minimarket.models.Image;
import com.balinasoft.minimarket.models.ProductItems.FullProductItem;
import com.balinasoft.minimarket.models.ProductItems.SuperProductItem;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Response.ResponseItem;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Microsoft on 01.07.2016.
 */
public class PagerItemFragment extends Basefragment {
    public static final String TAG = "PagerItemFragment";
    ViewPager viewPager;
    ImageView ivLike;
    MyFragmentPagerAdapter pagerAdapter;
    ExtensiblePageIndicator extensiblePageIndicator;
    SuperProductItem superProductItem;
    FullProductItem productItem;
    ShowFragmentListener showFragmentListener;
    ToolbarListener toolbarListener;
    ImageView ivBack;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        showFragmentListener = (ShowFragmentListener) getActivity();
        toolbarListener=(ToolbarListener)getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), new ArrayList<Fragment>());
        if (getArguments() != null) {
            superProductItem = getArguments().getParcelable(SuperProductItem.class.getCanonicalName());
            toolbarListener.setTittle(null);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pager_item, null);
        initView(v);
        if (superProductItem != null) {
            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put("item_id", String.valueOf(superProductItem.getId()));

            getService().item(hashMap).enqueue(new MyCallback<ResponseItem>() {
                @Override
                public void onData(ResponseItem data) {
                    productItem = data.getResult();
                    Bundle bundle=new Bundle();

                    bundle.putParcelable(FullProductItem.class.getCanonicalName(),productItem);
                    showFragmentListener.showCollapseFragment(ItemFragment.TAG,bundle,false);

                    for (Image image : productItem.getImages()) {

                        pagerAdapter.addFragment(PageItemFragment.getInstance(image.getImage()));
                    }
                }

                @Override
                public void onRequestEnd() {

                }
            });
        }
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        viewPager.setAdapter(pagerAdapter);
        extensiblePageIndicator.initViewPager(viewPager);

        return v;
    }

    private void initView(View v) {
        viewPager = (ViewPager) v.findViewById(R.id.pagerItem_viewPager);
        ivLike = (ImageView) v.findViewById(R.id.pagerItem_ivLike);
        extensiblePageIndicator = (ExtensiblePageIndicator) v.findViewById(R.id.pagerItem_indicator);
        ivBack=(ImageView)v.findViewById(R.id.pagerItem_ivBack);
    }

    @Override
    public void onResume() {
        super.onResume();
        toolbarListener.openToolbar();
    }
}
