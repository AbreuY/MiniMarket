package com.balinasoft.minimarket.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.Fragments.PageItemFragment;
import com.balinasoft.minimarket.interfaces.BasketListener;
import com.balinasoft.minimarket.models.Comments.Comment;
import com.balinasoft.minimarket.models.Image;
import com.balinasoft.minimarket.models.ProductItems.BasketProductItem;
import com.balinasoft.minimarket.models.ProductItems.FullProductItem;
import com.balinasoft.minimarket.models.ProductItems.SuperProductItem;
import com.balinasoft.minimarket.models.Shops.Shop;
import com.balinasoft.minimarket.models.UrlImage;
import com.balinasoft.minimarket.models.modelUsers.Buer;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.merhold.extensiblepageindicator.ExtensiblePageIndicator;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Microsoft on 01.07.2016.
 */
public class AdapterItemFragment extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public User getUser() {
        return user;
    }

    private User user;

    public void setBasketListener(BasketListener basketListener) {
        this.basketListener = basketListener;
    }

    BasketListener basketListener;

    public void setClickLikeListener(ClickLikeListener clickLikeListener) {
        this.clickLikeListener = clickLikeListener;
    }

    ClickLikeListener clickLikeListener;

    public void setUser(User user) {
        this.user = user;
    }

    public interface BackListener {
        void onBackPress();
    }

    public interface ClickPageListener {
        void onClick(String urlImage);
    }
    public interface ClickLikeListener{
        boolean onClick(SuperProductItem productItem);
        boolean unClick(SuperProductItem productItem);
    }
    public interface ClickShopListener{
        boolean onClick(Shop shop);
    }
    public static final int FRAGMENT_ITEM_PICTURE = 0;
    public static final int SUPER_ITEM_PRODUCT = 1;
    public static final int SHOP_ITEM = 2;
    public static final int DESCRIPTION_ITEM = 3;
    public static final int RATING_ITEM = 4;
    public static final int COMMENT_ITEM = 5;
    Context context;
    List<Object> objects = new ArrayList<>();
    FragmentManager fragmentManager;

    public void setBackListener(BackListener backListener) {
        this.backListener = backListener;
    }

    BackListener backListener;

    public void setClickPageListener(ClickPageListener clickPageListener) {
        this.clickPageListener = clickPageListener;
    }

    ClickPageListener clickPageListener;

    public void setClickShopListener(ClickShopListener clickShopListener) {
        this.clickShopListener = clickShopListener;
    }

    ClickShopListener clickShopListener;
    BasketProductItem superProductItem;
    public AdapterItemFragment(FullProductItem fullProductItem, Context context, FragmentManager fragmentManager,int amoutBAsketProdukt) {
        this.fragmentManager = fragmentManager;
        this.context = context;
        superProductItem=new BasketProductItem(fullProductItem.getId(), fullProductItem.getPrice(), fullProductItem.getItem(),String.valueOf(fullProductItem.getShop().getId()),fullProductItem.getRoute_id());
        superProductItem.setAmountProduct(amoutBAsketProdukt);
        objects.add(new Pictures(fullProductItem.getImages()));
        objects.add(new Rating(fullProductItem.getRating()));
        objects.add(superProductItem);
        objects.add(fullProductItem.getShop());
        objects.add(new Description(fullProductItem.getDescription()));


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        switch (viewType) {
            case FRAGMENT_ITEM_PICTURE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_item, parent, false);
                return new FragmentPagerItemViewHolder(v);
            case RATING_ITEM:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rating_item, parent, false);
                return new RatingViewHolder(v);
            case SUPER_ITEM_PRODUCT:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.super_item_product, parent, false);
                return new SuperItemViewHolder(v);
            case SHOP_ITEM:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_item_inside_reycl, parent, false);
                return new ShopViewHolder(v);
            case DESCRIPTION_ITEM:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.description_item, parent, false);
                return new DescriptionViewHolder(v);
            case COMMENT_ITEM:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.comment_item, parent, false);
                return new CommentViewHolder(v);
        }
        return null;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
       if( holder.getClass()== FragmentPagerItemViewHolder.class){
           FragmentPagerItemViewHolder  viewHolder=(FragmentPagerItemViewHolder)holder;
           viewHolder.pagerAdapter=new MyFragmentPagerAdapter(fragmentManager,new ArrayList<Fragment>());
           viewHolder.viewPager.setAdapter(viewHolder.pagerAdapter);
         //  viewHolder.extensiblePageIndicator.initViewPager(viewHolder.viewPager);
       }
        super.onViewRecycled(holder);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object o = objects.get(position);
        if (holder.getClass()== FragmentPagerItemViewHolder.class) {
            createPagerFragment((Pictures) o, (FragmentPagerItemViewHolder) holder);
        }
        if (holder.getClass()==  RatingViewHolder.class) {
            createRatingItem((Rating) o, (RatingViewHolder) holder);
        }
        if (holder.getClass()==  SuperItemViewHolder.class) {
            createSuperProductItem((SuperProductItem) o, (SuperItemViewHolder) holder);
        }
        if (holder.getClass()== ShopViewHolder.class) {
            createShop((Shop) o, (ShopViewHolder) holder);
        }
        if (holder.getClass()==  DescriptionViewHolder.class) {
            createDescription((Description) o, (DescriptionViewHolder) holder);
        }
        if (holder.getClass()==  CommentViewHolder.class) {
            createComment((Comment) o, (CommentViewHolder) holder);
        }
    }

    private void createPagerFragment(Pictures o, final FragmentPagerItemViewHolder holder) {
        holder.pagerAdapter=new MyFragmentPagerAdapter(fragmentManager,new ArrayList<Fragment>());

        for (String s : o.urls) {
            //holder.pagerAdapter.clear();

            holder.pagerAdapter.addFragment(PageItemFragment.getInstance(s).setClickItemListener(new PageItemFragment.ClickItemListener() {
                @Override
                public void onClickItem(String url) {
                    clickPageListener.onClick(url);
                }
            }));
        }
        holder.viewPager.setAdapter(holder.pagerAdapter);
        holder.extensiblePageIndicator.initViewPager(holder.viewPager);
      //  holder.extensiblePageIndicator.initViewPager(holder.viewPager);

        if (o.urls.size() > 1) {
            holder.extensiblePageIndicator.setVisibility(View.VISIBLE);
        } else holder.extensiblePageIndicator.setVisibility(View.INVISIBLE);
        if(isLike) {
            holder.ivLike.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_active));
        }else holder.ivLike.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));

        holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                holder.extensiblePageIndicator.onPageScrolled(position,positionOffset,positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
                holder.extensiblePageIndicator.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        holder.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backListener.onBackPress();
            }
        });
        if(user==null || user.getClass()==Buer.class) {


            holder.ivLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isLike && clickLikeListener != null) {
                        if (clickLikeListener.onClick(superProductItem)) {
                            holder.ivLike.setImageDrawable(context.getResources().getDrawable(R.drawable.heart_active));
                        }
                        isLike = false;
                    } else {
                        holder.ivLike.setImageDrawable(context.getResources().getDrawable(R.drawable.heart));
                        clickLikeListener.unClick(superProductItem);
                        isLike = true;
                    }
                }
            });
        }else holder.ivLike.setVisibility(View.INVISIBLE);
    }

    public void setLike(boolean like) {
        isLike = like;
        notifyDataSetChanged();
    }

    boolean isLike=false;
    private void createComment(Comment comment, CommentViewHolder holder) {
        holder.tvName.setText(comment.getUser().getFio());
        holder.tvComment.setText(comment.getComment());
        holder.ratingBar.setRating(Float.valueOf(comment.getScore()));
        holder.tvDate.setText(comment.getDate_time());

    }

    private void createDescription(Description description, DescriptionViewHolder holder) {
        holder.tvDescription.setText(description.getDescription());
    }

    private void createShop(final Shop shop, ShopViewHolder holder) {
        holder.tvName.setText(shop.getShop());
        holder.tvDescription.setText(shop.getDescription());
        Picasso.with(context).load(shop.getImage()).into(holder.ivShop);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(clickShopListener!=null){
                    clickShopListener.onClick(shop);
                }
            }
        });
    }
    public interface EnrollListener{
        void onClickEnroll(SuperProductItem superProductItem);
    }

    public void setEnrollListener(EnrollListener enrollListener) {
        this.enrollListener = enrollListener;
    }

    EnrollListener enrollListener;

    private void createSuperProductItem(final SuperProductItem product, SuperItemViewHolder holder) {
        holder.tvName.setText(product.getItem());
        holder.tvPrice.setText(product.getPrice());

        if(user==null || user.getClass()== Buer.class) {
            if(superProductItem.getRoute_id().equals("1")){
                if(superProductItem.getAmountProduct()>0){
                    holder.tvAmountInsideBasket.setText(String.valueOf(superProductItem.getAmountProduct()));
                    holder.ivBasket.setImageDrawable(context.getResources().getDrawable(R.drawable.shopping_active));
                }
                if(superProductItem.getAmountProduct()==0){
                    holder.tvAmountInsideBasket.setVisibility(View.GONE);
                }
                holder.ivBasket.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (basketListener != null)
                            basketListener.onPutBAsket(product);
                    }
                });
                holder.ivEnroll.setVisibility(View.INVISIBLE);
            }else {
                holder.ivEnroll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(enrollListener!=null){
                            enrollListener.onClickEnroll(product);
                        }
                    }
                });
                holder.tvAmountInsideBasket.setVisibility(View.INVISIBLE);
                holder.ivBasket.setVisibility(View.INVISIBLE);
            }


        }else {
            holder.ivBasket.setVisibility(View.INVISIBLE);
            holder.ivEnroll.setVisibility(View.INVISIBLE);
        }
    }

    private void createRatingItem(Rating rating, RatingViewHolder holder) {
        if (rating != null && rating.getRating() != null && !rating.getRating().isEmpty())
            holder.ratingBar.setRating(Float.valueOf(rating.getRating()));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object o = objects.get(position);
        if (o.getClass()== Pictures.class) {
            return FRAGMENT_ITEM_PICTURE;
        }
        if (o.getClass()==  Rating.class) {
            return RATING_ITEM;
        }
        if (o.getClass()==  BasketProductItem.class) {
            return SUPER_ITEM_PRODUCT;
        }
        if (o.getClass()==  Shop.class) {
            return SHOP_ITEM;
        }
        if (o.getClass()==  Description.class) {
            return DESCRIPTION_ITEM;
        }
        if (o.getClass()==  Comment.class) {
            return COMMENT_ITEM;
        }
        return RecyclerView.INVALID_TYPE;
    }

    public static class Pictures {

        ArrayList<String> urls;
        public Pictures(List<UrlImage> urlImages){
            this.urls = new ArrayList<>();
            for (UrlImage m:urlImages){
                urls.add(m.getImage());
            }
        }
        public Pictures(ArrayList<Image> images) {
            this.urls = new ArrayList<>();
            for (Image image : images) {
                urls.add(image.getImage());
            }
        }

        public ArrayList<String> getPictures() {
            return urls;
        }

        public void setPictures(ArrayList<String> urls) {
            this.urls = urls;
        }

    }

    public static class Description {
        public Description(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        private String description;
    }

    public static class Rating {

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        String rating;

        public Rating(String rating) {
            this.rating = rating;
        }
    }

    public static class SuperItemViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvAmountInsideBasket, tvPrice;
        ImageView ivBasket,ivEnroll;


        public SuperItemViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.superProductItem_tvName);
            tvAmountInsideBasket = (TextView) itemView.findViewById(R.id.superProductItem_tvAmountProduct);
            ivBasket = (ImageView) itemView.findViewById(R.id.superProductItem_ivBasket);
            tvPrice = (TextView) itemView.findViewById(R.id.superProductItem_tvPrice);
            ivEnroll=(ImageView)itemView.findViewById(R.id.superProductItem_ivRecord);
        }
    }

    public static class ShopViewHolder extends RecyclerView.ViewHolder {
        ImageView ivShop;
        TextView tvName, tvDescription;

        public ShopViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.shopItemInsideRec_tvName);
            tvDescription = (TextView) itemView.findViewById(R.id.shopItemInsideRec_tvDescription);
            ivShop = (ImageView) itemView.findViewById(R.id.shopItemInsideRec_ivImage);
        }
    }

    public static class DescriptionViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescription;

        public DescriptionViewHolder(View itemView) {
            super(itemView);
            tvDescription = (TextView) itemView.findViewById(R.id.description_tvDescription);
        }
    }

    public static class RatingViewHolder extends RecyclerView.ViewHolder {
        RatingBar ratingBar;

        public RatingViewHolder(View v) {
            super(v);
            ratingBar = (RatingBar) v.findViewById(R.id.ratingItem_ratingBar);
        }
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDate, tvComment;
        RatingBar ratingBar;

        public CommentViewHolder(View v) {
            super(v);
            tvName = (TextView) v.findViewById(R.id.commentItem_tvName);
            tvDate = (TextView) v.findViewById(R.id.commentItem_tvDate);
            tvComment = (TextView) v.findViewById(R.id.commentItem_tvComment);
            ratingBar = (RatingBar) v.findViewById(R.id.commentItem_ratingBar);
        }
    }

    public void addItem(Object o) {
        objects.add(o);
        notifyDataSetChanged();
    }

    public void addComments(ArrayList<Comment> objects) {
        this.objects.addAll(objects);
        notifyDataSetChanged();
    }

    public static class FragmentPagerItemViewHolder extends RecyclerView.ViewHolder {
        ViewPager viewPager;
        ImageView ivLike, ivBack;
        ExtensiblePageIndicator extensiblePageIndicator;
        MyFragmentPagerAdapter pagerAdapter;

        public FragmentPagerItemViewHolder(View v) {
            super(v);
            viewPager = (ViewPager) v.findViewById(R.id.pagerItem_viewPager);
            ivLike = (ImageView) v.findViewById(R.id.pagerItem_ivLike);
            ivBack = (ImageView) v.findViewById(R.id.pagerItem_ivBack);
            extensiblePageIndicator = (ExtensiblePageIndicator) v.findViewById(R.id.pagerItem_indicator);

        }
    }

}
