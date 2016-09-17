package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Dialogs.EditTextDialog;
import com.balinasoft.mallione.Ui.Dialogs.GestureViewDialog;
import com.balinasoft.mallione.adapters.AdapterDispute;
import com.balinasoft.mallione.adapters.AdapterItemFragment;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestCloseDispute;
import com.balinasoft.mallione.networking.Request.RequestFullDispute;
import com.balinasoft.mallione.networking.Response.ResponseAnswer;
import com.balinasoft.mallione.networking.Response.ResponseDisput;

import java.util.ArrayList;

/**
 * Created by Microsoft on 25.07.2016.
 */
public class DisputeDetailsFragment extends Basefragment {
    public static final String TAG = "DisputeDetailsFragment";
    ProgressBar progressBar;
    RecyclerView recyclerView;
    AdapterDispute adapterDispute;
    UserListener<? extends User> userListener;
    ToolbarListener toolbarListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userListener = (UserListener<? extends User>) getActivity();
        toolbarListener = (ToolbarListener) getActivity();
        toolbarListener.setTittle(" ");
        adapterDispute = new AdapterDispute(getActivity(), new ArrayList<Object>(), getChildFragmentManager());
        adapterDispute.setClickPageListener(new AdapterItemFragment.ClickPageListener() {
            @Override
            public void onClick(final String urlImage) {
                new GestureViewDialog().setUrlListener(new GestureViewDialog.UrlListener() {
                    @Override
                    public String getUrl() {
                        return urlImage;
                    }
                }).show(getFragmentManager(),"");
            }
        });
        adapterDispute.setMailClickListener(new AdapterDispute.MailClickListener() {
            @Override
            public void onMail(String mail) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:" + mail));
                Intent intent = Intent.createChooser(emailIntent, "Chooser Title");
                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
        adapterDispute.setPhoneClickListener(new AdapterDispute.PhoneClickListener() {
            @Override
            public void onPhone(String phone) {
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dispute_details, null);
        initView(v);
        recyclerView.setAdapter(adapterDispute);
        if (getArguments() != null) {
            int idDispute = Integer.valueOf(getArguments().getString("ID_DISPUTE"));
            getService().disput(new RequestFullDispute(String.valueOf(idDispute), userListener.getUser().getSession_id(), String.valueOf(userListener.getUser().getId()))).enqueue(new MyCallbackWithMessageError<ResponseDisput>() {
                @Override
                public void onData(ResponseDisput data) {
                    toolbarListener.setTittle(getString(R.string.disput) + " №" + data.getResult().getId());
                    adapterDispute.setDispute(data.getResult());
                    if(!data.getResult().isOpen()){
                        btnClose.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onRequestEnd() {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                new EditTextDialog().setTitle(getString(R.string.result)).setClickListener(new EditTextDialog.ClickListener() {
                    @Override
                    public void onClick(String message) {

                        RequestCloseDispute closeDispute=new RequestCloseDispute(String.valueOf(getArguments().getString("ID_DISPUTE")),
                                userListener.getUser().getSession_id(),
                                String.valueOf(userListener.getUser().getId()));
                        closeDispute.setResult(message);
                        getService().closeDisput(closeDispute).enqueue(new MyCallbackWithMessageError<ResponseAnswer>() {
                            @Override
                            public void onData(ResponseAnswer data) {
                                showToast(data.getResult().getAnswer());
                                getActivity().onBackPressed();
                            }

                            @Override
                            public void onRequestEnd() {
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
                    }
                }).setTitleBtn(getString(R.string.send)).show(getFragmentManager(),"");
            }
        });
        return v;
    }
    Button btnClose;
    private void initView(View v) {
        progressBar = (ProgressBar) v.findViewById(R.id.disputeDetails_progressBar);
        recyclerView = (RecyclerView) v.findViewById(R.id.disputeDetails_recyclerView);
        btnClose=(Button)v.findViewById(R.id.disputeDetails_btnCloseDispute);
    }
}
