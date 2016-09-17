package com.balinasoft.mallione.interfaces;

/**
 * Created by Microsoft on 04.07.2016.
 */
public interface TransactionListener<T> {
    T getList();
    void onList(T list);
}
