package com.balinasoft.mallione.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.models.Order;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Microsoft on 14.07.2016.
 */
public class AdapterOrders extends RecyclerView.Adapter<AdapterOrders.OrderViewHolder> {

    public void setClickItemListener(ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    public interface ClickItemListener {
        void clickItemImage(Order order);

        void clickItemBtn(Order order);
    }

    ClickItemListener clickItemListener;
    List<Order> orders;
    Context context;

    public AdapterOrders(Context context, List<Order> orders) {
        this.context = context;
        this.orders = orders;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        final Order order = orders.get(position);
        if (order.getShop() == null || order.getShop().getImage().isEmpty()) {
            holder.ivShop.setImageDrawable(context.getResources().getDrawable(R.drawable.defolt));
        } else {
            Picasso.with(context).load(order.getShop().getImage()).into(holder.ivShop);
        }
        holder.tvId.setText(context.getString(R.string.id) + " " + String.valueOf(order.getId()));
        if (order.getShop() != null)
            holder.tvNameShop.setText(order.getShop().getShop());
        holder.tvDate.setText(order.getDate_time_start());
        holder.tvPrice.setText(order.getTotal());
        holder.tvStatus.setText(order.getStatus());
        holder.ivShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickItemListener != null)
                    clickItemListener.clickItemImage(order);
            }
        });
        if (order.getReview_status()!=null && order.getReview_status().equals("1")) {
            holder.ivBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.rate));
            holder.ivBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickItemListener != null)
                        clickItemListener.clickItemBtn(order);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        ImageView ivShop, ivBtn;
        TextView tvId, tvNameShop, tvDate, tvPrice, tvStatus;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ivShop = (ImageView) itemView.findViewById(R.id.ItemOrder_ivShop);
            ivBtn = (ImageView) itemView.findViewById(R.id.ItemOrder_ivButton);
            tvId = (TextView) itemView.findViewById(R.id.ItemOrder_tvId);
            tvDate = (TextView) itemView.findViewById(R.id.ItemOrder_tvDate);
            tvPrice = (TextView) itemView.findViewById(R.id.ItemOrder_tvPrice);
            tvStatus = (TextView) itemView.findViewById(R.id.ItemOrder_tvStatus);
            tvNameShop = (TextView) itemView.findViewById(R.id.ItemOrder_tvNameShop);

        }
    }

    public void addItems(List<Order> orders) {
        this.orders.addAll(orders);
        notifyDataSetChanged();
    }

    public void addItem(Order order) {
        this.orders.add(order);
        notifyDataSetChanged();
    }
}
