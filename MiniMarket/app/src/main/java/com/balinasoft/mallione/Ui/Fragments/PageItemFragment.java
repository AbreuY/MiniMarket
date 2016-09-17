package com.balinasoft.mallione.Ui.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.balinasoft.mallione.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Microsoft on 01.07.2016.
 */
public class PageItemFragment extends Basefragment {
    public interface ClickItemListener{
        void onClickItem(String url);
    }

    public PageItemFragment setClickItemListener(ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
        return this;
    }

    ClickItemListener clickItemListener;

    public static final String TAG = "PageItemFragment";
    ImageView ivItem;

    public void setUrl(String url) {
        this.url = url;
    }

    String url;

    public static PageItemFragment getInstance(String url) {
        PageItemFragment pageItemFragment = new PageItemFragment();
        pageItemFragment.setUrl(url);
        return pageItemFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (url == null)
                url = getArguments().getString("url", "");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.page_item, null);
        ivItem = (ImageView) v.findViewById(R.id.pageItem_ivItem);
        if (url != null && !url.isEmpty())
            Picasso.with(getActivity()).load(url).into(ivItem);
        else ivItem.setImageDrawable(getResources().getDrawable(R.drawable.defolt));
        ivItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickItemListener.onClickItem(url);
            }
        });
        return v;
    }

    public void setonClickListener() {

    }


    public String getUrl() {
        return url;
    }
}
