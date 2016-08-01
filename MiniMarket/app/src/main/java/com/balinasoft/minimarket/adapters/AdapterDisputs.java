package com.balinasoft.minimarket.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.models.Dispute;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by Microsoft on 22.07.2016.
 */
public class AdapterDisputs extends RecyclerView.Adapter<AdapterDisputs.DisputeViewHolder> {
    List<Dispute> disputes;
    Context context;

    public AdapterDisputs(Context context, List<Dispute> disputes) {
        this.context = context;
        this.disputes = disputes;
    }

    public void addItems(ArrayList<Dispute> result) {
        this.disputes.addAll(result);
        notifyDataSetChanged();
    }

    public void clear() {
        disputes.clear();
        notifyDataSetChanged();
    }

    public interface ClickItemListener {
        void itemClick(String dispute_id);

        void itemClick(Dispute dispute);
    }

    public interface ClickOrderListener {
        void orderClick(String order_id);
    }

    public void setClickOrderListener(ClickOrderListener clickOrderListener) {
        this.clickOrderListener = clickOrderListener;
    }

    ClickOrderListener clickOrderListener;

    public void setClickItemListener(ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    ClickItemListener clickItemListener;

    @Override
    public DisputeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DisputeViewHolder(getView(R.layout.item_dispute, parent));
    }

    private View getView(@LayoutRes int id, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(id, parent, false);
    }

    @Override
    public void onBindViewHolder(DisputeViewHolder holder, int position) {
        final Dispute dispute = disputes.get(position);
        holder.tvStatus.setText(dispute.getIs_open());
        if (dispute.isOpen()) {
            holder.tvStatus.setTextColor(Color.GREEN);
        } else holder.tvStatus.setTextColor(Color.RED);
        holder.tvTheme.setText(dispute.getTitle());
        holder.tvDate.setText(dispute.getDate_time());
        holder.tvId.setText(context.getString(R.string.id) + " " + dispute.getOrder_id());
        Picasso.with(context).load(dispute.getShop().getImage()).transform(new CropCircleTransformation()).into(holder.ivShop);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickItemListener != null) {
                    clickItemListener.itemClick(dispute);
                    clickItemListener.itemClick(dispute.getId());
                }
            }
        });
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickOrderListener != null)
                    clickOrderListener.orderClick(dispute.getOrder_id());
            }
        });

    }

    @Override
    public int getItemCount() {
        return disputes.size();
    }

    public static class DisputeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTheme, tvStatus, tvId, tvDate;
        ImageView ivShop;
        RelativeLayout relativeLayout;

        public DisputeViewHolder(View itemView) {
            super(itemView);
            tvTheme = (TextView) itemView.findViewById(R.id.itemDispute_tvTheme);
            tvStatus = (TextView) itemView.findViewById(R.id.itemDispute_tvStatus);
            tvId = (TextView) itemView.findViewById(R.id.itemDispute_tvId);
            tvDate = (TextView) itemView.findViewById(R.id.itemDispute_tvDate);
            ivShop = (ImageView) itemView.findViewById(R.id.itemDispute_ivShop);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.itemDispute_rl);
        }
    }
}
