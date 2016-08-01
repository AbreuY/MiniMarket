package com.balinasoft.minimarket.Utils;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class MetricsUtil {
    public static final int SIZE_ICON=50;
    public static final int SIZE_PHOTO_MARKER=50;
    public static final int NOTIFICATION_SIZE_ICON=64;
    public static final int SIZE_PHOTO_IN_RECYCLER_VIEW=120;
    public static final int SIZE_SHOP_IN_RECYCLER_VIEW=150;
    public static int convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return (int) px;
    }
}