/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowldegelibrary.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * Created by chriszou on 16/9/28.
 */

public class DevicesUtils {

    /**
     * get window manager
     *
     * @param context
     * @return 返回窗口管理信息类，通过其可获得设备信息，example:屏幕高宽
     */
    public static Display getScreenDisplay ( Context context ) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display mDisplay = wm.getDefaultDisplay();
        return mDisplay;
    }

    /**
     * 获取屏幕宽度,单位：PX
     *
     * @param context
     * @return The screen width
     */
    public static int getScreenWidth(Context context) {
        int width = getScreenDisplay(context).getWidth();
        return width;
    }

    /**
     * 获取屏幕高度，单位：px
     *
     * @param context
     * @return The screen height
     */
    public static int getScreenHeight(Context context) {
        int height = getScreenDisplay(context).getHeight();
        return height;
    }

    /**
     * 获取屏幕密度（DPI）
     *
     * @return 屏幕密度
     */
    public static int getScreenDensityDPI() {
        DisplayMetrics dm      = new DisplayMetrics();
        int            density = dm.densityDpi;
        return density;
    }

    /**
     * 获取状态栏高度,单位：PX
     *
     * @param activity
     * @return 状态栏高度
     */
    public static int getStatusHeight (Activity activity ) {
        int  statusHeight = 0;
        Rect localRect    = new Rect();
        activity.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(localRect);
        statusHeight = localRect.top;
        if (0 == statusHeight) {
            Class<?> localClass;
            try {
                localClass = Class.forName("com.android.internal.R$dimen");
                Object localObject = localClass.newInstance();
                int i5 = Integer.parseInt(localClass
                                                  .getField("status_bar_height").get(localObject)
                                                  .toString());
                statusHeight = activity.getResources()
                                       .getDimensionPixelSize(i5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statusHeight;
    }

    /**
     * 获取设备最大内存,单位为字节(B)
     *
     * @return
     */
    public static int getMaxMemory() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        return maxMemory;
    }

    public static boolean installAPK (Context context,File file ) {
        if(file.exists()) {
            Intent intent = new Intent( "android.intent.action.VIEW");
            intent.addFlags(268435456);
            intent.setDataAndType ( Uri.fromFile ( file ), "application/vnd.android.package-archive" );
            context.startActivity(intent);
            Process.killProcess ( Process.myPid () );
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断SD卡是否可用
     */
    public static boolean isSDcardOK() {
        return Environment.getExternalStorageState ().equals ( Environment.MEDIA_MOUNTED );
    }
    /**
     * 获取SHA1码
     * @param context
     * @return
     */
    public static String getSHA1(Context context){
        try {
            PackageInfo info = context.getPackageManager ().getPackageInfo (
                    context.getPackageName(), PackageManager.GET_SIGNATURES );
            byte[]        cert      = info.signatures[0].toByteArray();
            MessageDigest md        = MessageDigest.getInstance ( "SHA1" );
            byte[]        publicKey = md.digest(cert);
            StringBuffer  hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                                             .toUpperCase ( Locale.US );
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取设备版本号
     * @return
     */
    public static String getDeviceLevel(){
        return Build.VERSION.RELEASE;
    }

    /**
     * 获取设备手机型号
     * @return
     */
    public static String getDeviceModel(){
        return Build.MODEL;
    }

    /**
     * 获取设备版本号
     * @return
     */
    public static int getDeviceSDK(){
        return Build.VERSION.SDK_INT;
    }
}
