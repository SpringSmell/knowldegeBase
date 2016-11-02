/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowledgebase.fragments;

import android.Manifest;
import android.widget.Toast;

import com.yidingliu.dev.knowldegelibrary.b.BaseParentViewHolder;
import com.yidingliu.dev.knowldegelibrary.b.BasePermissionFragment;
import com.yidingliu.dev.knowldegelibrary.m.easypermissions.AfterPermissionGranted;
import com.yidingliu.dev.knowldegelibrary.m.easypermissions.EasyPermissions;

import java.util.List;

import static com.yidingliu.dev.knowldegelibrary.m.easypermissions.EasyPermissions.RC_SMS_PERM;

/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/10/8
 * @modifyInfo1 chriszou-16/10/8
 * @modifyContent
 */
public class PermissionFragment extends BasePermissionFragment {

    @Override public void onInitView ( BaseParentViewHolder holder ) {

    }

    @Override public void onBindData ( BaseParentViewHolder holder ) {

    }

    @Override public void onPermissionsGranted ( int requestCode, List< String > perms ) {

    }

    @AfterPermissionGranted ( EasyPermissions.RC_SMS_PERM)
    private void smsTask() {
        if (EasyPermissions.hasPermissions ( getContext(), Manifest.permission.READ_SMS )) {
            // Have permission, do the thing!
            Toast.makeText ( getActivity (), "TODO: SMS things", Toast.LENGTH_LONG ).show ();
        } else {
            // Request one permission
            EasyPermissions.requestPermissions(this, getString ( com.yidingliu.dev.knowldegelibrary.R.string.rationale_sms ),
                                               RC_SMS_PERM, Manifest.permission.READ_SMS);
        }
    }

    @Override public int onResultLayoutResId () {

        return 0;
    }
}
