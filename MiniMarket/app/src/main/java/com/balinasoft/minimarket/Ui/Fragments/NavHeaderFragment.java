package com.balinasoft.minimarket.Ui.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.balinasoft.minimarket.R;
import com.balinasoft.minimarket.Ui.Activities.BuerActivity;
import com.balinasoft.minimarket.Ui.Activities.CourierActivity;
import com.balinasoft.minimarket.Ui.Activities.DispatcherActivity;
import com.balinasoft.minimarket.Ui.Activities.MainActivity;
import com.balinasoft.minimarket.Ui.Activities.ManagerActivity;
import com.balinasoft.minimarket.Ui.Dialogs.DialogLogin;
import com.balinasoft.minimarket.Utils.AuthManager;
import com.balinasoft.minimarket.interfaces.UserListener;
import com.balinasoft.minimarket.interfaces.UsersListener;
import com.balinasoft.minimarket.models.modelUsers.Buer;
import com.balinasoft.minimarket.models.modelUsers.Courier;
import com.balinasoft.minimarket.models.modelUsers.Dispatcher;
import com.balinasoft.minimarket.models.modelUsers.Manager;
import com.balinasoft.minimarket.models.modelUsers.User;
import com.balinasoft.minimarket.networking.MyCallback;
import com.balinasoft.minimarket.networking.Request.RequestLogout;
import com.balinasoft.minimarket.networking.Response.BaseResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.iid.FirebaseInstanceId;

/**
 * Created by Microsoft on 27.06.2016.
 */
public class NavHeaderFragment extends Basefragment {
    public interface LoginListener extends UsersListener {
        void onLogout();


    }
    LoginListener loginListener;
    DialogLogin dialogLogin = new DialogLogin();
    TextView tvFio, tvCity;
    Button btnLogin, btnLogout;
    ImageView ivBackGround;
    UserListener<? extends User> userListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            userListener=(UserListener<? extends User>)getActivity();
        }catch (ClassCastException e){

        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogLogin.setLoginUserListener(new DialogLogin.LoginUserListener() {
            @Override
            public void onBuer(Buer buer) {
                fillHeader(buer);
                loginListener.onBuer(buer);
            }

            @Override
            public void onManager(Manager manager) {
                fillHeader(manager);
                loginListener.onManager(manager);
            }

            @Override
            public void onCourier(Courier courier) {
                fillHeader(courier);
                loginListener.onCourier(courier);
            }

            @Override
            public void onDispatcher(Dispatcher dispatcher) {
                fillHeader(dispatcher);
                loginListener.onDispatcher(dispatcher);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.nav_header, null);
        initView(v);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogLogin.show(getActivity().getSupportFragmentManager(), "");
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                new AuthManager(getActivity()).logout();
                btnLogout.setVisibility(View.INVISIBLE);
                btnLogin.setVisibility(View.VISIBLE);

                getService().logout(new RequestLogout(userListener.getUser().getSession_id(),FirebaseInstanceId.getInstance().getToken(),String.valueOf(userListener.getUser().getId()))).enqueue(new MyCallback<BaseResponse>() {
                    @Override
                    public void onData(BaseResponse data) {

                    }

                    @Override
                    public void onRequestEnd() {

                    }
                });
                loginListener.onLogout();
            }
        });
        btnLogout.setVisibility(View.INVISIBLE);
        new AuthManager(getActivity()).setExtractListener(new AuthManager.SimpleExtractListener() {


            @Override
            public void onEmpty() {

            }

            @Override
            public void onUser(User user) {
                tvFio.setText(user.getFio());
                tvCity.setText(user.getCity().getCity());
                btnLogout.setVisibility(View.VISIBLE);
                btnLogin.setVisibility(View.INVISIBLE);
            }
        }).extract();
        return v;
    }

    private void initView(View v) {
        btnLogin = (Button) v.findViewById(R.id.navHeaderFragment_btnLogin);
        btnLogout = (Button) v.findViewById(R.id.navHeaderFragment_btnlogout);
        tvFio = (TextView) v.findViewById(R.id.navHeaderFragment_tvFio);
        tvCity = (TextView) v.findViewById(R.id.navHeaderFragment_tvCity);
        ivBackGround=(ImageView)v.findViewById(R.id.navHeaderFragment_inBackground);
        //Picasso.with(getActivity()).load(R.drawable.background).transform(new BlurTransformation(getActivity(),25)).into(ivBackGround);
    }

    public void setLoginListener(LoginListener loginListener) {
        this.loginListener = loginListener;
    }

    private void fillHeader(User user) {
        tvFio.setText(user.getFio());
        tvCity.setText(user.getCity().getCity());
        btnLogin.setVisibility(View.INVISIBLE);
        btnLogout.setVisibility(View.VISIBLE);
    }
    public static class StartActivityLoginUser implements LoginListener {
        Context context;
        public StartActivityLoginUser(Context context){
            this.context=context;
        }
        @Override
        public void onLogout() {

            Intent intent=new Intent(context, MainActivity.class);
            startActivity(intent);
        }

        @Override
        public void onManager(Manager manager) {
            Intent intent=new Intent(context, ManagerActivity.class);
            intent.putExtra(Manager.class.getCanonicalName(),manager);
            startActivity(intent);
        }

        @Override
        public void onCourier(Courier courier) {
            Intent intent=new Intent(context, CourierActivity.class);
            startActivity(intent);
        }

        @Override
        public void onBuer(Buer buer) {
            Intent intent = new Intent(context, BuerActivity.class);
            intent.putExtra(Buer.class.getCanonicalName(),buer);
            startActivity(intent);
        }

        @Override
        public void onDispatcher(Dispatcher dispatcher) {
            Intent intent=new Intent(context, DispatcherActivity.class);
            startActivity(intent);
        }

        private void startActivity(Intent intent){
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            context.startActivity(intent);
        }
    }
}
