package com.balinasoft.minimarket.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.UUID;

public class FileUtil {
    public static File bitmapToFile(Context context, Bitmap bitmap){
        UUID uuid = UUID.randomUUID();

        return bitmapToFile(context,bitmap,"IMG_"+uuid.toString());
    }
    public static File bitmapToFile(Context context, Bitmap bitmap,Uri uri){
        String[] path=uri.getPath().split("/");
        return bitmapToFile(context,bitmap,path[path.length-1]);
    }
    public static File bitmapToFile(Context context, Bitmap bitmap, String name) {

        File filesDir = context.getApplicationContext().getFilesDir();
        File imageFile = new File(filesDir, name + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os);
            os.flush();
            os.close();
            return imageFile;
        } catch (Exception e) {

        }
        return null;
    }
    public static Uri getUri(Bitmap bitmap){
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root.getAbsolutePath()+"/DCIM/Camera/"+UUID.randomUUID().toString()+".jpg");
        try
        {
            file.createNewFile();
            FileOutputStream ostream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
            ostream.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();

        }
        return Uri.fromFile(file);
    }
}