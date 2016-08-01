package com.balinasoft.minimarket.Ui.Dialogs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.adapters.ItemsAdapterRecyclerView;
import com.balinasoft.minimarket.interfaces.ToolbarListener;
import com.balinasoft.minimarket.interfaces.UserListener;
import com.balinasoft.minimarket.models.ProductItems.PreViewProductItem;
import com.balinasoft.minimarket.models.ProductItems.SuperProductItem;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.balinasoft.minimarket.networking.ApiFactory;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.SearchRequest;
import com.balinasoft.minimarket.networking.Response.ResponseSearch;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

/**
 * Created by Anton Kolotsey on 20.07.2016.
 */
public class DialogSearch extends FullScreenDialog {
    ToolbarListener toolbarListener;
    UserListener<? extends User> buerUserListener;

    public DialogSearch setClickItemListener(ItemsAdapterRecyclerView.ClickItemListener clickItemListener) {
        this.clickItemListener = clickItemListener;
        return this;
    }

    ItemsAdapterRecyclerView.ClickItemListener clickItemListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarListener = (ToolbarListener) getActivity();
        try {
            buerUserListener = (UserListener<? extends User>) getActivity();
        } catch (ClassCastException e) {

        }
        itemsAdapterRecyclerView = new ItemsAdapterRecyclerView(getActivity(), new ArrayList<PreViewProductItem>(), R.layout.item_item_favorites);
        itemsAdapterRecyclerView.setVisibleBasket(false);
        itemsAdapterRecyclerView.setClickItemListener(new ItemsAdapterRecyclerView.ClickItemListener() {
            @Override
            public void onClick(SuperProductItem productItem) {
                if (clickItemListener != null) {
                    clickItemListener.onClick(productItem);
                    dismiss();
                }
            }
        });
        if (buerUserListener != null)
            itemsAdapterRecyclerView.setUser(buerUserListener.getUser());

    }

    SearchRequest searchRequest;
    ItemsAdapterRecyclerView itemsAdapterRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (buerUserListener != null && buerUserListener.getUser() != null) {
            searchRequest = new SearchRequest(buerUserListener.getUser().getSession_id(), String.valueOf(buerUserListener.getUser().getId()), "");
        } else searchRequest = new SearchRequest();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.search_dialog, null);
        initView(v);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edTxSearch.getText().toString().length() > 0) {
                    edTxSearch.setText("");
                    itemsAdapterRecyclerView.clear();
                } else {
                    dismiss();
                }
            }
        });

        recyclerView.setAdapter(itemsAdapterRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                searchRequest.setOffset(itemsAdapterRecyclerView.getItemCount());
                addResult();
            }
        });
        edTxSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchRequest.setOffset(0);
                searchRequest.setSearch(s.toString());
                if (!s.toString().isEmpty()) {

                        ApiFactory.getService().search(searchRequest).enqueue(new MyCallback<ResponseSearch>() {
                            @Override
                            public void onData(ResponseSearch data) {
                                itemsAdapterRecyclerView.setItems(data.getResult());
                            }

                            @Override
                            public void onRequestEnd() {

                            }
                        });

                } else itemsAdapterRecyclerView.clear();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
    }

    private void addResult() {
        ApiFactory.getService().search(searchRequest).enqueue(new MyCallback<ResponseSearch>() {
            @Override
            public void onData(ResponseSearch data) {

                itemsAdapterRecyclerView.addItems(data.getResult());
                searchRequest.setOffset(itemsAdapterRecyclerView.getItemCount());
            }

            @Override
            public void onRequestEnd() {
                swipyRefreshLayout.setRefreshing(false);
            }
        });
    }

    ImageView ivBack;
    EditText edTxSearch;
    RecyclerView recyclerView;
    SwipyRefreshLayout swipyRefreshLayout;

    private void initView(View v) {
        ivBack = (ImageView) v.findViewById(R.id.searchDialog_ivBack);
        Drawable upArrow = getResources().getDrawable(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        upArrow.setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        ivBack.setImageDrawable(upArrow);
        edTxSearch = (EditText) v.findViewById(R.id.searchDialog_edTxSearch);
        recyclerView = (RecyclerView) v.findViewById(R.id.searchDialog_recyclerView);
        swipyRefreshLayout = (SwipyRefreshLayout) v.findViewById(R.id.searchDialog_swipe);
    }

}
