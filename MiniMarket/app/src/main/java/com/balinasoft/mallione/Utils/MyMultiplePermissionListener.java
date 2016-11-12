package com.balinasoft.mallione.Utils;

import com.balinasoft.mallione.Ui.Dialogs.AssessDialog;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

/**
 * Created by hzkto on 10/31/2016.
 */

public class MyMultiplePermissionListener implements MultiplePermissionsListener {
    private final AssessDialog activity;

    public MyMultiplePermissionListener(AssessDialog activity) {
        this.activity = activity;
    }

    @Override
    public void onPermissionsChecked(MultiplePermissionsReport report) {
//        for (PermissionGrantedResponse response : report.getGrantedPermissionResponses()) {
            activity.showPermissionGranted();
//        }
//
//        for (PermissionDeniedResponse response : report.getDeniedPermissionResponses()) {
//            activity.showPermissionDenied(response.getPermissionName(), response.isPermanentlyDenied());
//        }
    }

    @Override
    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
        activity.showPermissionRationale(token);
    }
}
