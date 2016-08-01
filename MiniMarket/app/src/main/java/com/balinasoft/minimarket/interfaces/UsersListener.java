package com.balinasoft.minimarket.interfaces;

import com.balinasoft.minimarket.models.modelUsers.Buer;
import com.balinasoft.minimarket.models.modelUsers.Courier;
import com.balinasoft.minimarket.models.modelUsers.Dispatcher;
import com.balinasoft.minimarket.models.modelUsers.Manager;

/**
 * Created by Microsoft on 30.06.2016.
 */
public interface UsersListener {
    void onManager(Manager manager);

    void onCourier(Courier courier);

    void onBuer(Buer buer);

    void onDispatcher(Dispatcher dispatcher);
}
