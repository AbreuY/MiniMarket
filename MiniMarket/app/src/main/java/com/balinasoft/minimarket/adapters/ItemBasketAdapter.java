package com.balinasoft.minimarket.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.models.ProductItems.BasketProductItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Microsoft on 04.07.2016.
 */
public class ItemBasketAdapter extends RecyclerView.Adapter<ItemBasketAdapter.ItemBasketViewHolder> {

    private boolean visibilityControl=true;

    public void setVisibilityControl(boolean visibilityControl) {
        this.visibilityControl = visibilityControl;
    }

    public interface ControlAmountListener {
        void add(BasketProductItem basketProductItem);

        void delete(BasketProductItem basketProductItem);

        void requestDelete(BasketProductItem basketProductItem);
    }
    public interface AmountShangeListener{
        void onChangeAmount();
    }

    public void setAmountShangeListener(AmountShangeListener amountShangeListener) {
        this.amountShangeListener = amountShangeListener;
    }

    AmountShangeListener amountShangeListener;
    public void setClickItemListener(ItemsAdapterRecyclerView.ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    ItemsAdapterRecyclerView.ClickItemListener clickItemListener;

    public ControlAmountListener getControlAmountListener() {
        return controlAmountListener;
    }

    public void setControlAmountListener(ControlAmountListener controlAmountListener) {
        this.controlAmountListener = controlAmountListener;
    }

    ControlAmountListener controlAmountListener;
    Context context;

    public List<BasketProductItem> getProductItems() {
        return productItems;
    }

    List<BasketProductItem> productItems = new ArrayList<>();

    public ItemBasketAdapter(Context context) {
        this.context = context;
    }

    public ItemBasketAdapter(Context context, List<BasketProductItem> productItems) {
        this(context);
        this.productItems = productItems;
    }

    @Override
    public ItemBasketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_basket, parent, false);
        return new ItemBasketViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ItemBasketViewHolder holder, int position) {
        final BasketProductItem productItem = productItems.get(position);
        if (productItem.getImage().isEmpty()) {
            holder.ivImage.setImageDrawable(context.getResources().getDrawable(R.drawable.defolt));
        } else Picasso.with(context).load(productItem.getImage()).into(holder.ivImage);
        if(!visibilityControl){
            holder.tvAddProduct.setVisibility(View.GONE);
            holder.tvDeleteProduct.setVisibility(View.GONE);
        }
        holder.tvDescription.setText(productItem.getDescription());
        holder.tvAmountProduct.setText(String.valueOf(productItem.getAmountProduct()));
        holder.tvPrice.setText(productItem.getPrice());
        holder.tvName.setText(productItem.getItem());

        holder.rlClickItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickItemListener != null) {
                    clickItemListener.onClick(productItem);
                }
            }
        });
        holder.tvAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productItem.incrementAmount();
                if (controlAmountListener != null) {

                    controlAmountListener.add(productItem);
                }
                if(amountShangeListener!=null){
                    amountShangeListener.onChangeAmount();
                }
                holder.tvAmountProduct.setText(String.valueOf(productItem.getAmountProduct()));
            }
        });
        holder.tvDeleteProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controlAmountListener != null) {
                    if (productItem.getAmountProduct() == 1) {
                        controlAmountListener.requestDelete(productItem);
                    } else {
                        productItem.decrementAmount();
                        controlAmountListener.delete(productItem);
                    }
                }
                if (amountShangeListener!=null){
                    amountShangeListener.onChangeAmount();
                }
                holder.tvAmountProduct.setText(String.valueOf(productItem.getAmountProduct()));
            }
        });


    }

    @Override
    public int getItemCount() {
        return productItems.size();
    }

    public static class ItemBasketViewHolder extends RecyclerView.ViewHolder {

        TextView tvDescription, tvPrice, tvName, tvAddProduct, tvDeleteProduct, tvAmountProduct;
        ImageView ivImage;
        RelativeLayout rlClickItem;

        public ItemBasketViewHolder(View itemView) {
            super(itemView);
            tvDescription = (TextView) itemView.findViewById(R.id.itemBasket_tvDescription);
            tvPrice = (TextView) itemView.findViewById(R.id.itemBasket_tvPrice);
            tvName = (TextView) itemView.findViewById(R.id.itemBasket_tvName);
            tvAddProduct = (TextView) itemView.findViewById(R.id.itemBasket_tvAddProduct);
            tvDeleteProduct = (TextView) itemView.findViewById(R.id.itemBasket_tvDeleteProduct);
            tvAmountProduct = (TextView) itemView.findViewById(R.id.itemBasket_tvAmountProduct);
            ivImage = (ImageView) itemView.findViewById(R.id.itemBasket_ivImage);
            rlClickItem = (RelativeLayout) itemView.findViewById(R.id.itemBasket_rlClickItem);
        }
    }

    public void addItems(ArrayList<BasketProductItem> basketProductItems) {
        productItems.addAll(basketProductItems);
        notifyDataSetChanged();
    }

    public void delete(BasketProductItem basketProductItem) {
        productItems.remove(basketProductItem);
        notifyDataSetChanged();
    }
}
