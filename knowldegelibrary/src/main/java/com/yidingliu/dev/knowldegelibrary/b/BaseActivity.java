package com.yidingliu.dev.knowldegelibrary.b;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.yidingliu.dev.knowldegelibrary.R;
import com.yidingliu.dev.knowldegelibrary.b.activities.ThemeActivity;

/**
 * 对外开放的类，请继承该类
 * 使用该类须隐藏title，主题城需使用兼容的风格，详情请查看Demo的mainifests
 * Created by chris on 2016/6/8.
 *
 * @author chris Zou
 * @date 2016/6/8.
 */
public abstract class BaseActivity extends ThemeActivity {

public static final String TITLE="title";
    @Override
    protected void onCreate ( @Nullable Bundle savedInstanceState ) {

        super.onCreate ( savedInstanceState );
        setContentView ( onResultLayoutResId () );
    }

    @Override
    public void setContentView ( @LayoutRes int layoutResID ) {

        super.setContentView ( layoutResID );
        onInit ();
        onInitView ( getViewHolder () );
        onBindData ( getViewHolder () );
        onInitLayout ();
    }

    /**
     * 初始化操作
     */
    public void onInit () {

    }

    /**
     * 返回资源文件ID
     *
     * @return
     */
    public abstract @LayoutRes int onResultLayoutResId ();

    /**
     * 初始化View的回调方法
     *
     * @return
     */
    public abstract void onInitView ( BaseParentViewHolder holder );

    /**
     * 绑定数据
     *
     * @param holder
     */
    public abstract void onBindData ( BaseParentViewHolder holder );

    public void onResumeBindData(BaseParentViewHolder holder){}

    @Override protected void onResume () {

        super.onResume ();
        onResumeBindData(getViewHolder ());
    }

    @CallSuper
    public void onInitLayout () {

        setBackGround ( R.color.colorPrimary );
        setBackGroundContent ( R.color.colorContent );
        setBackValid ();
        if ( getIntent ().hasExtra ( TITLE ) ) {
            setTitle ( getIntent ().getStringExtra ( TITLE ) );
        }
    }


    public static void startAction ( Activity activity, Class activityClass, String title ) {

        startAction ( activity, activityClass, title, 0 );
    }

    /**
     * 这只是一个简易的跳转方法，参数多时建议重写此方法
     *
     * @param activity
     * @param activityClass
     * @param title
     * @param requestCode
     */
    public static void startAction ( Activity activity, Class activityClass, String title, int requestCode ) {

        Intent intent = new Intent ( activity, activityClass );
        intent.putExtra ( TITLE, title );
        activity.startActivityForResult ( intent, requestCode );
    }
}
