package com.balinasoft.minimarket.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Microsoft on 01.06.2016.
 */
public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> fragments;

    public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);

    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    public void addFragment(Fragment fragment){
        fragments.add(fragment);
        notifyDataSetChanged();
    }
    public void addFragment(Fragment fragment, Bundle bundle){
        if(fragment!=null){
            fragment.setArguments(bundle);
            addFragment(fragment);
        }

    }
    public void addListFragment(List<Fragment> fragments){
        this.fragments.addAll(fragments);
        notifyDataSetChanged();
    }

    public void clear() {
        fragments.clear();
        notifyDataSetChanged();
    }
}
