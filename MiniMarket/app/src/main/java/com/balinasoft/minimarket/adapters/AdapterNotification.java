package com.balinasoft.minimarket.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.models.Notification;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Microsoft on 11.07.2016.
 */
public class AdapterNotification extends RecyclerView.Adapter<AdapterNotification.ViewHolderNotification> {
    List<Notification> notifications;
    Context context;
    public static final int ORDER_TYPE = 1;
    public static final int RECORD_TYPE = 2;
    public static final int OTHER_TYPE = 3;

    public interface OnItemClickListener {
        void onOrder(Notification notification);

        void onRecord(Notification notification);

        void onOther(Notification notification);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListener onItemClickListener;

    public AdapterNotification(Context context, List<Notification> notifications) {
        this.context = context;
        this.notifications = notifications;
    }

    @Override
    public ViewHolderNotification onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);
//        switch (viewType){
//            case OTHER_TYPE:
//
//                break;
//        }
        return new ViewHolderNotification(v);
    }

    @Override
    public void onBindViewHolder(ViewHolderNotification holder, int position) {
        final Notification notification = notifications.get(position);

        if (notification.getShop().getImage() != null && !notification.getShop().getImage().isEmpty()) {
            Picasso.with(context).load(notification.getShop().getImage()).into(holder.ivShop);
        } else
            holder.ivShop.setImageDrawable(context.getResources().getDrawable(R.drawable.defolt));

        if (notification.getIs_new().equals("1")) {
            holder.tvName.setTextColor(Color.RED);
        }

        holder.tvName.setText(notification.getShop().getShop());
        holder.tvNotification.setText(notification.getNotification());
        holder.tvDate.setText(notification.getDate_time());

        switch (getItemViewType(position)) {
            case ORDER_TYPE:
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onOrder(notification);
                        }
                    }
                });
                break;
            case RECORD_TYPE:
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onRecord(notification);
                        }
                    }
                });
                break;
            case OTHER_TYPE:
                holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onOther(notification);
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {

        Notification notification = notifications.get(position);
        if (notification.getOrder_id().isEmpty() && notification.getRecord_id().isEmpty()) {
            return OTHER_TYPE;
        }
        if (!notification.getOrder_id().isEmpty()) {
            return ORDER_TYPE;
        }
        if (!notification.getRecord_id().isEmpty()) {
            return RECORD_TYPE;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }


    public List<Notification> getNotifications() {
        return notifications;
    }

    public class ViewHolderNotification extends RecyclerView.ViewHolder {
        ImageView ivShop;
        TextView tvName, tvDate, tvNotification;
        CardView cardView;

        public ViewHolderNotification(View itemView) {
            super(itemView);
            ivShop = (ImageView) itemView.findViewById(R.id.itemNotification_ivShop);
            tvName = (TextView) itemView.findViewById(R.id.itemNotification_tvName);
            tvDate = (TextView) itemView.findViewById(R.id.itemNotification_tvDate);
            tvNotification = (TextView) itemView.findViewById(R.id.itemNotification_tvNotification);
            cardView = (CardView) itemView.findViewById(R.id.itemNotification_cardView);
        }
    }

    public void addItems(ArrayList<Notification> notifications) {
        this.notifications.addAll(notifications);
        notifyDataSetChanged();
    }

    public void addItem(Notification notification) {
        this.notifications.add(notification);
        notifyDataSetChanged();
    }
}
