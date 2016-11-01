package com.balinasoft.mallione.Ui.Dialogs;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Utils.MyMultiplePermissionListener;
import com.balinasoft.mallione.Utils.MyPermissionListener;
import com.balinasoft.mallione.adapters.AdapterImage;
import com.balinasoft.mallione.interfaces.UserListener;
import com.balinasoft.mallione.models.Order;
import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.networking.ApiFactory;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Request.RequestAssess;
import com.balinasoft.mallione.networking.Response.ResponseAnswer;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by Microsoft on 15.07.2016.
 */
public class AssessDialog extends BaseDialog {
    public static final String ORDER = "ORDER";
    RatingBar ratingBarShop, ratingBarCourier;
    EditText edTXAssess;
    Button btnAssess, btnAddPhoto;
    RequestAssess requestAssess;
    UserListener<Buer> userListener;
    Order order;
    AdapterImage adapterImage;
    DialogFragmentForSelectedPhoto dialogFragmentForSelectedPhoto;
    RecyclerView recyclerView;
    ViewGroup rootView;
    private PermissionListener storagePermissionListener;
    private String sOrder;

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

    public AssessDialog setOrder(String order) {

        this.sOrder = order;
        return this;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        userListener = (UserListener<Buer>) getActivity();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestAssess = new RequestAssess();
        dialogFragmentForSelectedPhoto = new DialogFragmentForSelectedPhoto().setOnUriListener(new DialogFragmentForSelectedPhoto.OnUriListener() {
            @Override
            public void onUri(Uri uri) {
                adapterImage.addUri(uri);
            }
        });
        adapterImage = new AdapterImage(getActivity(), new ArrayList<Uri>());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.assess_dialog, null);
        Dexter.initialize(getContext());
        createPermissionListeners();
        initView(v);
        recyclerView.setAdapter(adapterImage);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        btnAssess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
                for (Uri uri : adapterImage.getUris()) {
                    File file = new File(uri.getPath());
                    builder.addFormDataPart("img_dispute_photo[]", file.getName(),
                            RequestBody.create(MediaType.parse("image/jpeg"), file));
                }

                requestAssess.setUser_id(String.valueOf(userListener.getUser().getId()));
                requestAssess.setSession_id(userListener.getUser().getSession_id());
                requestAssess.setComment(edTXAssess.getText().toString());
                requestAssess.setCourier_score(String.valueOf((int) ratingBarCourier.getRating()));
                requestAssess.setShop_score(String.valueOf((int) ratingBarShop.getRating()));
                if (typeAssess != null && typeAssess.equals(ORDER)) {
                    requestAssess.setOrder_id(sOrder);
//                    requestAssess.setOrder_id(String.valueOf(order.getId()));
                }
                builder.addFormDataPart("add_review", null, RequestBody.create(MediaType.parse("text/plain"), new Gson().toJson(requestAssess)));
                ApiFactory.getService().addReview(builder.build()).enqueue(new MyCallbackWithMessageError<ResponseAnswer>() {
                    @Override
                    public void onData(ResponseAnswer data) {
//                        order.setReview_status("1");
                        Toast.makeText(getActivity(), data.getResult().getAnswer(), Toast.LENGTH_SHORT).show();
                        dismiss();
                    }

                    @Override
                    public void onFailure(Call<ResponseAnswer> call, Throwable t) {
                        super.onFailure(call, t);
                    }

                    @Override
                    public void onResponse(Call<ResponseAnswer> call, Response<ResponseAnswer> response) {
                        super.onResponse(call, response);
                    }

                    @Override
                    public void onRequestEnd() {

                    }
                });

            }
        });
        btnAddPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Dexter.isRequestOngoing()) {
                    return;
                }
                Dexter.checkPermission(storagePermissionListener, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
        });
        return v;
    }

    void initView(View v) {
        ratingBarCourier = (RatingBar) v.findViewById(R.id.assessDialog_ratingBarCourier);
        ratingBarShop = (RatingBar) v.findViewById(R.id.assessDialog_ratingBarShop);
        edTXAssess = (EditText) v.findViewById(R.id.assessDialog_edTxComment);
        btnAssess = (Button) v.findViewById(R.id.assessDialog_btnAssess);
        btnAddPhoto = (Button) v.findViewById(R.id.assessDialog_btnAddPhoto);
        recyclerView = (RecyclerView) v.findViewById(R.id.sendDisputeFragment_recyclerView);
    }

    private void createPermissionListeners() {
        PermissionListener feedbackViewPermissionListener = new MyPermissionListener(this);
        MultiplePermissionsListener feedbackViewMultiplePermissionListener =
                new MyMultiplePermissionListener(this);
        PermissionListener dialogOnDeniedPermissionListener =
                DialogOnDeniedPermissionListener.Builder.withContext(getContext())
                        .withTitle(R.string.audio_permission_denied_dialog_title)
                        .withMessage(R.string.audio_permission_denied_dialog_feedback)
                        .withButtonText(android.R.string.ok)
                        .withIcon(R.mipmap.ic_launcher)
                        .build();
        storagePermissionListener = new CompositePermissionListener(feedbackViewPermissionListener,
                dialogOnDeniedPermissionListener);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void showPermissionRationale(final PermissionToken token) {
        token.continuePermissionRequest();
    }

    public void showPermissionGranted(String permission) {
        try {
            if (!dialogFragmentForSelectedPhoto.isAdded() && adapterImage.getUris().size() < 10) {
                dialogFragmentForSelectedPhoto.show(getFragmentManager(), "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
