package com.balinasoft.minimarket.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.interfaces.ToolbarListener;
import com.balinasoft.minimarket.models.Shops.Shop;
import com.squareup.picasso.Picasso;

/**
 * Created by Microsoft on 03.06.2016.
 */
public class ShopFragment extends Basefragment {
    Shop shop;
    TextView tittle;
    ImageView ivBackground;
    boolean noTittle;
    ToolbarListener toolbarListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarListener=(ToolbarListener)getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            shop=getArguments().getParcelable(Shop.class.getCanonicalName());
            noTittle=getArguments().getBoolean("tittle");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.shop_item,null);
        ivBackground=(ImageView)v.findViewById(R.id.shopItem_ivBackground);
        tittle=(TextView)v.findViewById(R.id.shopItem_tvTittle);
        tittle.setText(shop.getShop());
        Picasso.with(getActivity()).load(shop.getImage()).into(ivBackground);
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(noTittle){
            tittle.setVisibility(View.INVISIBLE);
        }
    }
}
