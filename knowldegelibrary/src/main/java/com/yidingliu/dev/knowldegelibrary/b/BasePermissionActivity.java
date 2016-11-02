/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowldegelibrary.b;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yidingliu.dev.knowldegelibrary.R;
import com.yidingliu.dev.knowldegelibrary.m.easypermissions.AppSettingsDialog;
import com.yidingliu.dev.knowldegelibrary.m.easypermissions.EasyPermissions;

import java.util.List;

/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/9/30
 * @modifyInfo1 chriszou-16/9/30
 * @modifyContent
 */
public abstract class BasePermissionActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {

    public static final String TAG = "BasePermissionActivity";
    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {

        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );
        EasyPermissions.onRequestPermissionsResult ( requestCode, permissions, grantResults );
    }

    @Override public void onPermissionsDenied ( int requestCode, List< String > perms ) {
        Log.d ( TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size () );

        // (Optional) Check whether the user denied any permissions and checked "NEVER ASK AGAIN."
        // This will display a dialog directing them to enable the permission in app settings.
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            new AppSettingsDialog.Builder( this, getString ( R.string.rationale_ask_again ))
                    .setTitle(getString(R.string.title_settings_dialog))
                    .setPositiveButton(getString(R.string.setting))
                    .setNegativeButton(getString(R.string.cancel), null /* click listener */)
                    .setRequestCode(EasyPermissions.RC_SETTINGS_SCREEN)
                    .build()
                    .show();
        }
    }

}
