package com.balinasoft.minimarket.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Microsoft on 09.03.2016.
 */
public class BitmapUtil {
    public static Bitmap getBitmapFromUrl(String strUrl, int wight, int height) {
        Bitmap bitmap = getBitmapFromURL(strUrl);
        if (bitmap != null) {
            return scaleBitmap(bitmap, wight, height);
        }
        return null;
    }

    public static Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap scaleBitmap(Bitmap bitmap, int wight, int height) {
        return Bitmap.createScaledBitmap(bitmap, wight, height, false);
    }

    public static Bitmap createCircleBitmap(Bitmap bitmapimg) {
        try {


            Bitmap output = Bitmap.createBitmap(bitmapimg.getWidth(),
                    bitmapimg.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);

            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmapimg.getWidth(),
                    bitmapimg.getHeight());

            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawCircle(bitmapimg.getWidth() / 2,
                    bitmapimg.getHeight() / 2, bitmapimg.getWidth() / 2, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            canvas.drawBitmap(bitmapimg, rect, rect, paint);
            return output;
        } catch (OutOfMemoryError e) {

        }
        return null;
    }

    public static Bitmap drawableToCircleBitmap(Drawable drawable) {
        Bitmap bitmap = drawableToBitmap(drawable);
        return createCircleBitmap(bitmap);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if (bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if (drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    public static Bitmap getBitmap(Context context, Uri imageUri) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), imageUri);
            return bitmap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
