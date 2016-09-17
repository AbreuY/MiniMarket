package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.models.Blurb;
import com.squareup.picasso.Picasso;

/**
 * Created by Microsoft on 01.06.2016.
 */
public class BlurbFragment extends Basefragment {
    public static final String TAG = "BlurbFragment";
    private ImageView ivBackground;
    private TextView tvTittle, tvSubTittle;

    public interface ClickBlurbListener {
        void onShop(int id);

        void onItem(int id);

        void onLink(String externalUrl);
    }

    public void setBlurb(Blurb blurb) {
        this.blurb = blurb;
    }

    public ClickBlurbListener getClickBlurbListener() {
        return clickBlurbListener;
    }

    public void setClickBlurbListener(ClickBlurbListener clickBlurbListener) {
        this.clickBlurbListener = clickBlurbListener;
    }

    ClickBlurbListener clickBlurbListener;
    private Blurb blurb;

    public static BlurbFragment newInstance(Blurb blurb, BlurbFragment.ClickBlurbListener clickBlurbListener) {
        BlurbFragment blurbFragment = new BlurbFragment();
        blurbFragment.setBlurb(blurb);
        blurbFragment.setClickBlurbListener(clickBlurbListener);
        return blurbFragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    public String getTitle() {
        if (blurb != null) {
            return blurb.getTitle();
        }
        return " ";
    }

    public String getSubTitle() {
        if (blurb != null) {
            return blurb.getContent();
        }
        return " ";
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.blurb_fragment, null);
        initView(v);
        if (blurb != null) {
            Picasso.with(getActivity()).load(blurb.getImage()).into(ivBackground);
            if (blurb.getTitle() != null && !blurb.getTitle().isEmpty()) {
                tvTittle.setText(blurb.getTitle());
            } else tvTittle.setVisibility(View.INVISIBLE);

            if (blurb.getContent() != null && !blurb.getContent().isEmpty()) {
                tvSubTittle.setText(blurb.getContent());
            } else tvSubTittle.setVisibility(View.INVISIBLE);
            ivBackground.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (clickBlurbListener != null) {
                        if (blurb.getItem_id() != 0) {
                            clickBlurbListener.onItem(blurb.getItem_id());
                        } else if (blurb.getShop_id() != 0) {
                            clickBlurbListener.onShop(blurb.getShop_id());
                        }else if(blurb.getExternal_url()!=null){
                            clickBlurbListener.onLink(blurb.getExternal_url());
                        }

                    }

                }
            });
        }


        return v;
    }

    private void initView(View v) {
        ivBackground = (ImageView) v.findViewById(R.id.blurbFragment_ivBackground);
        tvTittle = (TextView) v.findViewById(R.id.blurbFragment_tvTittle);
        tvSubTittle = (TextView) v.findViewById(R.id.blurbFragment_tvSubTittle);
    }

    @Override
    public void onResume() {
        super.onResume();

    }
}
