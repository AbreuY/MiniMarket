package com.balinasoft.mallione.interfaces;

import com.balinasoft.mallione.models.modelUsers.User;

/**
 * Created by Anton on 04.07.2016.
 */
public interface UserListener<T extends User> {
    T getUser();
}
