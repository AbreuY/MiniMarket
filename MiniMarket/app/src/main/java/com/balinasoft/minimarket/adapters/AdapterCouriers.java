package com.balinasoft.minimarket.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.models.modelUsers.Courier;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anton Kolotsey on 01.08.2016.
 */
public class AdapterCouriers extends RecyclerView.Adapter<AdapterCouriers.CourierViewHolder> {
    List<Courier> couriers;
    Context context;

    public List<Courier> getCouriers() {
        return couriers;
    }

    public void setCouriers(List<Courier> couriers) {
        this.couriers = couriers;
    }

    public AdapterCouriers(List<Courier> couriers, Context context) {

        this.couriers = couriers;
        this.context = context;
    }

    @Override
    public CourierViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CourierViewHolder(getView(R.layout.courier_item, parent));
    }

    private View getView(int id, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(id, parent, false);
    }

    @Override
    public void onBindViewHolder(CourierViewHolder holder, int position) {
        final Courier courier = couriers.get(position);
        if (courier.getPhone().isEmpty()) {
            holder.rlPhone.setVisibility(View.GONE);
        }
        if (courier.getEmail().isEmpty()) {
            holder.rlMAil.setVisibility(View.GONE);
        }
        holder.ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + courier.getPhone()));
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                context.startActivity(intent);
            }
        });
        holder.ivMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" +courier.getEmail()));
                Intent intent = Intent.createChooser(emailIntent, "Chooser Title");
                if (intent != null) {
                    context.startActivity(intent);
                }
            }
        });
        holder.tvPhone.setText(courier.getPhone());
        holder.tvMail.setText(courier.getEmail());
        holder.tvCarBrand.setText(courier.getC_car_brand());
        holder.tvStatus.setText(courier.getC_status());
        holder.tvFio.setText(courier.getFio());
    }

    @Override
    public int getItemCount() {
        return couriers.size();
    }

    public void addItems(ArrayList<Courier> result) {
        couriers.addAll(result);
        notifyDataSetChanged();
    }

    public static class CourierViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout rlPhone,rlMAil;
        TextView tvFio,tvMail,tvPhone,tvStatus,tvCarBrand;
        ImageView ivPhone,ivMail;
        public CourierViewHolder(View itemView) {
            super(itemView);
            rlPhone=(RelativeLayout)itemView.findViewById(R.id.courierItem_rlPhone);
            rlMAil=(RelativeLayout)itemView.findViewById(R.id.courierItem_rlMail);
            tvFio=(TextView)itemView.findViewById(R.id.courierItem_tvFio);
            tvMail=(TextView)itemView.findViewById(R.id.courierItem_tvEmail);
            tvPhone=(TextView)itemView.findViewById(R.id.courierItem_tvPhone);
            tvStatus=(TextView)itemView.findViewById(R.id.courierItem_tvStatus);
            tvCarBrand=(TextView)itemView.findViewById(R.id.courierItem_tvCarBrand);
            ivMail=(ImageView)itemView.findViewById(R.id.courierItem_ivMail);
            ivPhone=(ImageView)itemView.findViewById(R.id.courierItem_ivPhone);
        }
    }
}
