package com.yidingliu.dev.knowldegelibrary.tools;

import android.app.Activity;
import android.view.WindowManager;

/**
 * 状态栏工具
 * The class is status tools
 * Created by Chris on 2016/6/16.
 */
public class StatusUtils {

    private static StatusUtils instance;
    public static StatusUtils getInstance(){
        if(instance==null){
            instance=new StatusUtils();
        }
        return instance;
    }

    public void hideStatus(Activity activity){
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }



}
