/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowldegelibrary.b;

import android.support.annotation.NonNull;
import android.util.Log;

import com.yidingliu.dev.knowldegelibrary.m.easypermissions.EasyPermissions;

import java.util.List;

/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/10/8
 * @modifyInfo1 chriszou-16/10/8
 * @modifyContent
 */
public abstract class BasePermissionFragment extends BaseFragment implements EasyPermissions.PermissionCallbacks {

    public static final String TAG="BasePermissionFragment";

    @Override
    public void onRequestPermissionsResult ( int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult ( requestCode, permissions, grantResults, this );
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsDenied:" + requestCode + ":" + perms.size());
    }
}
