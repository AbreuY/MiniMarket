package com.balinasoft.mallione.interfaces;

import com.balinasoft.mallione.models.modelUsers.Buer;
import com.balinasoft.mallione.models.modelUsers.Courier;
import com.balinasoft.mallione.models.modelUsers.Dispatcher;
import com.balinasoft.mallione.models.modelUsers.Manager;

/**
 * Created by Microsoft on 30.06.2016.
 */
public interface UsersListener {
    void onManager(Manager manager);

    void onCourier(Courier courier);

    void onBuer(Buer buer);

    void onDispatcher(Dispatcher dispatcher);
}
