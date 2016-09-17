package com.balinasoft.mallione.Ui.Dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Ui.Activities.BuerActivity;
import com.balinasoft.mallione.Ui.Activities.CourierActivity;
import com.balinasoft.mallione.Ui.Activities.DispatcherActivity;
import com.balinasoft.mallione.Ui.Activities.ManagerActivity;
import com.balinasoft.mallione.Ui.Fragments.LoginFragment;
import com.balinasoft.mallione.Ui.Fragments.RegistrationFragment;
import com.balinasoft.mallione.Utils.AuthManager;
import com.balinasoft.mallione.models.FormModel.BaseForm;
import com.balinasoft.mallione.models.FormModel.LoginForm;
import com.balinasoft.mallione.models.ProductItems.SuperProductItem;
import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.models.modelUsers.Courier;
import com.balinasoft.mallione.models.modelUsers.Dispatcher;
import com.balinasoft.mallione.models.modelUsers.Manager;
import com.balinasoft.mallione.models.modelUsers.User;
import com.balinasoft.mallione.networking.ApiFactory;
import com.balinasoft.mallione.networking.MyCallbackWithMessageError;
import com.balinasoft.mallione.networking.Response.ResponseAnswer;
import com.balinasoft.mallione.networking.Response.ResponseAthorization;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

/**
 * Created by Microsoft on 23.06.2016.
 */
public class DialogLogin extends FullScreenDialog {
    ImageView ivClose;
    LoginFragment loginFragment = new LoginFragment();
    AuthManager authManager;


    public interface LoginUserListener {
        void onBuer(Buer buer);

        void onManager(Manager manager);

        void onCourier(Courier courier);

        void onDispatcher(Dispatcher dispatcher);
    }

    public DialogLogin setLoginUserListener(LoginUserListener loginUserListener) {
        this.loginUserListener = loginUserListener;
        return this;
    }

    public LoginUserListener getLoginUserListener() {
        return loginUserListener;
    }

    LoginUserListener loginUserListener;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        authManager = new AuthManager(getActivity());

        dialogRegistration.setRegistrationListener(new RegistrationFragment.RegistrationListener() {
            @Override
            public void onSuccess(BaseForm baseForm) {
                loginFragment.setPhone(baseForm.getPhone());
                loginFragment.setPassword(baseForm.getPassword());
            }
        });
        loginFragment.setButtonsListener(new LoginFragment.ButtonsListener() {
            @Override
            public void onClickLogin(final LoginForm loginForm) {
                progressBar.setVisibility(View.VISIBLE);
                ApiFactory.getService().login(loginForm).enqueue(new MyCallbackWithMessageError<ResponseAthorization>() {
                    @Override
                    public void onData(ResponseAthorization data) {

                        final User user = data.getResult().getUser();
                        authManager.setAdapter(new AuthManager.RetentiveAdapter() {
                            @Override
                            public User getUser() {
                                return user;
                            }
                        });
                        authManager.save();

                        FirebaseAuth.getInstance().signInWithEmailAndPassword(user.getEmail(), loginForm.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if (user.getClass() == Buer.class) {
                                        loginUserListener.onBuer((Buer) user);
                                    }
                                    if (user.getClass() == Manager.class) {
                                        loginUserListener.onManager((Manager) user);
                                    }
                                    if (user.getClass() == Courier.class) {
                                        loginUserListener.onCourier((Courier) user);
                                    }
                                    if (user.getClass() == Dispatcher.class) {
                                        loginUserListener.onDispatcher((Dispatcher) user);
                                    }
                                    dismiss();
                                } else
                                    Toast.makeText(getActivity(), getString(R.string.tryAgain), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }

                    @Override
                    public void onRequestEnd() {
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            }

            @Override
            public void onClickForgotPassword() {
                if (!editTextDialog.isAdded())
                    editTextDialog.setTitle(getString(R.string.forgotPassword)).setTitleBtn(getString(R.string.send)).setClickListener(new EditTextDialog.ClickListener() {
                        @Override
                        public void onClick(String message) {
                            HashMap<String,String> hashMap=new HashMap<String, String>();
                            hashMap.put("email",message);
                            ApiFactory.getService().forgotPassword(hashMap).enqueue(new MyCallbackWithMessageError<ResponseAnswer>() {
                                @Override
                                public void onData(ResponseAnswer data) {
                                    Toast.makeText(getActivity(),data.getResult().getAnswer(),Toast.LENGTH_SHORT).show();
                                    dismiss();
                                }

                                @Override
                                public void onRequestEnd() {

                                }
                            });
                        }
                    }).show(getFragmentManager(), "");
            }

            @Override
            public void onClickRegister() {
                dialogRegistration.show(getFragmentManager(), "fslkdj;l");
            }
        });
    }

    EditTextDialog editTextDialog = new EditTextDialog();
    ProgressBar progressBar;
    DialogRegistration dialogRegistration = new DialogRegistration();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.dialog_login, null);
        getChildFragmentManager().beginTransaction().replace(R.id.dialogLogin_frame, loginFragment).commit();
        ivClose = (ImageView) v.findViewById(R.id.dialogLogin_ivClose);
        progressBar = (ProgressBar) v.findViewById(R.id.dialogLogin_progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return v;
    }

    abstract public static class SimpleLoginListener implements LoginUserListener {

        @Override
        public void onBuer(Buer buer) {
            onUser(buer);
        }

        @Override
        public void onManager(Manager manager) {
            onUser(manager);
        }

        @Override
        public void onCourier(Courier courier) {
            onUser(courier);
        }

        @Override
        public void onDispatcher(Dispatcher dispatcher) {
            onUser(dispatcher);
        }

        abstract void onUser(User user);
    }

    public static class StartActivityLogin implements LoginUserListener {
        private Context context;
        private SuperProductItem superProductItem;

        public StartActivityLogin(Context context, SuperProductItem productItem) {
            this(context);
            this.superProductItem = productItem;
        }

        public StartActivityLogin(Context context) {
            this.context = context;
        }

        @Override
        public void onBuer(Buer buer) {
            Intent intent = new Intent(context, BuerActivity.class);
            if (superProductItem != null)
                intent.putExtra(SuperProductItem.class.getCanonicalName(), superProductItem);
            startActivity(intent);
        }

        @Override
        public void onManager(Manager manager) {
            Intent intent = new Intent(context, ManagerActivity.class);
            intent.putExtra(Manager.class.getCanonicalName(), manager);
            startActivity(intent);
        }

        @Override
        public void onCourier(Courier courier) {
            Intent intent = new Intent(context, CourierActivity.class);
            startActivity(intent);
        }

        @Override
        public void onDispatcher(Dispatcher dispatcher) {
            Intent intent = new Intent(context, DispatcherActivity.class);
        }

        private void startActivity(Intent intent) {
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

}
