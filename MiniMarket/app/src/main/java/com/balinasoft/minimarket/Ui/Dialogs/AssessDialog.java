package com.balinasoft.minimarket.Ui.Dialogs;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.interfaces.UserListener;
import com.balinasoft.minimarket.models.Order;
import com.balinasoft.minimarket.models.modelUsers.Buer;
import com.balinasoft.minimarket.networking.ApiFactory;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.RequestAssess;
import com.balinasoft.minimarket.networking.Response.ResponseAnswer;

/**
 * Created by Microsoft on 15.07.2016.
 */
public class AssessDialog extends BaseDialog {
    public static final String ORDER="ORDER";
    RatingBar ratingBarShop, ratingBarCourier;
    EditText edTXAssess;
    Button btnAssess;
    RequestAssess requestAssess;
    UserListener<Buer> userListener;

    public AssessDialog setTypeAssess(String typeAssess) {
        this.typeAssess = typeAssess;
        return this;
    }

    String typeAssess;
    public Order getOrder() {
        return order;
    }

    public AssessDialog setOrder(Order order) {

        this.order = order;
        return this;
    }

    Order order;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userListener = (UserListener<Buer>) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestAssess = new RequestAssess();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.assess_dialog, null);
        initView(v);
        btnAssess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestAssess.setUser_id(String.valueOf(userListener.getUser().getId()));
                requestAssess.setSession_id(userListener.getUser().getSession_id());
                requestAssess.setComment(edTXAssess.getText().toString());
                requestAssess.setCourier_score(String.valueOf(ratingBarCourier.getRating()));
                requestAssess.setShop_score(String.valueOf(ratingBarShop.getRating()));
                if(typeAssess!=null && typeAssess.equals(ORDER)){
                    requestAssess.setOrder_id(String.valueOf(order.getId()));
                }
                ApiFactory.getService().addReview(requestAssess).enqueue(new MyCallback<ResponseAnswer>() {
                    @Override
                    public void onData(ResponseAnswer data) {
                        order.setReview_status("0");
                        Toast.makeText(getActivity(), data.getResult().getAnswer(), Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void onRequestEnd() {

                    }
                });

            }
        });
        return v;
    }

    private void initView(View v) {
        ratingBarCourier = (RatingBar) v.findViewById(R.id.assessDialog_ratingBarCourier);
        ratingBarShop = (RatingBar) v.findViewById(R.id.assessDialog_ratingBarShop);
        edTXAssess = (EditText) v.findViewById(R.id.assessDialog_edTxComment);
        btnAssess = (Button) v.findViewById(R.id.assessDialog_btnAssess);
    }
}
