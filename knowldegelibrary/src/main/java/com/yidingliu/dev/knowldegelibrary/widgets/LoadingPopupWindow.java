package com.yidingliu.dev.knowldegelibrary.widgets;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.yidingliu.dev.knowldegelibrary.R;
import com.yidingliu.dev.knowldegelibrary.tools.CommonUtils;


/**
 * Created by Administrator on 2016/6/7.
 */
public class LoadingPopupWindow extends PopupWindow {

    private Context mContext;
    public int width,height;
    private View contentView;

    public LoadingPopupWindow(Context context){
        mContext=context;
        contentView=LayoutInflater.from(context).inflate ( R.layout.pw_loading, null );
        setContentView(contentView);
        setBackgroundDrawable(new ColorDrawable(0x000000));
        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(false);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.mandatoryDraw();
    }

    private void mandatoryDraw(){
        this.contentView.measure(View.MeasureSpec.UNSPECIFIED,View.MeasureSpec.UNSPECIFIED);
        //强制刷新后拿到宽高
        this.width=contentView.getMeasuredWidth();
        this.height=contentView.getMeasuredHeight();
    }


    public void showPopupWindow(View view){
        if(!isShowing()){
            showAsDropDown ( view, CommonUtils.getScreenWidth ( mContext )/2-width/2, CommonUtils.getScreenHeight ( mContext )/2-height/2 );
        }
    }

    public static class LoadingPWBuilder{

        private static LoadingPopupWindow mLoadingPopupWindow;
        public static String activityHashCode;
        public static LoadingPWBuilder instance;

        public static LoadingPWBuilder getInstance(Activity activity){
            if(instance==null){
                instance=new LoadingPWBuilder();
            }
            String hashCode=String.valueOf(activity.hashCode());
            if(!hashCode.equalsIgnoreCase(String.valueOf(activityHashCode))){
                mLoadingPopupWindow=new LoadingPopupWindow(activity);
            }
            return instance;
        }

        public LoadingPWBuilder setTouchable(boolean istTouchable){
            mLoadingPopupWindow.setTouchable(istTouchable);
            return this;
        }

        public LoadingPWBuilder setBackGroundDrawable(Drawable backGround){
            mLoadingPopupWindow.setBackgroundDrawable(backGround);
            return this;
        }

        public LoadingPWBuilder setAnimationStyle(int animationStyle){
            mLoadingPopupWindow.setAnimationStyle(animationStyle);
            return this;
        }

        public LoadingPopupWindow getPopupWindow(){
            mLoadingPopupWindow.update();
            return mLoadingPopupWindow;
        }
    }


}
