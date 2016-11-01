package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Activities.BasketItemsActivity;
import com.balinasoft.mallione.adapters.AdapterNotification;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.Notification;
import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestDeleteNotification;
import com.balinasoft.mallione.networking.Request.RequestUserData;
import com.balinasoft.mallione.networking.Response.BaseResponse;
import com.balinasoft.mallione.networking.Response.ResponseNotification;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

import retrofit2.Call;

import static com.balinasoft.mallione.R.string.notifications;

/**
 * Created by Microsoft on 11.07.2016.
 */
public class NotificationFragment extends Basefragment {
    public static final String TAG = "NotificationFragment";

    RecyclerView recyclerView;
    SwipyRefreshLayout swipyRefreshLayout;
    ToolbarListener toolbarListener;
    AdapterNotification adapterNotification;
    UserListener<? extends User> usersListener;
    RequestUserData requestUserData;
    ShowFragmentListener showFragmentListener;

    private String session_id;
    private String user_id;
    private String notification_id;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarListener = (ToolbarListener) getActivity();
        toolbarListener.closeToolbar();
        toolbarListener.setTittle(getString(notifications));
        usersListener = (UserListener<Buer>) getActivity();
        showFragmentListener = (ShowFragmentListener) getActivity();
        showFragmentListener.showFragmentToolBar(BlurbFragment.TAG, null);
        requestUserData = new RequestUserData(usersListener.getUser().getSession_id(), usersListener.getUser().getId());
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        notification_id = sharedPreferences.getString("session_id", "");

        adapterNotification = new AdapterNotification(getActivity(), new ArrayList<Notification>());
        adapterNotification.setOnItemClickListener(new AdapterNotification.OnItemClickListener() {
            @Override
            public void onOrder(Notification notification) {
                Intent intent = new Intent(getActivity(), BasketItemsActivity.class);
                intent.putExtra("ORDER_ID", notification.getOrder_id());
                getActivity().startActivity(intent);
            }

            @Override
            public void onRecord(Notification notification) {
                showFragmentListener.showFragment(MyServicesFragment.TAG, null, true);
            }

            @Override
            public void onOther(Notification notification) {

            }
        });

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.notification_fragment, null);
        initView(v);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapterNotification);
        //  recyclerView.setNestedScrollingEnabled(false);
        addNotification(false);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, final int swipeDir) {
                UserListener<? extends User> userListener = (UserListener<? extends User>) getContext();

                RequestDeleteNotification request = new RequestDeleteNotification();
                request.setSession_id(userListener.getUser().getSession_id());
                request.setUser_id(userListener.getUser().getId());
                request.setNotification_id(adapterNotification.getItem(viewHolder.getAdapterPosition()).getId());

                getService().deleteNotification(request).enqueue(new MyCallbackWithMessageError<BaseResponse>() {


                    @Override
                    public void onData(BaseResponse data) {
                        if (data.isSuccess()) {
                            int position = viewHolder.getAdapterPosition();
                            adapterNotification.notifyItemRemoved(position);
                            adapterNotification.removeItem(position);
                        }
                    }

                    @Override
                    public void onRequestEnd() {

                    }

                    @Override
                    public void onFailure(Call<BaseResponse> call, Throwable t) {
                        super.onFailure(call, t);
                        adapterNotification.notifyDataSetChanged();
                    }
                });
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);

        itemTouchHelper.attachToRecyclerView(recyclerView);
        return v;
    }

    ProgressBar progressBar;

    private void initView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.notificationFragment_recyclerView);

        swipyRefreshLayout = (SwipyRefreshLayout) v.findViewById(R.id.notificationFragment_swipyRefresh);
        progressBar = (ProgressBar) v.findViewById(R.id.notificationFragment_progressBar);
        swipyRefreshLayout.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                swipyRefreshLayout.setRefreshing(true);
                addNotification(true);
            }
        });


    }

    public void addNotification(final boolean flagSmooth) {
        getService().notifications(requestUserData).enqueue(new MyCallbackWithMessageError<ResponseNotification>() {
            @Override
            public void onData(ResponseNotification data) {

                adapterNotification.addItems(data.getResult());
                int count = adapterNotification.getItemCount();
                if (data.getResult().size() > 0) {
                    requestUserData.setOffset(adapterNotification.getItemCount());

                    if (flagSmooth) {
                        recyclerView.smoothScrollToPosition(count + 1);
                    }
                }

            }

            @Override
            public void onRequestEnd() {
                swipyRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}
