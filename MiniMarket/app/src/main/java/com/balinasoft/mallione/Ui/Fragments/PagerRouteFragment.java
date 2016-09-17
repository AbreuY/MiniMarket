package com.balinasoft.mallione.Ui.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.adapters.MyFragmentPagerAdapter;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.models.Category;

import java.util.ArrayList;

/**
 * Created by Microsoft on 02.06.2016.
 */
public class PagerRouteFragment extends Basefragment {
    public static final String TAG = "PagerRouteFragment";
    ViewPager viewPager;
    ShowFragmentListener showFragmentListener;
    MyFragmentPagerAdapter pagerRouteAdapter;
    ToolbarListener toolbarListener;
    int currentItem;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        showFragmentListener = (ShowFragmentListener) activity;
        toolbarListener = (ToolbarListener) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        pagerRouteAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), new ArrayList<Fragment>());
        if (getArguments() != null) {
            ArrayList<Category> categories = getArguments().getParcelableArrayList("RoutesList");
            currentItem = getArguments().getInt("CurrentRouteIndex");

            if (categories != null) {
                category=categories.get(currentItem);
                for (Category category : categories) {
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(Category.class.getCanonicalName(), category);
                    bundle.putBoolean("tittle", true);
                    Fragment fragment = new RouteFragment();
                    fragment.setArguments(bundle);
                    pagerRouteAdapter.addFragment(fragment);
                }
            }
        }
    }

    Category category;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pager_routes_fragment, null);
        viewPager = (ViewPager) v.findViewById(R.id.pagerRoutesFragment_viewPager);
        viewPager.setAdapter(pagerRouteAdapter);
        viewPager.setCurrentItem(currentItem);
        toolbarListener.openToolbar();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Bundle bundle = pagerRouteAdapter.getItem(position).getArguments();
                category = bundle.getParcelable(Category.class.getCanonicalName());
                showFragmentListener.showCollapseFragment(ShopsFragment.TAG, bundle, false);
                toolbarListener.setTittle(category.getRoute());
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
        Bundle bundle = new Bundle();
        if (category != null) {
            bundle.putParcelable(Category.class.getCanonicalName(),category);
            showFragmentListener.showCollapseFragment(ShopsFragment.TAG, bundle, false);
            toolbarListener.setTittle(category.getRoute());
            viewPager.setCurrentItem(currentItem);
        }
        pagerRouteAdapter.notifyDataSetChanged();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }
}
