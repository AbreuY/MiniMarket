package com.balinasoft.mallione.interfaces;

import android.os.Bundle;

/**
 * Created by Microsoft on 02.06.2016.
 */
public interface ShowFragmentListener {
    void showCollapseFragment(String fragmentTag, Bundle data, boolean backStack);
    void showFragmentToolBar(String fragmentTag, Bundle data);
    void showFragment(String fragmentTag, Bundle data, boolean backStack);
}
