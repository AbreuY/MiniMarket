package com.balinasoft.minimarket.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.models.ProductItems.PreViewProductItem;
import com.balinasoft.minimarket.models.Service;
import com.balinasoft.minimarket.models.Shops.Shop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Microsoft on 13.07.2016.
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    public interface OnClickItemListener{
        void onShop(Shop shop);
        void onProduct(PreViewProductItem preViewProductItem);
    }

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    OnClickItemListener onClickItemListener;
    List<Service> services;
    Context context;

    public ServiceAdapter(Context context, List<Service> services) {
        this.context = context;
        this.services = services;
    }

    @Override
    public ServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ServiceViewHolder holder, int position) {
        final Service service = services.get(position);
        if (service.getItem().getImage() == null || service.getItem().getImage().isEmpty()) {
            holder.ivService.setImageDrawable(context.getResources().getDrawable(R.drawable.defolt));
        } else Picasso.with(context).load(service.getItem().getImage()).into(holder.ivService);

        if (service.getShop().getImage() == null || service.getShop().getImage().isEmpty()) {
            holder.ivShop.setImageDrawable(context.getResources().getDrawable(R.drawable.defolt));
        } else Picasso.with(context).load(service.getShop().getImage()).into(holder.ivShop);
        holder.tvId.setText(context.getString(R.string.id)+" "+service.getId());
        holder.tvNameShop.setText(service.getShop().getShop());
        holder.tvNameService.setText(service.getItem().getItem());
        holder.tvDate.setText(service.getDate_time_record());
        holder.tvStatus.setText(service.getStatus());
        holder.tvPrice.setText(service.getPrice());
        if(service.getStatus().equals(Service.ACTIVE)){
            holder.tvStatus.setTextColor(Color.GREEN);
        }
        holder.ivShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickItemListener!=null){
                    onClickItemListener.onShop(service.getShop());
                }
            }
        });
        holder.ivService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickItemListener!=null){
                    onClickItemListener.onProduct(service.getItem());
                }
            }
        });
        
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public static final class ServiceViewHolder extends RecyclerView.ViewHolder {
        ImageView ivService, ivShop;
        TextView tvId, tvNameShop, tvNameService, tvDate, tvPrice, tvStatus;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            ivService = (ImageView) itemView.findViewById(R.id.itemService_ivService);
            ivShop = (ImageView) itemView.findViewById(R.id.itemService_ivShop);
            tvId = (TextView) itemView.findViewById(R.id.itemService_tvId);
            tvNameShop = (TextView) itemView.findViewById(R.id.itemService_tvNameShop);
            tvNameService = (TextView) itemView.findViewById(R.id.itemService_tvNameService);
            tvDate = (TextView) itemView.findViewById(R.id.itemService_tvdate);
            tvPrice = (TextView) itemView.findViewById(R.id.itemService_tvPrice);
            tvStatus = (TextView) itemView.findViewById(R.id.itemService_tvStatus);
        }
    }

    public void addItems(ArrayList<Service> services) {
        this.services.addAll(services);
        notifyDataSetChanged();
    }

    public void addItem(Service service) {
        this.services.add(service);
        notifyDataSetChanged();
    }
}
