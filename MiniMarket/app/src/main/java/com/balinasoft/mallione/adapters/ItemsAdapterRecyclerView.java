package com.balinasoft.mallione.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Utils.MetricsUtil;
import com.balinasoft.mallione.interfaces.BasketListener;
import com.balinasoft.mallione.models.ProductItems.BasketProductItem;
import com.balinasoft.mallione.models.ProductItems.PreViewProductItem;
import com.balinasoft.mallione.models.ProductItems.SuperProductItem;
import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.models.modelUsers.User;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Microsoft on 03.06.2016.
 */
public class ItemsAdapterRecyclerView extends RecyclerSwipeAdapter<ItemsAdapterRecyclerView.ItemViewHolder> {
    private boolean visibleBasket = true;

    public List<PreViewProductItem> getItems() {
        return items;
    }

    List<PreViewProductItem> items = new ArrayList<>();
    Context context;
    int size;

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.itemFragment_swipe;
    }

    public void addItem(BasketProductItem productItem) {
        this.items.add(productItem);
    }

    public void setItems(ArrayList<PreViewProductItem> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public void setVisibleBasket(boolean visibleBasket) {
        this.visibleBasket = visibleBasket;
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public interface DeleteItemListener {
        void onDelete(SuperProductItem productItem);
    }

    public interface EnrollListener {
        void onEnroll(SuperProductItem productItem);
    }

    public void setEnrollListener(EnrollListener enrollListener) {
        this.enrollListener = enrollListener;
    }

    EnrollListener enrollListener;

    public void setDeleteItemListener(DeleteItemListener deleteItemListener) {
        this.deleteItemListener = deleteItemListener;
    }

    DeleteItemListener deleteItemListener;

    public void setUser(User user) {
        this.user = user;
        notifyDataSetChanged();
    }

    public User getUser() {
        return user;
    }

    User user;


    private BasketListener basketListener = new BasketListener() {
        @Override
        public void onPutBAsket(SuperProductItem baseProductItem) {

        }

    };

    public interface ClickItemListener {
        void onClick(SuperProductItem productItem);
    }

    public void setClickItemListener(ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
    }

    ClickItemListener clickItemListener;
    int layoutId;

    public ItemsAdapterRecyclerView(Context context, ArrayList<PreViewProductItem> preViewProductItems, @LayoutRes int layoutId) {
        items.addAll(preViewProductItems);
        this.context = context;
        size = MetricsUtil.convertDpToPixel(MetricsUtil.SIZE_PHOTO_IN_RECYCLER_VIEW, context);
        this.layoutId = layoutId;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position) {
        final PreViewProductItem productItem = items.get(position);
        try {
            Picasso.with(context).load(productItem.getImage()).resize(size, size).into(holder.ivBackground);
        } catch (Exception e) {
            holder.ivBackground.setImageDrawable(context.getResources().getDrawable(R.drawable.defolt));
        }

        holder.tvPrice.setText(productItem.getPrice());
        if (productItem.getRating() != null)
            holder.ratingBar.setRating(Float.valueOf(productItem.getRating()));
        else holder.ratingBar.setVisibility(View.INVISIBLE);
        holder.tvName.setText(productItem.getItem());
        if (holder.tvDescription != null) {
            holder.tvDescription.setText(productItem.getDescription());
        }
        if (holder.swipeLayout != null) {
            SwipeLayout swipeLayout = holder.swipeLayout;
            swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
            holder.ivDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (deleteItemListener != null)
                        deleteItemListener.onDelete(productItem);
                }
            });

        }
        if (user != null && !(user instanceof Buer)) {
            holder.ivBasket.setVisibility(View.INVISIBLE);
        }
        if (!visibleBasket) {
            holder.ivBasket.setVisibility(View.INVISIBLE);
            holder.tvAmount.setVisibility(View.INVISIBLE);
        }
        holder.ivBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemListener.onClick(productItem);
            }
        });
        if (productItem.getRoute_id() != null)
            if (!productItem.getRoute_id().equals(SuperProductItem.PRODUCT)) {
                holder.ivBasket.setImageDrawable(context.getResources().getDrawable(R.drawable.enroll));
                holder.tvAmount.setVisibility(View.INVISIBLE);
                if (enrollListener != null) {
                    holder.ivBasket.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            enrollListener.onEnroll(productItem);
                        }
                    });

                }

            } else {
                holder.ivBasket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        basketListener.onPutBAsket(productItem);

                        notifyItemChanged(items.indexOf(productItem));
                    }
                });

                if (productItem instanceof BasketProductItem) {
                    int amount = ((BasketProductItem) productItem).getAmountProduct();
                    if (amount > 0) {
                        holder.ivBasket.setImageDrawable(context.getResources().getDrawable(R.drawable.shopping_active));
                        holder.tvAmount.setVisibility(View.VISIBLE);
                        holder.tvAmount.setText(String.valueOf((amount)));
                    } else {
                        holder.tvAmount.setVisibility(View.INVISIBLE);
                    }

                }
            }
        if (holder.swipeLayout != null)
            mItemManger.bindView(holder.itemView, position);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(ArrayList<BasketProductItem> result) {
        items.addAll(result);
        notifyDataSetChanged();
    }

    public void addItems(List<PreViewProductItem> preViewProductItems) {
        items.addAll(preViewProductItems);
        notifyDataSetChanged();
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        public RatingBar ratingBar;
        public TextView tvPrice, tvName, tvDescription, tvAmount;
        public ImageView ivBackground, ivBasket, ivDelete;
        public SwipeLayout swipeLayout;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ratingBar = (RatingBar) itemView.findViewById(R.id.itemFragment_ratingBar);
            tvName = (TextView) itemView.findViewById(R.id.itemFragment_tvName);
            tvPrice = (TextView) itemView.findViewById(R.id.itemFragment_tvPrice);
            ivBackground = (ImageView) itemView.findViewById(R.id.itemFragment_ivBackground);
            ivBasket = (ImageView) itemView.findViewById(R.id.itemFragment_ivBasket);
            tvDescription = (TextView) itemView.findViewById(R.id.itemFragment_tvDescription);
            tvAmount = (TextView) itemView.findViewById(R.id.itemFragment_tvAmount);
            ratingBar.setNumStars(6);

            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.itemFragment_swipe);
            if (swipeLayout != null)
                ivDelete = (ImageView) itemView.findViewById(R.id.itemFragment_ivDelete);
        }

    }

    public void setBasketListener(BasketListener basketListener) {
        this.basketListener = basketListener;
    }

    public void deleteItem(SuperProductItem productItem) {
        items.remove(productItem);
        notifyDataSetChanged();
    }

}
