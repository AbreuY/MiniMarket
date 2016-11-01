package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.balinasoft.mallione.Implementations.Basket;
import com.balinasoft.mallione.R;
import com.balinasoft.mallione.interfaces.OnBackPressedListener;
import com.balinasoft.mallione.interfaces.ShowFragmentListener;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.networking.APIBank;
import com.balinasoft.mallione.networking.ApiFactory;
import com.balinasoft.mallione.networking.Request.RequestConfirmPayment;
import com.balinasoft.mallione.networking.Response.ResponseAnswer;
import com.balinasoft.mallione.networking.Response.ResponseBankStatus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by hzkto on 10/10/2016.
 */

public class BankFragment extends Basefragment implements OnBackPressedListener {
    public static final String TAG = "BankFragment";

    ShowFragmentListener showFragmentListener;
    UserListener<Buer> userListener;
    ToolbarListener toolbarListener;
    String item;
    WebView webView;
    private String session_id;
    private String user_id;
    private String order_id_for_bank;
    private String order_id_for_shop;
    private String shop_id;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userListener = (UserListener<Buer>) getActivity();
        toolbarListener = (ToolbarListener) getActivity();
        toolbarListener.setTittle(getString(R.string.OrderPayment));
        showFragmentListener = (ShowFragmentListener) getActivity();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            item = getArguments().getString("URL");
            session_id = getArguments().getString("session_id");
            user_id = getArguments().getString("user_id");
            order_id_for_bank = getArguments().getString("order_id_for_bank");
            order_id_for_shop = getArguments().getString("order_id_for_shop");
            shop_id = getArguments().getString("shop_id");
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.payment_fragment, null);
        initView(v);
        return v;
    }

    private void initView(View v) {
        webView = (WebView) v.findViewById(R.id.webView);
        webView.setWebViewClient(new MyWebViewClient());
        // включаем поддержку JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        // указываем страницу загрузки
        webView.loadUrl(item);
    }

    @Override
    public int onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            return 1;
        }
        return 0;
    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            if (url.startsWith(APIBank.RETURN_URL)) {
                makeBankRequest();
//                showToast("Оплата проведена успешно. Заказ принят в обработку", true);
                RequestConfirmPayment r = new RequestConfirmPayment(session_id, user_id, order_id_for_shop);
                ApiFactory.getService().confirmPayment(r).enqueue(new Callback<ResponseAnswer>() {
                    @Override
                    public void onResponse(Call<ResponseAnswer> call, Response<ResponseAnswer> response) {
                        if (response.isSuccessful()) {
                            new Basket().delete(shop_id);
//                            getActivity().onBackPressed();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseAnswer> call, Throwable t) {

                    }
                });
            }
//            Toast.makeText(getContext(), "Страничка загружена", Toast.LENGTH_SHORT).show();

            return true;
        }


    }

    public void makeBankRequest() {
//        showToast("Выполняется запрос в банк");
//        String json = new Gson().toJson(requestBankRegister);
        ApiFactory.getBankService().getOrderStatus(order_id_for_bank, APIBank.PASSWORD, APIBank.USER_NAME).enqueue(new Callback<ResponseBankStatus>() {
            @Override
            public void onResponse(Call<ResponseBankStatus> call, Response<ResponseBankStatus> response) {
                try {
                    if (response.body().getErrorCode().equals("0")) {
                        showToast("Обработка запроса прошла без системных ошибок",false);
                    } else {
                        showToast("Статус оплаты заказа не подтвержден", false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBankStatus> call, Throwable t) {

            }
        });
    }
}
