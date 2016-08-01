package com.balinasoft.minimarket.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.models.Comments.Review;
import com.balinasoft.minimarket.models.Shops.Shop;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Microsoft on 19.07.2016.
 */
public class AdapterShopInfo extends RecyclerView.Adapter {
    List<Object> objects;
    Context context;
    Shop shop;
    public static final int PICTURE = 1;
    public static final int RATING = 2;
    public static final int NAME = 3;
    public static final int ADDRESS = 4;
    public static final int COMMENT = 5;
    public static final int DESCRIPTION = 6;

    public AdapterShopInfo(Context context, Shop shop) {
        objects = new ArrayList<>();
        this.context = context;
        this.shop = shop;
        objects.add(new UrlPicture(shop.getImage()));
        objects.add(new AdapterItemFragment.Rating(shop.getRating()));
        objects.add(new Name(shop.getShop()));
        objects.add(new Address(shop.getAddress(), shop.getPhone()));
        objects.add(new AdapterItemFragment.Description(shop.getDescription()));
        notifyDataSetChanged();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case PICTURE:
                return new ImageViewHolder(getView(parent, R.layout.image_item));
            case RATING:
                return new AdapterItemFragment.RatingViewHolder(getView(parent, R.layout.rating_item));
            case NAME:
                return new NameViewHolder(getView(parent, R.layout.name_item));
            case ADDRESS:
                return new AddressViewHolder(getView(parent, R.layout.address_item));
            case COMMENT:
                return new AdapterItemFragment.CommentViewHolder(getView(parent, R.layout.comment_item));
            case DESCRIPTION:
                return new AdapterItemFragment.DescriptionViewHolder(getView(parent, R.layout.description_item));
        }
        return null;
    }

    public View getView(ViewGroup parent, @LayoutRes int id) {
        return LayoutInflater.from(parent.getContext()).inflate(id, parent, false);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object o = objects.get(position);
        if (o.getClass() == UrlPicture.class) {
            bindPicture((UrlPicture) o, (ImageViewHolder) holder);
        }
        if (o.getClass() == AdapterItemFragment.Rating.class) {
            bindRating((AdapterItemFragment.Rating) o, (AdapterItemFragment.RatingViewHolder) holder);
        }
        if (o.getClass() == Name.class) {
            bindName((Name) o, (NameViewHolder) holder);
        }
        if (o.getClass() == Address.class) {
            bindAddress((Address) o, (AddressViewHolder) holder);
        }
        if (o.getClass() == Review.class) {
            bindComment((Review) o, (AdapterItemFragment.CommentViewHolder) holder);
        }
        if (o.getClass() == AdapterItemFragment.Description.class) {
            bindDescription((AdapterItemFragment.Description) o, (AdapterItemFragment.DescriptionViewHolder) holder);
        }


    }

    private void bindDescription(AdapterItemFragment.Description o, AdapterItemFragment.DescriptionViewHolder holder) {
        holder.tvDescription.setText(o.getDescription());
    }

    private void bindComment(Review o, AdapterItemFragment.CommentViewHolder holder) {
        holder.tvName.setText(o.getUser().getFio());
        holder.tvComment.setText(o.getComment());

        holder.ratingBar.setRating(o.getRatingShop());
        holder.tvDate.setText(o.getDate_time());
    }

    private void bindAddress(Address o, AddressViewHolder holder) {
        holder.tvTime.setText(o.getAddress());
        holder.tvPhone.setText(o.getPhone());
    }

    private void bindName(Name o, NameViewHolder holder) {
        holder.tvName.setText(o.getName());
    }

    private void bindRating(AdapterItemFragment.Rating o, AdapterItemFragment.RatingViewHolder holder) {
        if (o.getRating() != null && !o.getRating().isEmpty())
            holder.ratingBar.setRating(Float.valueOf(o.getRating()));
    }

    private void bindPicture(UrlPicture o, ImageViewHolder holder) {
        if (o != null && o.getUrlPicture() != null && !o.getUrlPicture().isEmpty()) {
            Picasso.with(context).load(o.getUrlPicture()).into(holder.ivShop);
        } else
            holder.ivShop.setImageDrawable(context.getResources().getDrawable(R.drawable.defolt));
    }

    @Override
    public int getItemViewType(int position) {
        Object o = objects.get(position);
        if (o.getClass() == UrlPicture.class) {
            return PICTURE;
        }
        if (o.getClass() == AdapterItemFragment.Rating.class) {
            return RATING;
        }
        if (o.getClass() == Name.class) {
            return NAME;
        }
        if (o.getClass() == Address.class) {
            return ADDRESS;
        }
        if (o.getClass() == Review.class) {
            return COMMENT;
        }
        if (o.getClass() == AdapterItemFragment.Description.class) {
            return DESCRIPTION;
        }
        return -1;
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void addItem(Object o) {
        objects.add(o);
        notifyDataSetChanged();
    }

    public void addItems(List<Object> objects) {
        this.objects.addAll(objects);
        notifyDataSetChanged();
    }

    public void addItems(ArrayList<Review> result) {
        objects.addAll(result);
        notifyDataSetChanged();
    }


    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView ivShop;

        public ImageViewHolder(View itemView) {
            super(itemView);
            ivShop = (ImageView) itemView.findViewById(R.id.imageItem_ivShop);
        }
    }

    public static class NameViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;

        public NameViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.nameItem_tvName);
        }
    }

    public static class AddressViewHolder extends RecyclerView.ViewHolder {

        TextView tvTime, tvPhone;

        public AddressViewHolder(View itemView) {
            super(itemView);
            tvTime = (TextView) itemView.findViewById(R.id.addressItem_tvTime);
            tvPhone = (TextView) itemView.findViewById(R.id.addressItem_tvPhone);
        }
    }

    public static class UrlPicture {
        String urlPicture;

        public UrlPicture(String urlPicture) {
            this.urlPicture = urlPicture;
        }

        public String getUrlPicture() {
            return urlPicture;
        }

        public void setUrlPicture(String urlPicture) {
            this.urlPicture = urlPicture;
        }
    }

    public static class Name {
        public Name(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        String name;
    }

    public static class Address {

        String address, phone;

        public Address(String address, String phone) {
            this.address = address;
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }
    }
}
