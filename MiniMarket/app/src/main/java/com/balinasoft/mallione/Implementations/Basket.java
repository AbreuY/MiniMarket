package com.balinasoft.mallione.Implementations;

import com.balinasoft.mallione.interfaces.Transaction;
import com.balinasoft.mallione.interfaces.TransactionListener;
import com.balinasoft.mallione.models.FireBase.ProductFire;
import com.balinasoft.mallione.models.ProductItems.BasketProductItem;
import com.balinasoft.mallione.models.ProductItems.SuperProductItem;
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

/**
 * Created by Anton on 04.07.2016.
 */
public class Basket implements Transaction {
    FirebaseUser firebaseUser;
    private DatabaseReference databaseReference;
    public static final String TAG_BASKET = "basket";

    public Basket setTransactionListener(TransactionListener<HashMap<String, HashMap<String, ProductFire>>> transactionListener) {
        this.transactionListener = transactionListener;
        return this;
    }

    TransactionListener<HashMap<String, HashMap<String, ProductFire>>> transactionListener;

    public Basket() {
        this.firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser == null) throw new IllegalArgumentException("User no auth in FireBase =)");
        this.databaseReference = FirebaseDatabase.getInstance().getReference();
        ;
    }

    @Override
    public void put(SuperProductItem productItem, String idShop) {
        if (productItem instanceof BasketProductItem)
            databaseReference.child("users").child(firebaseUser.getUid())
                    .child(TAG_BASKET).child(idShop).child(String.valueOf(productItem.getId()))
                    .setValue(new ProductFire(productItem.getId(), ((BasketProductItem) productItem).getAmountProduct()));
        else databaseReference.child("users").child(firebaseUser.getUid())
                .child(TAG_BASKET).child(idShop).child(String.valueOf(productItem.getId()))
                .setValue(new ProductFire(productItem.getId()), 1);
    }


    @Override
    public void delete(SuperProductItem productItem, String idShop) {
        databaseReference.child("users").child(firebaseUser.getUid()).child(TAG_BASKET).child(idShop).child(String.valueOf(productItem.getId())).removeValue();
    }

    public Basket getDataBasket() {
        databaseReference.child("users").child(firebaseUser.getUid()).child(TAG_BASKET).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap<String, HashMap<String, ProductFire>> mapHashMap=null;
                GenericTypeIndicator<HashMap<String, HashMap<String, ProductFire>>> indicator = new GenericTypeIndicator<HashMap<String, HashMap<String, ProductFire>>>() {
                };
                try {
                    mapHashMap = dataSnapshot.getValue(indicator);
                }catch (Exception e){

                }

                if (mapHashMap != null) {
                    transactionListener.onList(mapHashMap);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return this;
    }

    public void amountInsideBasket(SuperProductItem productItem, final CallBack callBack) {
        databaseReference.child("users")
                .child(firebaseUser.getUid()).child(TAG_BASKET)
                .child(String.valueOf(productItem.getShop_id())).child(String.valueOf(productItem.getId())).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ProductFire> indicator = new GenericTypeIndicator<ProductFire>() {
                };
                ProductFire productFire = dataSnapshot.getValue(indicator);
                if (productFire != null) {
                    callBack.onSuccess(productFire);
                } else callBack.onFailure();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callBack.onFailure();
            }
        });
    }

    public void checkBasket(ArrayList<BasketProductItem> basketProductItems, MyValueEventListener.CallBack callBack) {
        try {
            databaseReference.child("users")
                    .child(firebaseUser.getUid()).child(TAG_BASKET).child(String.valueOf(basketProductItems.get(0).getShop_id()))
                    .addListenerForSingleValueEvent(new MyValueEventListener(basketProductItems, callBack));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void delete(String shop_id) {
        databaseReference.child("users")
                .child(firebaseUser.getUid()).child(TAG_BASKET).child(shop_id).removeValue();
    }

    public interface CallBack {

        void onSuccess(ProductFire productFire);

        void onFailure();
    }

    public static class MyValueEventListener implements ValueEventListener {

        public interface CallBack {
            void onCheckProduct(ArrayList<BasketProductItem> productItem);
        }

        CallBack callBack;

        public MyValueEventListener(ArrayList<BasketProductItem> items, CallBack callBack) {
            this.items = items;
            this.callBack = callBack;
        }

        ArrayList<BasketProductItem> items;

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            GenericTypeIndicator<HashMap<String, ProductFire>> indicator = new GenericTypeIndicator<HashMap<String, ProductFire>>() {
            };
            HashMap<String, ProductFire> hashMap = dataSnapshot.getValue(indicator);
            if (hashMap != null && !hashMap.isEmpty())
                for (BasketProductItem item : items) {
                    ProductFire p = hashMap.get(String.valueOf(item.getId()));
                    if (p != null) {
                        item.setAmountProduct(p.getAmount());
                    }
                }
            callBack.onCheckProduct(items);
            items = null;
            callBack = null;

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            callBack.onCheckProduct(items);
            items = null;
            callBack = null;
        }
    }
    public void checkProduct(BasketProductItem productItem, SingleValueListener.Callback callback){
        databaseReference.child("users")
                .child(firebaseUser.getUid()).child(TAG_BASKET)
                .child(String.valueOf(productItem.getShop_id())).child(String.valueOf(productItem.getId())).addListenerForSingleValueEvent(new SingleValueListener(productItem,callback));
    }

    public static class SingleValueListener implements ValueEventListener {
        public interface Callback {
            void onCheckProduct(BasketProductItem productItem);
        }

        private Callback callback;
        BasketProductItem basketProductItem;

        public SingleValueListener(BasketProductItem productItem, SingleValueListener.Callback callBack) {
            this.callback = callBack;
            this.basketProductItem = productItem;
        }

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            ProductFire productFire = dataSnapshot.getValue(ProductFire.class);
            if(productFire!=null){
                basketProductItem.setAmountProduct(productFire.getAmount());
            }
            callback.onCheckProduct(basketProductItem);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {
            callback.onCheckProduct(basketProductItem);
        }
    }
}
