package com.balinasoft.minimarket.Implementations;

import com.balinasoft.minimarket.interfaces.Transaction;
import com.balinasoft.minimarket.interfaces.TransactionListener;
import com.balinasoft.minimarket.models.FireBase.ProductFire;
import com.balinasoft.minimarket.models.ProductItems.SuperProductItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Microsoft on 04.07.2016.
 */
public class MyFavoritesStorage implements Transaction {
    public MyFavoritesStorage setTransactionListener(TransactionListener<List<ProductFire>> transactionListener) {
        this.transactionListener = transactionListener;
        return this;
    }

    TransactionListener<List<ProductFire>> transactionListener;
    public static final String TAG_FIELD = "myFavorites";
    FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;

    public MyFavoritesStorage() {
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) throw new IllegalArgumentException("User no auth =)");
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        ;
    }

    @Override
    public void put(SuperProductItem productItem, String idShop) {
        databaseReference.child("users").child(firebaseUser.getUid()).child(TAG_FIELD).child(String.valueOf(productItem.getId())).setValue(new ProductFire(productItem.getId()));
    }

    @Override
    public void delete(SuperProductItem productItem, String idShop) {
        databaseReference.child("users").child(firebaseUser.getUid()).child(TAG_FIELD).child(String.valueOf(productItem.getId())).removeValue();
    }


    public void getListId() {

        databaseReference.child("users").child(firebaseUser.getUid()).child(TAG_FIELD).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ProductFire> productFires = new ArrayList<ProductFire>();
                GenericTypeIndicator<HashMap<String, ProductFire>> indicator = new GenericTypeIndicator<HashMap<String, ProductFire>>() {
                };
                HashMap<String, ProductFire> productHash = (HashMap<String, ProductFire>) dataSnapshot.getValue(indicator);
                if (productHash != null)
                    for (String s : productHash.keySet()) {
                        productFires.add(productHash.get(s));
                    }
                transactionListener.onList(productFires);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void isFavorites(SuperProductItem productItem, final Callback callback) {
        databaseReference.child("users").child(firebaseUser.getUid()).child(TAG_FIELD).child(String.valueOf(productItem.getId())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ProductFire productFire = dataSnapshot.getValue(ProductFire.class);
                if (productFire != null) {
                    callback.onSuccess();
                } else callback.onFailure();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFailure();
            }
        });
    }

    public interface Callback {
        void onSuccess();

        void onFailure();
    }
}
