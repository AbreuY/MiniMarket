package com.balinasoft.mallione.Utils;

import com.balinasoft.mallione.Ui.Dialogs.AssessDialog;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

/**
 * Created by hzkto on 10/31/2016.
 */

public class MyPermissionListener implements PermissionListener {
    private final AssessDialog activity;

    public MyPermissionListener(AssessDialog activity) {
        this.activity = activity;
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse response) {
        activity.showPermissionGranted(response.getPermissionName());
    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse response) {
//        activity.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
        activity.showPermissionRationale(token);
    }
}
