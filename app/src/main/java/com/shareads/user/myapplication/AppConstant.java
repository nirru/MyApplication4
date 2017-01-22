package com.shareads.user.myapplication;

import android.Manifest;

/**
 * Created by ericbasendra on 25/09/16.
 */

public class AppConstant {

    public static final String[] PERMISSIONS = { Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA};
    public static final int REQUEST_TAKE_PHOTO = 1;
    public static final int PICK_IMAGE_REQUEST = 2;
    public static final int PERMISSION_ALL = 113;

    }
