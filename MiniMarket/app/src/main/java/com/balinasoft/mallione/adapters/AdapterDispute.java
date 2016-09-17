package com.balinasoft.mallione.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Fragments.PageItemFragment;
import com.balinasoft.mallione.models.FullDispute;
import com.balinasoft.mallione.models.modelUsers.Buer;

import java.util.ArrayList;
import java.util.List;

import static com.balinasoft.mallione.adapters.AdapterItemFragment.*;

/**
 * Created by Microsoft on 25.07.2016.
 */
public class AdapterDispute extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Object> objects;
    Context context;
    private FullDispute dispute;
    FragmentManager fragmentManager;

    public interface MailClickListener {
        void onMail(String mail);
    }

    public interface PhoneClickListener {
        void onPhone(String phone);
    }

    public void setMailClickListener(MailClickListener mailClickListener) {
        this.mailClickListener = mailClickListener;
    }

    public void setPhoneClickListener(PhoneClickListener phoneClickListener) {
        this.phoneClickListener = phoneClickListener;
    }

    PhoneClickListener phoneClickListener;
    MailClickListener mailClickListener;

    public void setClickPageListener(ClickPageListener clickPageListener) {
        this.clickPageListener = clickPageListener;
    }

    ClickPageListener clickPageListener;

    public AdapterDispute(Context context, List<Object> objects, FragmentManager fragmentManager) {
        this.context = context;
        this.objects = objects;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DATE:
                return new DateViewHolder(getView(R.layout.item_date, parent));
            case STATUS:
                return new StatusViewHolder(getView(R.layout.item_status, parent));
            case THEME:
                return new ThemeViewHolder(getView(R.layout.item_theme, parent));
            case MESSAGE:
                return new MessageViewHolder(getView(R.layout.item_message, parent));
            case IMAGES:
                return new AdapterItemFragment.FragmentPagerItemViewHolder(getView(R.layout.pager_item, parent));
            case BUYER:
                return new BuyerViewHolder(getView(R.layout.item_buyer, parent));
        }
        return null;
    }

    View getView(@LayoutRes int id, ViewGroup viewGroup) {
        return LayoutInflater.from(viewGroup.getContext()).inflate(id, viewGroup, false);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Object o = objects.get(position);
        if (o.getClass() == Date.class) {
            bindDate((Date) o, (DateViewHolder) holder);
        }
        if (o.getClass() == Status.class) {
            binndStatus((Status) o, (StatusViewHolder) holder);
        }
        if (o.getClass() == Theme.class) {
            bindTheme((Theme) o, (ThemeViewHolder) holder);
        }
        if (o.getClass() == Message.class) {
            bindMessage((Message) o, (MessageViewHolder) holder);
        }
        if (o.getClass() == Pictures.class) {
            binndImages((Pictures) o, (AdapterItemFragment.FragmentPagerItemViewHolder) holder);
        }
        if (o.getClass() == Buer.class) {
            bindUser((Buer) o, (BuyerViewHolder) holder);
        }
    }

    private void bindUser(final Buer o, BuyerViewHolder holder) {
        holder.tvFio.setText(o.getFio());
        if (o.getEmail() == null) {
            holder.ivMail.setVisibility(View.INVISIBLE);
        }
        holder.ivMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mailClickListener != null)
                    mailClickListener.onMail(o.getEmail());
            }
        });
        holder.ivPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phoneClickListener != null) {
                    phoneClickListener.onPhone(o.getPhone());
                }
            }
        });
    }

    private void binndImages(Pictures o, final FragmentPagerItemViewHolder holder) {
        if (o.urls.size() > 0) {

            holder.pagerAdapter = new MyFragmentPagerAdapter(fragmentManager, new ArrayList<Fragment>());
            for (String s : o.urls) {
                holder.pagerAdapter.addFragment(PageItemFragment.getInstance(s).setClickItemListener(new PageItemFragment.ClickItemListener() {
                    @Override
                    public void onClickItem(String url) {
                        if (clickPageListener != null)
                            clickPageListener.onClick(url);
                    }
                }));
            }
            holder.viewPager.setAdapter(holder.pagerAdapter);
            holder.extensiblePageIndicator.initViewPager(holder.viewPager);

            if (o.urls.size() > 1) {
                holder.extensiblePageIndicator.setVisibility(View.VISIBLE);
            } else holder.extensiblePageIndicator.setVisibility(View.INVISIBLE);

            holder.ivLike.setVisibility(View.INVISIBLE);
            holder.ivBack.setVisibility(View.INVISIBLE);

            holder.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    holder.extensiblePageIndicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
                }

                @Override
                public void onPageSelected(int position) {
                    holder.extensiblePageIndicator.onPageSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        } else holder.itemView.setVisibility(View.GONE);
    }

    private void bindMessage(Message o, MessageViewHolder holder) {
        holder.tvMessage.setText(o.getText());
    }

    private void bindTheme(Theme o, ThemeViewHolder holder) {
        holder.tvTheme.setText(o.getText());
    }

    private void binndStatus(Status o, StatusViewHolder holder) {
        holder.tvStatus.setText(o.getText());
        if(o.getResult()!=null && !o.getResult().isEmpty()){
            holder.tvResult.setText(o.getResult());
        }else holder.linearLayout.setVisibility(View.GONE);
        if(dispute.isOpen()){
            holder.tvStatus.setTextColor(Color.GREEN);
        }else holder.tvStatus.setTextColor(Color.RED);
    }

    private void bindDate(Date o, DateViewHolder holder) {
        holder.tvDate.setText(o.getText());
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public void setDispute(FullDispute dispute) {
        this.dispute = dispute;
        objects.clear();
        objects.add(new Date(dispute.getDate_time()));
        objects.add(new Status(dispute.getIs_open(),dispute.getResult()));
        objects.add(new Theme(dispute.getTitle()));
        objects.add(new Message(dispute.getMessage()));
        if (dispute.getImages().size() > 0)
            objects.add(new Pictures(dispute.getImages()));
        objects.add(dispute.getUser());
        notifyDataSetChanged();

    }

    public static final int DATE = 1;
    public static final int STATUS = 2;
    public static final int THEME = 3;
    public static final int MESSAGE = 4;
    public static final int IMAGES = 5;
    public static final int BUYER = 6;

    @Override
    public int getItemViewType(int position) {
        Object o = objects.get(position);
        if (o.getClass() == Date.class) {
            return DATE;
        }
        if (o.getClass() == Status.class) {
            return STATUS;
        }
        if (o.getClass() == Theme.class) {
            return THEME;
        }
        if (o.getClass() == Message.class) {
            return MESSAGE;
        }
        if (o.getClass() == Pictures.class) {
            return IMAGES;
        }
        if (o.getClass() == Buer.class) {
            return BUYER;
        }
        return -1;
    }

    public abstract static class Text {
        public Text(String text) {
            this.text = text;
        }

        String text;

        public int getType() {
            return -1;
        }

        public String getText() {
            return text;
        }
    }

    public static class Date extends Text {

        public Date(String text) {
            super(text);
        }
    }

    public static class Status extends Text {
        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        String result;

        public Status(String text, String result) {
            super(text);
            this.result = result;
        }
    }

    public static class Theme extends Text {
        public Theme(String text) {
            super(text);
        }
    }

    public static class Message extends Text {
        public Message(String text) {
            super(text);
        }
    }

    private static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView tvDate;

        public DateViewHolder(View view) {
            super(view);
            tvDate = (TextView) view.findViewById(R.id.itemDate_tvDate);
        }
    }

    private class StatusViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatus;
        TextView tvResult;
        LinearLayout linearLayout;
        public StatusViewHolder(View view) {
            super(view);
            tvStatus = (TextView) view.findViewById(R.id.itemStatus_tvStatus);
            tvResult=(TextView)view.findViewById(R.id.itemStatus_tvResult);
            linearLayout=(LinearLayout)view.findViewById(R.id.itemStatus_ll);
        }
    }

    private class ThemeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTheme;

        public ThemeViewHolder(View view) {
            super(view);
            tvTheme = (TextView) view.findViewById(R.id.itemTheme_tvTheme);
        }
    }

    private class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView tvMessage;

        public MessageViewHolder(View view) {
            super(view);
            tvMessage = (TextView) view.findViewById(R.id.itemMassag_tvMessage);
        }
    }

    private class BuyerViewHolder extends RecyclerView.ViewHolder {
        TextView tvFio;
        ImageView ivMail, ivPhone;

        public BuyerViewHolder(View view) {
            super(view);
            tvFio = (TextView) view.findViewById(R.id.itemBuyer_tvFio);
            ivMail = (ImageView) view.findViewById(R.id.itemBuyer_ivMail);
            ivPhone = (ImageView) view.findViewById(R.id.itemBuyer_ivPhone);
        }
    }
}
