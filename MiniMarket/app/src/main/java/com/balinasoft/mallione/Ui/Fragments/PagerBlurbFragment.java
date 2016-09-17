package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Activities.BasketItemsActivity;
import com.balinasoft.mallione.Ui.Dialogs.DialogItemFragment;
import com.balinasoft.mallione.adapters.MyFragmentPagerAdapter;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.ToolbarSettingsListener;
import com.balinasoft.mallione.models.Blurb;
import com.balinasoft.mallione.models.ProductItems.SuperProductItem;
import com.balinasoft.mallione.models.Shops.Shop;
import com.balinasoft.mallione.networking.CallBackWithoutMessage;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.PaginationRequest;
import com.balinasoft.mallione.networking.Response.ResponseBlurb;
import com.balinasoft.mallione.networking.Response.ResponseItem;
import com.balinasoft.mallione.networking.Response.ResponseShop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Microsoft on 01.06.2016.
 */
public class PagerBlurbFragment extends Basefragment {
    public static final String TAG = "PagerBlurbFragment";
    ViewPager viewPager;
    MyFragmentPagerAdapter pagerAdapter;
    Handler handlerBlurb = new Handler();
    int countBlurb;
    int i = 0;
    PaginationRequest paginationRequest;
    Runnable runnableAutoScroll = new Runnable() {


        @Override
        public void run() {
            smoothScroll();
            handlerBlurb.postDelayed(this, 4000);
        }

        public void smoothScroll() {
            if (!(i < countBlurb)) {
                i = 0;
                if (countBlurb > 0 && pagerAdapter != null && pagerAdapter.getCount() > 0) {
                    paginationRequest.setOffset(countBlurb);
                    addBlurb();
                }
            }
            if (i == 0) {
                viewPager.setCurrentItem(i, false);
            } else viewPager.setCurrentItem(i, true);

            i++;
        }
    };
    ToolbarSettingsListener toolbarSettingsListener;

    @Override
    public void onResume() {
        super.onResume();
        handlerBlurb.postDelayed(runnableAutoScroll, 0);
        toolbarListener.setTittle(getString(R.string.app_name));
        showFragmentListener.showCollapseFragment(RoutesFragment.TAG, null, false);
        toolbarListener.openToolbar();
        toolbarSettingsListener.hideTitle();


    }

    @Override
    public void onPause() {
        super.onPause();
        handlerBlurb.removeCallbacks(runnableAutoScroll);
        toolbarSettingsListener.showitle();
    }

    ShowFragmentListener showFragmentListener;
    ToolbarListener toolbarListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        showFragmentListener = (ShowFragmentListener) getActivity();
        toolbarListener = (ToolbarListener) getActivity();
        toolbarSettingsListener = (ToolbarSettingsListener) getActivity();
        paginationRequest = new PaginationRequest(0, 10);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pagerAdapter = new MyFragmentPagerAdapter(getChildFragmentManager(), new ArrayList<Fragment>());
        addBlurb();

    }

    DialogItemFragment dialogItemFragment = new DialogItemFragment();

    void addBlurb() {
        getService().blurb(paginationRequest).enqueue(new CallBackWithoutMessage<ResponseBlurb>() {
            @Override
            public void onData(ResponseBlurb data) {
                if (data.getResult() != null) {
                    List<Fragment> fragments = new ArrayList<Fragment>();
                    for (Blurb blurb : data.getResult()) {
                        fragments.add(BlurbFragment.newInstance(blurb, new BlurbFragment.ClickBlurbListener() {
                            @Override
                            public void onShop(int id) {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("shop_id", String.valueOf(id));
                                getService().shop(hashMap).enqueue(new MyCallbackWithMessageError<ResponseShop>() {
                                    @Override
                                    public void onData(ResponseShop data) {
                                        if (data.getResult() != null) {
                                            Intent intent = new Intent(getActivity(), BasketItemsActivity.class);
                                            intent.putExtra(Shop.class.getCanonicalName(), data.getResult());
                                            getActivity().startActivity(intent);
                                        }
                                    }

                                    @Override
                                    public void onRequestEnd() {

                                    }
                                });
                            }

                            @Override
                            public void onItem(int id) {
                                HashMap<String, String> hashMap = new HashMap<String, String>();
                                hashMap.put("item_id", String.valueOf(id));
                                getService().item(hashMap).enqueue(new MyCallbackWithMessageError<ResponseItem>() {
                                    @Override
                                    public void onData(final ResponseItem data) {
                                        if (!dialogItemFragment.isAdded()) {
                                            dialogItemFragment.setProductListener(new DialogItemFragment.ProductListener() {
                                                @Override
                                                public SuperProductItem getProduct() {
                                                    return data.getResult();
                                                }
                                            });
                                            dialogItemFragment.show(getFragmentManager(), "");
                                        }
                                    }

                                    @Override
                                    public void onRequestEnd() {

                                    }
                                });
                            }

                            @Override
                            public void onLink(String s) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(s));
                                startActivity(browserIntent);
                            }
                        }));
                    }
                    if (fragments != null && fragments.size() > 0)
                        pagerAdapter.addListFragment(fragments);
                    countBlurb = pagerAdapter.getCount();
                }
            }

            @Override
            public void onRequestEnd() {

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.pager_blurb_fragment, null);
        initView(v);
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                i = position;
                if (pagerAdapter != null && pagerAdapter.getCount() - 1 == i && pagerAdapter.getCount() > 0) {


                    paginationRequest.setOffset(pagerAdapter.getCount());
                    addBlurb();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        return v;
    }

    private void initView(View v) {
        viewPager = (ViewPager) v.findViewById(R.id.pagerBlurbFragment_viewPager);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
