package com.balinasoft.mallione.interfaces;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Microsoft on 29.06.2016.
 */
public interface FireBaseListener {
    FirebaseAuth getFirebaseAuth();
    FirebaseUser getFirebaseUser();
}
