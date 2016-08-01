package com.balinasoft.minimarket.interfaces;

import com.balinasoft.minimarket.models.modelUsers.User;

/**
 * Created by Anton on 04.07.2016.
 */
public interface UserListener<T extends User> {
    T getUser();
}
