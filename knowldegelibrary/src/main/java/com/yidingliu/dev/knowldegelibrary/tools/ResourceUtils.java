package com.yidingliu.dev.knowldegelibrary.tools;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;


/**
 * Created by chris on 2016/5/9.
 */
public class ResourceUtils {



    /**
     * 设置本地
     * @param context
     * @param imageView
     * @param prefix 前缀
     * @param postfix 后缀
     */
    public static void setImgResource(Context context, ImageView imageView,String prefix, String postfix){
        String resName=prefix+postfix;
        try{
            int resId=context.getResources().getIdentifier(resName,"mipmap",context.getPackageName());
            imageView.setImageResource(resId);
        }catch (Exception ex){
            Log.e("setImgResource", "set image resource aborted");
        }
    }

}
