package com.balinasoft.minimarket.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.models.Menu.ItemMenu;

import java.util.List;


public class MenuAdapterRecyclerView extends RecyclerView.Adapter<MenuAdapterRecyclerView.ViewHolder> {
    public MenuAdapterRecyclerView(List<ItemMenu> menuItems) {
        this.menuItems = menuItems;
    }

    List<ItemMenu> menuItems;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ItemMenu menuItem = menuItems.get(position);
        holder.tvName.setText(menuItem.getName());

        if (menuItem.getAmountNotification() <= 0) {
            holder.tvAmountNotify.setVisibility(View.GONE);
        } else {
            holder.tvAmountNotify.setVisibility(View.VISIBLE);
            if (menuItem.getAmountNotification() > 100) {
                holder.tvAmountNotify.setText("100+");
            } else
                holder.tvAmountNotify.setText(String.valueOf(menuItem.getAmountNotification()));
        }
        holder.rlContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onMenuItemClickListener.onMenuItemClick(menuItem)) {
                    menuItem.setAmountNotification(0);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public void addItem(ItemMenu itemMenu) {
        menuItems.add(itemMenu);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAmountNotify;
        RelativeLayout rlContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.menuItem_tvName);
            tvAmountNotify = (TextView) itemView.findViewById(R.id.menuItem_tvAmountNotify);
            rlContent = (RelativeLayout) itemView.findViewById(R.id.menuItem_rlContent);
        }
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(ItemMenu item);

    }

    public void setOnMenuItemClickListener(OnMenuItemClickListener onMenuItemClickListener) {
        this.onMenuItemClickListener = onMenuItemClickListener;
    }

    OnMenuItemClickListener onMenuItemClickListener = new OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(ItemMenu item) {
            return false;
        }
    };

    public void addNotification(String name, int amount) {
        for (ItemMenu itemMenu : menuItems) {
            if (itemMenu.getName().equals(name)) {
                itemMenu.setAmountNotification(amount);
                notifyDataSetChanged();
                return;
            }
        }
    }
}
