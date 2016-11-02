package com.yidingliu.dev.knowldegelibrary.m;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.Uri;

import com.yidingliu.dev.knowldegelibrary.helper.SharedPreferencesHelper;
import com.yidingliu.dev.knowldegelibrary.m.okhttp.OkHttpUtils;
import com.yidingliu.dev.knowldegelibrary.m.okhttp.callback.BitmapCallback;
import com.yidingliu.dev.knowldegelibrary.m.okhttp.callback.FileCallBack;
import com.yidingliu.dev.knowldegelibrary.m.okhttp.callback.StringCallback;
import com.yidingliu.dev.knowldegelibrary.tools.DevicesUtils;
import com.yidingliu.dev.knowldegelibrary.tools.Md5Utils;
import com.yidingliu.dev.knowldegelibrary.tools.SharedPreferencesUtil;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求的相关方法
 *
 * @author hzm
 * @Date 28/9/23
 * @modifyInfo1 28/9/23
 * @modifyContent
 */

public class HttpManager {

    /* 签名秘钥 */
    private static final String SIGN_P = "cq196.cn";

    /**
     * 对参数进行md5加密
     *
     * @param pars 网络请求的参数
     */
    private static void addSignPar ( Map< String, String > pars ) {

        if ( pars != null ) {

            String parString = pars.toString ().substring ( 1, pars.toString ().length () );
            parString = parString.substring ( 0, parString.length () - 1 );
            String[] array = parString.split ( ", " );
            Arrays.sort ( array );
            StringBuilder sb = new StringBuilder ();
            for ( String str : array ) {
                sb.append ( str ).append ( "&" );
            }
            sb.delete ( sb.length () - 1, sb.length () );
            sb.append ( SIGN_P );
            String signStr = Md5Utils.getMD5String ( sb.toString () );
            pars.put ( "s", signStr );

        }
    }


    /**
     * Get请求
     *
     * @param url      请求的url
     * @param tag      请求的tag，可以用来取消网络请求
     * @param callback 网络请求的回调接口
     */
    public static void Get ( String url, String tag, StringCallback callback ) {

        OkHttpUtils.get ().url ( url ).tag ( tag ).build ().execute ( callback );
    }


    public static void Post ( final Context context, final String url, StringCallback callback ) {

        Post ( context, url, "noTag", null, callback );
    }

    public static void Post ( final Context context, final String url, Map< String, String > params, StringCallback callback ) {

        Post ( context, url, "noTag", params, callback );
    }

    /**
     * Post请求
     *
     * @param url      请求的url
     * @param tag      请求的tag，可以用来取消网络请求
     * @param params   请求的参数
     * @param callback 网络请求的回调接口
     */
    public static void Post ( final Context context, final String url, final String tag, Map< String, String > params, StringCallback callback ) {

        if ( params == null ) {
            params = new HashMap<> ();
        }
        params.put ( "platform", "ANDROID" );
        params.put ( "t", System.currentTimeMillis () + "" );
        params.put ( "d", DevicesUtils.getSHA1 ( context ) );
        if ( !params.containsKey ( "a" ) ) {
            params.put ( "a", SharedPreferencesHelper.Builder.getInstance ( context, "dzpk2016" )
                                                             .getHelper ().getValue ( "a" ) + "" );
        }

        addSignPar ( params );
        OkHttpUtils.post ().url ( url ).tag ( tag ).params ( params ).build ().execute ( callback );
    }

    /**
     * 根据tag取消网络请求
     *
     * @param mContext
     * @param tag      发起请求的tag
     */
    public static void Cancel ( final Context mContext, final String tag ) {

        OkHttpUtils.getInstance ( mContext ).cancelTag ( tag );
    }

    public static void UploadImage ( final String url, final File file, StringCallback callback ) {

        UploadImage ( url, file, "noTag", callback );
    }

    public static void UploadImage ( final String url, final File file, final String tag, StringCallback callback ) {

        UploadImage ( url, tag, file, file.getName (), callback );
    }

    /**
     * 上传图片
     *
     * @param url      请求的url
     * @param tag      请求的tag，可以用来取消网络请求
     * @param file     图片文件，一般可以根据图片名称获取得到如 new File(Environment.getExternalStorageDirectory() + "/" + "HaiPa/", name);
     * @param name     图片的名称 如"20160802060402.jpg"
     * @param callback 网络请求的回调接口
     */
    public static void UploadImage ( final String url, final String tag, final File file, final String name, StringCallback callback ) {

        Map< String, String > params = new HashMap< String, String > ();
        params.put ( "f1", String.valueOf ( Uri.fromFile ( file ) ) );
        params.put ( "pn", "pk" );//此处的hp根据服务器要求可能会改变

        OkHttpUtils.post ().addFile ( "mFile", name, file ).url ( url ).tag ( tag )
                   .params ( params ).build ().execute ( callback );
    }


    /**
     * @param url      下载的地址
     * @param callback 下载文件的回调内部包含有进度信息
     */
    public static void DownLoadFile ( String url, String tag, FileCallBack callback ) {

        OkHttpUtils.get ().tag ( tag ).url ( url ).build ().execute ( callback );
    }

    /**
     * @param url      文件上传地址
     * @param tag      请求的tag，可以用来取消网络请求
     * @param file     待上传的文件
     * @param fileName 上传的文件名
     * @param params   上传的参数
     * @param callback
     */
    public static void UpLoadFile ( String url, String tag, File file, String fileName, Map< String, String > params, StringCallback callback ) {

        OkHttpUtils.post ().addFile ( "mFile", fileName, file ).url ( url ).tag ( tag )
                   .params ( params ).build ().execute ( callback );
    }


    /**
     * @param url      图片的地址
     * @param tag      请求的tag，可以用来取消网络请求
     * @param callback 图片下载的回调
     */
    public void DownImage ( String url, String tag, BitmapCallback callback ) {

        OkHttpUtils.get ().url ( url ).tag ( tag ).build ().execute ( callback );
    }

}

