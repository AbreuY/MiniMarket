package com.balinasoft.mallione.Ui.Dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alexvasilkov.gestures.views.GestureImageView;
import com.balinasoft.mallione.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Microsoft on 02.07.2016.
 */
public class GestureViewDialog extends FullScreenDialog {
    public interface UrlListener {
        String getUrl();
    }

    public GestureViewDialog setUrlListener(UrlListener urlListener) {
        this.urlListener = urlListener;
        return this;
    }

    UrlListener urlListener;
    GestureImageView ivItem;
    ImageView ivBack;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.getsture_view, null);
        ivItem = (GestureImageView) v.findViewById(R.id.gestureViewDialog_getstureView);
        ivBack = (ImageView) v.findViewById(R.id.gestureViewDialog_ivBack);
        if (!urlListener.getUrl().isEmpty())
            Picasso.with(getActivity()).load(urlListener.getUrl()).into(ivItem);
        else dismiss();
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ivItem.getController().getSettings()
                .setMaxZoom(10f);
        return v;
    }


}
