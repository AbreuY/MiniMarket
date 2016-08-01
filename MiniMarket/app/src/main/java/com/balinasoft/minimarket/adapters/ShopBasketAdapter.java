package com.balinasoft.minimarket.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.models.Shops.ShopBasket;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Microsoft on 04.07.2016.
 */
public class ShopBasketAdapter extends RecyclerView.Adapter<ShopBasketAdapter.ShopBasketViewHolder> {
    List<ShopBasket> shopBaskets;
    Context context;

    public void setOnClickItemListener(ShopsRecyclerAdapter.OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    ShopsRecyclerAdapter.OnClickItemListener onClickItemListener;
    public ShopBasketAdapter(Context context,List<ShopBasket> shopBaskets){
        this.shopBaskets=shopBaskets;
        this.context=context;

    }


    @Override
    public int getItemCount() {
        return shopBaskets.size();
    }



    @Override
    public ShopBasketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_basket_item,parent,false);
        return new ShopBasketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShopBasketViewHolder viewHolder, int position) {
        final ShopBasket shopBasket=shopBaskets.get(position);
        Picasso.with(context).load(shopBasket.getImage()).into(viewHolder.ivShop);
        viewHolder.tvName.setText(shopBasket.getShop());
        viewHolder.tvAmount.setText(String.valueOf(shopBasket.getAmountProduct()));
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClikItem(shopBasket);
            }
        });
    }

    public void clear() {
        shopBaskets.clear();
        notifyDataSetChanged();
    }

    public static class ShopBasketViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvAmount;
        ImageView ivShop;
        CardView cardView;
        public ShopBasketViewHolder(View itemView) {
            super(itemView);
            tvAmount=(TextView)itemView.findViewById(R.id.shopBasket_tvAmountProduct);
            tvName=(TextView)itemView.findViewById(R.id.shopBasket_tvName);
            ivShop=(ImageView)itemView.findViewById(R.id.shopBasket_ivShop);
            cardView=(CardView)itemView.findViewById(R.id.shopBasket_cardView);
        }
    }
    public void addItems(List<ShopBasket> shopBaskets){
        this.shopBaskets=shopBaskets;
        notifyDataSetChanged();
    }
}
