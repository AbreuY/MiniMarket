package com.balinasoft.minimarket.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.adapters.RoutesRecyclerAdapter;
import com.balinasoft.minimarket.interfaces.ShowFragmentListener;
import com.balinasoft.minimarket.interfaces.UserListener;
import com.balinasoft.minimarket.models.Category;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.RequestUser;
import com.balinasoft.minimarket.networking.Response.ResponseCategories;

import java.util.ArrayList;

/**
 * Created by Microsoft on 02.06.2016.
 */
public class RoutesFragment extends Basefragment {
    public static final String TAG = "RoutesFragment";
    RoutesRecyclerAdapter recyclerAdapter;
    RecyclerView recyclerView;
    ProgressBar progressBar;
    ShowFragmentListener showFragmentListener;
    RoutesFragment thisFragment = this;
    UserListener<? extends User> userListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        showFragmentListener = (ShowFragmentListener) getActivity();
        try {
            userListener = (UserListener<? extends User>) getActivity();
        } catch (ClassCastException e) {

        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerAdapter = new RoutesRecyclerAdapter(new ArrayList<Category>(), getActivity()).setOnClickItemListener(new RoutesRecyclerAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(Category category) {
                Bundle bundle = new Bundle();
                bundle.putInt("CurrentRouteIndex", recyclerAdapter.getCategories().indexOf(category));
                bundle.putParcelableArrayList("RoutesList", recyclerAdapter.getCategories());

                showFragmentListener.showFragmentToolBar(PagerRouteFragment.TAG, bundle);

            }
        });
        RequestUser requestUser = new RequestUser();
        if (userListener != null && userListener.getUser() != null) {
            requestUser.setSession_id(userListener.getUser().getSession_id());
            requestUser.setUser_id(String.valueOf(userListener.getUser().getId()));

            getService().catigories(requestUser).enqueue(new MyCallback<ResponseCategories>() {
                @Override
                public void onData(ResponseCategories data) {
                    if (thisFragment.isAdded())
                        recyclerAdapter.addList(data.getResult());
                }

                @Override
                public void onRequestEnd() {
                    if (thisFragment.isAdded())
                        progressBar.setVisibility(View.INVISIBLE);
                }

            });
        }else {
            getService().catigories().enqueue(new MyCallback<ResponseCategories>() {
                @Override
                public void onData(ResponseCategories data) {
                    if (thisFragment.isAdded())
                        recyclerAdapter.addList(data.getResult());
                }

                @Override
                public void onRequestEnd() {
                    if (thisFragment.isAdded())
                        progressBar.setVisibility(View.INVISIBLE);
                }

            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.routes_fragment, null);
        recyclerView = (RecyclerView) v.findViewById(R.id.routesFragment_recyclerView);
        progressBar = (ProgressBar) v.findViewById(R.id.routesFragment_progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setNestedScrollingEnabled(false);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

}
