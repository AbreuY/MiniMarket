package com.balinasoft.minimarket.Ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.adapters.AdapterItemFragment;
import com.balinasoft.minimarket.models.ProductItems.FullProductItem;

/**
 * Created by Microsoft on 01.07.2016.
 */
public class ItemFragment extends Basefragment {
    public static final String TAG="ItemFragment";
    RecyclerView recyclerView;
    AdapterItemFragment adapterItemFragment;
    FullProductItem fullProductItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            fullProductItem=getArguments().getParcelable(FullProductItem.class.getCanonicalName());
            adapterItemFragment=new AdapterItemFragment(fullProductItem,getActivity(),getChildFragmentManager(),0);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.full_item,null);
        initView(v);
        recyclerView.setAdapter(adapterItemFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    private void initView(View v) {
        recyclerView=(RecyclerView)v.findViewById(R.id.itemFragment_recyclerView);
    }
}
