package com.balinasoft.mallione.Ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.adapters.MenuAdapterRecyclerView;
import com.balinasoft.mallione.models.Menu.ItemMenu;

import java.util.ArrayList;

/**
 * Created by Microsoft on 30.06.2016.
 */
public class MenuFragment extends Basefragment {
    RecyclerView recyclerView;
    MenuAdapterRecyclerView menuAdapter=new MenuAdapterRecyclerView(new ArrayList<ItemMenu>());
    public static MenuFragment newInstance(String[] strings){
        MenuFragment menuFragment=new MenuFragment();
        for (int i=0;i<strings.length;i++){
            menuFragment.addMenuItem(new ItemMenu(i,strings[i],0));
        }
        return menuFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.menu,null);
        initView(v);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(menuAdapter);

        return v;
    }

    private void initView(View v) {
        recyclerView=(RecyclerView)v.findViewById(R.id.menu_recyclerView);
    }
    public void addMenuItem(ItemMenu itemMenu){
        menuAdapter.addItem(itemMenu);
    }
    public void addNotification(String nameMenu, int amount){
        menuAdapter.addNotification(nameMenu,amount);
    }
    public void setOnMenuItemClickListener(MenuAdapterRecyclerView.OnMenuItemClickListener onMenuItemClickListener){
        menuAdapter.setOnMenuItemClickListener(onMenuItemClickListener);
    }
}
