package com.yidingliu.dev.knowldegelibrary.m;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yidingliu.dev.knowldegelibrary.m.glide.GlideCircleTransform;
import com.yidingliu.dev.knowldegelibrary.m.glide.GlideRoundTransform;

import java.io.File;

/**
 * 请在这里写上用途
 *
 * @author chris
 * @Date 16/9/23
 * @modifyInfo1 chris-16/9/23
 * @modifyContent
 */

public class ImgManager {

    /** 加载网络图片 （无默认图和出错图）
     * @param context 可以是Activity 和 Fragment
     * @param url 图片的url
     * @param iv  显示图片的ImageView
     */
    public static synchronized void loadImage ( Context context, String url, ImageView iv ) {

        Glide.with(context).load(url).into ( iv );
    }
    /** 加载网络图片 （有默认图和出错图）
     * @param context 可以是Activity 和 Fragment
     * @param url 图片的url
     * @param iv  显示图片的ImageView
     * @param erroImg 加载出错显示的图片
     * @param emptyImg 加载前的默认图
     */
    public static synchronized void loadImage(Context context, String url, int erroImg, int emptyImg, ImageView iv) {

        Glide.with ( context ).load ( url ).placeholder ( emptyImg ).error ( erroImg ).into ( iv );
    }

    /** 加载SD卡图片
     * @param context 可以是Activity 和 Fragment
     * @param file 图片文件
     * @param imageView  显示图片的ImageView
     */
    public static synchronized void loadImage(Context context, final File file, final ImageView imageView) {

        Glide.with(context).load(file).into(imageView);
    }

    /** 加载drawable和mipmap图片
     * @param context 可以是Activity 和 Fragment
     * @param resourceId 图片的id 如 R.drawable.img1
     * @param imageView  显示图片的ImageView
     */
    public static synchronized void loadImage(Context context, final int resourceId, final ImageView imageView) {

        Glide.with(context).load(resourceId).into(imageView);
    }

    public static synchronized void loadImage(Context context, final int resourceId,SimpleTarget simpleTarget){
        Glide.with(context).load(resourceId).asBitmap ().into (simpleTarget);

    }

    /** 加载Assets中的图片
     * @param context 可以是Activity 和 Fragment
     * @param name 图片名称
     * @param imageView  显示图片的ImageView
     */
    public static synchronized void loadAssetImage(Context context, final String name, final ImageView imageView) {

        Glide.with(context).load("file:///android_asset/"+name).into(imageView);
    }

    /** 加载gif图片
     * @param context 可以是Activity 和 Fragment
     * @param url 图片url
     * @param iv  显示图片的ImageView
     */
    public static synchronized void loadGifImage(Context context, String url, ImageView iv) {

        Glide.with(context).load(url).asGif().diskCacheStrategy ( DiskCacheStrategy.SOURCE ).into ( iv );
    }

    /** 加载gif图片
     * @param context 可以是Activity 和 Fragment
     * @param url 图片url
     * @param erroImg 加载出错显示的图片
     * @param emptyImg 加载前的默认图
     * @param iv  显示图片的ImageView
     */
    public static synchronized void loadGifImage(Context context, String url, int erroImg, int emptyImg, ImageView iv) {

        Glide.with(context).load(url).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(emptyImg).error(erroImg).into(iv);
    }

    /** 加载图片为圆形图片
     * @param context 可以是Activity 和 Fragment
     * @param url 图片url
     * @param iv  显示图片的ImageView
     */
    public static synchronized void loadCircleImage(Context context, String url, ImageView iv) {

        Glide.with(context).load(url).transform (new GlideCircleTransform ( context) ).into ( iv );
    }

    /** 加载图片为圆形图片
     * @param context 可以是Activity 和 Fragment
     * @param url 图片url
     * @param erroImg 加载出错显示的图片
     * @param emptyImg 加载前的默认图
     * @param iv  显示图片的ImageView
     */
    public static synchronized void loadCircleImage(Context context, String url,int erroImg, int emptyImg, ImageView iv) {

        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).transform (new GlideCircleTransform ( context) ).into ( iv );
    }

    /** 加载图片为圆角图片
     * @param context 可以是Activity 和 Fragment
     * @param url 图片url
     * @param iv  显示图片的ImageView
     * @param radius 圆角的大小，单位dp
     */
    public static synchronized void loadRoundCornerImage(Context context, String url, ImageView iv ,int radius) {

        Glide.with(context).load(url).transform (new GlideRoundTransform ( context, radius) ).into ( iv );
    }

    /** 加载图片为圆角图片
     * @param context 可以是Activity 和 Fragment
     * @param url 图片url
     * @param iv  显示图片的ImageView
     * @param erroImg 加载出错显示的图片
     * @param emptyImg 加载前的默认图
     * @param radius 圆角的大小，单位dp
     */
    public static synchronized void loadRoundCornerImage(Context context, String url,int erroImg, int emptyImg, ImageView iv,int radius) {

        Glide.with(context).load(url).placeholder(emptyImg).error(erroImg).transform (new GlideRoundTransform ( context, radius) ).into ( iv );
    }

    /**
     * 取消所有正在下载或等待下载的任务。
     * @param context 上下文
     */
    public static synchronized void cancelAllTasks(Context context) {
        Glide.with(context).pauseRequests();
    }

    /**
     * 恢复所有任务
     */
    public static synchronized void resumeAllTasks(Context context) {
        Glide.with(context).resumeRequests();
    }



    /*
     * 清除内存缓存
     * @param context 上下文
     */
    public static void clearMemory(Context context) {
        Glide.get(context).clearMemory();
    }

    /*
     * 清除磁盘缓存
     * @param context 上下文
     */
    public static void clearDiskCache(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
    }

    /*
     * 清除所有缓存
     * @param context 上下文
     */
    public static synchronized void cleanAllCache(Context context) {
        clearDiskCache(context);
        clearMemory(context);
    }
}
