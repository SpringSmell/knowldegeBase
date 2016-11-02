/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowledgebase.activities;

import android.Manifest;
import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.yidingliu.dev.knowldegelibrary.b.BaseParentViewHolder;
import com.yidingliu.dev.knowldegelibrary.b.BasePermissionActivity;
import com.yidingliu.dev.knowldegelibrary.m.easypermissions.AfterPermissionGranted;
import com.yidingliu.dev.knowldegelibrary.m.easypermissions.EasyPermissions;
import com.yidingliu.dev.knowledgebase.R;

import java.util.List;

import static com.yidingliu.dev.knowldegelibrary.m.easypermissions.EasyPermissions.RC_SETTINGS_SCREEN;

/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/9/30
 * @modifyInfo1 chriszou-16/9/30
 * @modifyContent
 */
public class PermissionActivity extends BasePermissionActivity {

    @Override public int onResultLayoutResId () {

        return R.layout.activity_permission;
    }

    @Override public void onInitView ( BaseParentViewHolder holder ) {

    }

    @Override public void onInitLayout () {

        super.onInitLayout ();
        setBackGroundContentColor ( Color.WHITE );
    }

    @Override public void onBindData ( BaseParentViewHolder holder ) {
        holder.setOnClickListener ( R.id.requestCamera, new View.OnClickListener () {

            @Override public void onClick ( View v ) {
                cameraTask();
            }
        } );
    }

    @Override public void onPermissionsGranted ( int requestCode, List< String > perms ) {

    }

    @AfterPermissionGranted ( RC_SETTINGS_SCREEN)
    public void cameraTask() {
        if ( EasyPermissions.hasPermissions ( this, Manifest.permission.CAMERA )) {
            // Have permission, do the thing!
            Toast.makeText ( this, "TODO: Camera things", Toast.LENGTH_LONG ).show ();
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(this, getString ( R.string.rationale_camera ),
                                               RC_SETTINGS_SCREEN, Manifest.permission.CAMERA);
        }
    }
}
