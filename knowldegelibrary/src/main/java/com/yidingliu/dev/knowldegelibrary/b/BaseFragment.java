package com.yidingliu.dev.knowldegelibrary.b;

import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yidingliu.dev.knowldegelibrary.b.fragments.ThemeFragment;


/**
 * Created by chris Zou on 2016/6/12.
 *
 * @author chris Zou
 * @date 2016/6/12
 */
public abstract class BaseFragment extends ThemeFragment {//<VH extends BaseParentViewHolder> 暂未有更好的方式，暂不支持ViewHolder的继承

    @Nullable
    @Override
    public View onCreateView ( LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState ) {

        View rootView = super.onCreateView ( inflater, container, savedInstanceState );
        setContentView ( onResultLayoutResId(),inflater,container );
        onInit ();
        onInitView ( mViewHolder );
        onBindData ( mViewHolder );
        onInitLayout ();
        return rootView;
    }
    /**
     * 返回资源文件ID
     *
     * @return
     */
    public abstract @LayoutRes int onResultLayoutResId ();

    public void onInit () {

    }

    public abstract void onInitView ( BaseParentViewHolder holder );

    public abstract void onBindData ( BaseParentViewHolder holder );

    public void onResumeBindData(BaseParentViewHolder holder){}

    @Override public void onResume () {

        super.onResume ();
        onResumeBindData( mViewHolder );
    }

    @CallSuper
    public void onInitLayout () {

        showTitleBar ( false );/*默认隐藏标题栏*/
        setBackValid ();
    }
}
