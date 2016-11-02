package com.yidingliu.dev.knowldegelibrary.tools;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

/**
 * Created by Administrator on 2016/6/6.
 */
public class ImageUtils {

    /**
     * 获取指定大小的位图
     *
     * @param res
     * @param resId
     * @param reqWidth  希望取得的宽度
     * @param reqHeight 希望取得的高度
     * @return 按指定大小压缩之后的图片
     * @source http://www.android-doc.com/training/displaying-bitmaps/load-bitmap
     * .html#read-bitmap
     */
    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId, int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth,
                reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * 计算与指定位图的大小比例
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return 缩放的比例
     * @source http://www.android-doc.com/training/displaying-bitmaps/load-bitmap
     * .html #read-bitmap
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            // 取高宽中更大一边，进行同比例缩放，这样才不会变形。
            if (width > height) {
                inSampleSize = Math.round((float) height / (float) reqHeight);
            } else {
                inSampleSize = Math.round((float) width / (float) reqWidth);
            }
        }
        return inSampleSize;
    }

    /**
     * 显示图片
     * @param img
     * @param url
     */
    public static void displayImg ( ImageView img, String url ){
//        x.image().bind(img,url);
    }

//    /**
//     * 显示图片
//     * @param img
//     * @param url
//     * @param options
//     */
//    public static void displayImg(ImageView img, String url, ImageOptions options){
//        x.image().bind(img,url,options);
//    }

    /**判断该文件是否是一个图片。*/
    public static boolean isImage(String fileName) {
        fileName=fileName.toLowerCase();
        return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png")||fileName.endsWith(".gif");
    }
}
