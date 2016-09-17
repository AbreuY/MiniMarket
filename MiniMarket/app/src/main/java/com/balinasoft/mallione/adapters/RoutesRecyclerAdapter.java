package com.balinasoft.mallione.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.models.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Microsoft on 02.06.2016.
 */
public class RoutesRecyclerAdapter extends RecyclerView.Adapter<RoutesRecyclerAdapter.RouteViewHolder> {
    public ArrayList<Category> getCategories() {
        return (ArrayList<Category>) categories;
    }

    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }

    List<Category> categories;
    Context context;

    OnClickItemListener onClickItemListener = new OnClickItemListener() {
        @Override
        public void onClickItem(Category category) {

        }
    };

    public RoutesRecyclerAdapter(ArrayList<Category> categories, Context context) {
        this.categories = categories;
        this.context=context;
    }

    @Override
    public RouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_route, parent, false);
        return new RouteViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RouteViewHolder holder, int position) {
        final Category category = categories.get(position);
        Picasso.with(context).load(category.getImage()).into(holder.ivBackground);
        holder.tvTittle.setText(category.getRoute());
        holder.ivBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickItemListener.onClickItem(category);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public void addRoute(Category category) {
        categories.add(category);
        notifyDataSetChanged();
    }

    public void addRoute(int position, Category category) {
        categories.add(position, category);
        notifyDataSetChanged();
    }

    public void addList(ArrayList<Category> categories) {
        this.categories.addAll(categories);
        notifyDataSetChanged();
    }

    public OnClickItemListener getOnClickItemListener() {
        return onClickItemListener;
    }

    public RoutesRecyclerAdapter setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
        return this;
    }

    class RouteViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivBackground;
        public TextView tvTittle;
        public RouteViewHolder(View itemView) {
            super(itemView);
            ivBackground=(ImageView) itemView.findViewById(R.id.itemRoute_ivBackground);
            tvTittle=(TextView)itemView.findViewById(R.id.itemRoute_tvTittle);
        }
    }

    public interface OnClickItemListener {
        void onClickItem(Category category);
    }
}
