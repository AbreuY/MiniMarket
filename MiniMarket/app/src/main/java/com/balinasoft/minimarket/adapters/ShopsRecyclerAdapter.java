package com.balinasoft.minimarket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Utils.MetricsUtil;
import com.balinasoft.minimarket.models.Shops.Shop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Microsoft on 02.06.2016.
 */
public class ShopsRecyclerAdapter extends RecyclerView.Adapter<ShopsRecyclerAdapter.ShopViewHolder> {
    public ArrayList<Shop> getShops() {
        return (ArrayList<Shop>) shops;
    }

    public void setShops(List<Shop> shops) {
        this.shops = shops;
    }

    List<Shop> shops;
    Context context;
    int sizeItem;

    public OnClickItemListener onClickItemListener = new OnClickItemListener() {
        @Override
        public void onClikItem(Shop shop) {

        }
    };

    public interface OnClickItemListener {
        void onClikItem(Shop shop);
    }

    public ShopsRecyclerAdapter(ArrayList<Shop> shops, Context context) {
        this.shops = shops;
        this.context = context;
        this.sizeItem= MetricsUtil.convertDpToPixel(MetricsUtil.SIZE_SHOP_IN_RECYCLER_VIEW,context);
    }

    @Override
    public ShopViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item, parent, false);
        return new ShopViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ShopViewHolder holder, int position) {
        final Shop shop = shops.get(position);
        holder.tvTittle.setText(shop.getShop());
        Picasso.with(context).load(shop.getImage()).resize(sizeItem,sizeItem).centerCrop().into(holder.ivBackground);
        holder.ivBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClikItem(shop);
            }
        });
    }
    public void addShop(Shop shop){
        shops.add(shop);
        notifyDataSetChanged();
    }
    public void addShops(ArrayList<Shop> shops){
        this.shops.addAll(shops);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return shops.size();
    }

    public OnClickItemListener getOnClickItemListener() {
        return onClickItemListener;
    }

    public ShopsRecyclerAdapter setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
        return this;
    }

    class ShopViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivBackground;
        public TextView tvTittle;

        public ShopViewHolder(View itemView) {
            super(itemView);
            ivBackground = (ImageView) itemView.findViewById(R.id.shopItem_ivBackground);
            tvTittle = (TextView) itemView.findViewById(R.id.shopItem_tvTittle);
        }
    }
}
