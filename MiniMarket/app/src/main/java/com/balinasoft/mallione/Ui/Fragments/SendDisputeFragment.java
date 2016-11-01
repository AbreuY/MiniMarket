package com.balinasoft.mallione.Ui.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Dialogs.DialogFragmentForSelectedPhoto;
import com.balinasoft.mallione.adapters.AdapterImage;
import com.balinasoft.mallione.interfaces.ToolbarListener;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.Order;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestOpenDispute;
import com.balinasoft.mallione.networking.Response.ResponseAnswer;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by Microsoft on 26.07.2016.
 */
public class SendDisputeFragment extends Basefragment {
    public static final String TAG = "SendDisputeFragment";
    EditText edTx;
    RecyclerView recyclerView;
    Button btnAddPhoto;
    Button btnSend;
    AdapterImage adapterImage;
    Order order;
    ToolbarListener toolbarListener;
    UserListener<? extends User> userListener;
    private String order_id;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        toolbarListener = (ToolbarListener) getActivity();
        toolbarListener.setTittle(getString(R.string.disput));
        userListener = (UserListener<? extends User>) getActivity();

        if (getArguments() != null) {
            order_id = getArguments().getString("order_id");
//            order=getArguments().getParcelable(Order.class.getCanonicalName());
        }
    }

    DialogFragmentForSelectedPhoto dialogFragmentForSelectedPhoto = new DialogFragmentForSelectedPhoto().setOnUriListener(new DialogFragmentForSelectedPhoto.OnUriListener() {
        @Override
        public void onUri(Uri uri) {

            adapterImage.addUri(uri);
        }
    });


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapterImage = new AdapterImage(getActivity(), new ArrayList<Uri>());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.send_dispute_fragment, null);
        initView(v);
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!dialogFragmentForSelectedPhoto.isAdded() && adapterImage.getUris().size() < 10) {
                    dialogFragmentForSelectedPhoto.show(getFragmentManager(), "");
                }
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                RequestOpenDispute requestOpenDispute = new RequestOpenDispute();
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

                ArrayList<File> files = new ArrayList<File>();
                for (Uri uri : adapterImage.getUris()) {
                    File file = new File(uri.getPath());
                    builder.addFormDataPart("img_dispute_photo[]", file.getName(),
                            RequestBody.create(MediaType.parse("image/jpeg"), file));
                }
                requestOpenDispute.setSession_id(userListener.getUser().getSession_id());
                requestOpenDispute.setUser_id(String.valueOf(userListener.getUser().getId()));
                requestOpenDispute.setMessage(edTx.getText().toString());
                requestOpenDispute.setTitle(edTxTheme.getText().toString());
                requestOpenDispute.setOrder_id(order_id);
//                requestOpenDispute.setOrder_id(String.valueOf(order.getId()));
                builder.addFormDataPart("open_dispute", null, RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(requestOpenDispute)));
                getService().openDispute(builder.build()).enqueue(new MyCallbackWithMessageError<ResponseAnswer>() {
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
        });
        recyclerView.setAdapter(adapterImage);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    ArrayList<File> files = new ArrayList<>();
    EditText edTxTheme;
    ProgressBar progressBar;

    private void initView(View v) {
        edTx = (EditText) v.findViewById(R.id.sendDisputeFragment_edTx);
        recyclerView = (RecyclerView) v.findViewById(R.id.sendDisputeFragment_recyclerView);
        btnAddPhoto = (Button) v.findViewById(R.id.sendDisputeFragment_btnAddphoto);
        btnSend = (Button) v.findViewById(R.id.sendDisputeFragment_btnSend);
        edTxTheme = (EditText) v.findViewById(R.id.sendDisputeFragment_edTxTheme);
        progressBar = (ProgressBar) v.findViewById(R.id.sendDisputeFragment_progressBar);
        progressBar.setVisibility(View.INVISIBLE);
    }
}
