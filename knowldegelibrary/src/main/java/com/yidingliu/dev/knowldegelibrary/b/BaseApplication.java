package com.yidingliu.dev.knowldegelibrary.b;

import com.yidingliu.dev.knowldegelibrary.b.application.ExitApplication;
import com.yidingliu.dev.knowldegelibrary.m.litepal.LitePalApplication;
import com.yidingliu.dev.knowldegelibrary.m.okhttp.OkHttpUtils;
import com.yidingliu.dev.knowldegelibrary.tools.DevicesUtils;
import com.zhy.autolayout.config.AutoLayoutConifg;

import java.util.concurrent.TimeUnit;

/**
 * 请在这里写上用途
 *
 * @author chris
 * @Date 16/9/23
 * @modifyInfo1 chris-16/9/23
 * @modifyContent
 */

public class BaseApplication extends ExitApplication {

    public static int width;
    public static int height;

    /*最大内存*/
    public static int maxMemory;

    @Override
    public void onCreate () {

        super.onCreate ();
        init ();
    }

    public void init () {
        /*数据库初始化*/
        LitePalApplication.initialize ( this );
        AutoLayoutConifg.getInstance ().useDeviceSize ();
        OkHttpUtils.getInstance ( this ).debug ( "dzpk2016" ).setConnectTimeout ( 10000, TimeUnit.MILLISECONDS );//初始化okHttp

        width = DevicesUtils.getScreenWidth ( this );
        height = DevicesUtils.getScreenHeight ( this );
        maxMemory = DevicesUtils.getMaxMemory ();
    }
}
