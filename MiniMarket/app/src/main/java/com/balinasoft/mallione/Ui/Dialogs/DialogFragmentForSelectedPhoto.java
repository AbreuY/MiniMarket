package com.balinasoft.mallione.Ui.Dialogs;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.balinasoft.mallione.R;
import com.balinasoft.mallione.Utils.FileUtil;
import com.balinasoft.mallione.interfaces.UploadPhotoListener;

public class DialogFragmentForSelectedPhoto extends BaseDialog {

    private static final int CAMERA_PICTURE_REQUEST = 10001;
    private static final int RESULT_GALLERY = 10011;
    private static final int PIC_CROP = 10111;
    private Button btnChooseGallery, btnTakePhoto;

    private UploadPhotoListener photoListener = new UploadPhotoListener() {
        @Override
        public void uploadPhoto(Bitmap bitmap) {

        }

    };

    public DialogFragmentForSelectedPhoto setPhotoListener(UploadPhotoListener photoListener) {
        this.photoListener = photoListener;
        return this;
    }

    interface OnFailureListener {
        void onFailure();
    }

    public DialogFragmentForSelectedPhoto setOnFailureListener(OnFailureListener onFailureListener) {
        this.onFailureListener = onFailureListener;
        return this;
    }

    OnFailureListener onFailureListener = new OnFailureListener() {
        @Override
        public void onFailure() {

        }
    };

    public interface OnUriListener {
        void onUri(Uri uri);
    }

    public OnUriListener getOnUriListener() {
        return onUriListener;
    }

    public DialogFragmentForSelectedPhoto setOnUriListener(OnUriListener onUriListener) {
        this.onUriListener = onUriListener;
        return this;
    }

    OnUriListener onUriListener = new OnUriListener() {
        @Override
        public void onUri(Uri uri) {

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.selected_photo_layout, null);
        btnChooseGallery = (Button) v.findViewById(R.id.btnChooseGallery);
        btnTakePhoto = (Button) v.findViewById(R.id.btnTakePhoto);
        btnChooseGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, RESULT_GALLERY);
            }
        });
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, CAMERA_PICTURE_REQUEST);
            }
        });
        return v;
    }

    private void profCrop(Uri pic_uri) {
        try {
            // Намерение для кадрирования. Не все устройства поддерживают его
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(pic_uri, "image/*");
            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);
        } catch (ActivityNotFoundException e) {

        }
    }

//    private void profCrop2(Uri pic_uri) {
//        new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
//        CroperinoFileUtil.verifyStoragePermissions(getActivity());
//        CroperinoFileUtil.setupDirectory(getActivity());
//
//        //Prepare Chooser (Gallery or Camera)
//        Croperino.prepareChooser(getActivity(), "Capture photo...", ContextCompat.getColor(getActivity(), android.R.color.background_dark));
//
//        //Prepare Camera
//        try {
//            Croperino.prepareCamera(getActivity());
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//        //Prepare Gallery
//        Croperino.prepareGallery(getActivity());
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_PICTURE_REQUEST) {
            if (data == null) {
                onFailureListener.onFailure();
                return;
            }
            Bitmap bitmap = null;
            try {
                bitmap = (Bitmap) data.getExtras().get("data");
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (bitmap != null) {
                photoListener.uploadPhoto(bitmap);
                onUriListener.onUri(FileUtil.getUri(bitmap));
            }

        } else if (requestCode == RESULT_GALLERY) {
            if (data == null || data.getData() == null) {
                onFailureListener.onFailure();

                return;
            }
            Uri imageUri = data.getData();
            profCrop(imageUri);

        } else if (requestCode == PIC_CROP) {
            if (data == null || data.getExtras() == null) {
                onFailureListener.onFailure();
                return;
            }
            Bitmap bitmap = data.getExtras().getParcelable("data");
            onUriListener.onUri(FileUtil.getUri(bitmap));
            dismiss();
            photoListener.uploadPhoto(bitmap);

        }


    }



    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance())
            getDialog().setDismissMessage(null);
        super.onDestroyView();
    }
}