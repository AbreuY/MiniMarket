package com.balinasoft.minimarket.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.interfaces.ToolbarListener;
import com.balinasoft.minimarket.models.Category;
import com.squareup.picasso.Picasso;

/**
 * Created by Microsoft on 02.06.2016.
 */
public class RouteFragment extends Basefragment {
    public static final String TAG="RouteFragment";
    Category category;
    ImageView ivBackground;
    TextView tvTittle;
    boolean noTittle;
    ToolbarListener toolbarListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarListener=(ToolbarListener)getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments()!=null){
            category =getArguments().getParcelable(Category.class.getCanonicalName());
            noTittle=getArguments().getBoolean("tittle");
        }
    }
    LinearLayout ll;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.item_route,null);
        ll=(LinearLayout)v.findViewById(R.id.itemRoute_ll);
        tvTittle=(TextView)v.findViewById(R.id.itemRoute_tvTittle);
        ivBackground=(ImageView)v.findViewById(R.id.itemRoute_ivBackground);
        Picasso.with(getActivity()).load(category.getImage()).into(ivBackground);
        tvTittle.setText(category.getRoute());
        return v;
    }

    @Override
    public void onResume() {

        super.onResume();
        if(noTittle){
            tvTittle.setVisibility(View.INVISIBLE);
            ll.setVisibility(View.GONE);
        }
    }
}
