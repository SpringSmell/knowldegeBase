/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowldegelibrary.function;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yidingliu.dev.knowldegelibrary.R;
import com.yidingliu.dev.knowldegelibrary.widgets.LoadingPopupWindow;

/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/9/30
 * @modifyInfo1 chriszou-16/9/30
 * @modifyContent
 */
public class AlertWindows implements View.OnClickListener {

    private static class AlertWindowsHolder {

        private static final AlertWindows INSTANCE = new AlertWindows ();
    }

    private OnClickListener mOnClickListener;

    private AlertDialog mAlertDialog;
    private LoadingPopupWindow mLoadingPopupWindow;
    private DialogViewHolder holder;

    /**
     * popupWindow是否可操作
     */
    private boolean isHandle = true;


    private AlertWindows () {

    }

    public static AlertWindows getInstance () {

        return AlertWindowsHolder.INSTANCE;
    }

    public void alertIsOkDialog ( Context context, CharSequence msgContent ) {

        this.alertIsOkDialog ( context, msgContent, null );
    }

    public void alertIsOkDialog ( Context context, CharSequence msgContent, OnClickListener onClickListener ) {

        this.alertIsOkDialog ( context, "消息提示", msgContent, onClickListener );
    }

    public void alertIsOkDialog ( Context context,CharSequence msgTitle, CharSequence msgContent , OnClickListener onClickListener ) {

        this.alertIsOkDialog ( context,false,msgTitle, msgContent, onClickListener );
    }

    public void alertIsOkDialog ( Context context, boolean isHandle, CharSequence msgTitle, CharSequence msgContent, OnClickListener onClickListener ) {

        this.mOnClickListener = onClickListener;
        if ( mAlertDialog == null ) {
            initDialog ( context );
        }

        holder.msgTitleTv.setText ( msgTitle );
        holder.msgContentTv.setText ( msgContent );

        if ( mOnClickListener == null ) {
            holder.msgOkBtn.setVisibility ( View.GONE );
            holder.msgCancelBtn.setVisibility ( View.GONE );
        } else {
            holder.msgOkBtn.setVisibility ( View.VISIBLE );
            holder.msgCancelBtn.setVisibility ( View.VISIBLE );
        }
        mAlertDialog.show ();
        mAlertDialog.setCanceledOnTouchOutside ( isHandle );
    }

    @TargetApi ( Build.VERSION_CODES.LOLLIPOP )
    private void initDialog ( Context context ) {

        AlertDialog.Builder builder = new AlertDialog.Builder ( context );
        View view= LayoutInflater.from ( context ).inflate ( R.layout.dialog_is_ok,null );
        builder.setView ( view );
        mAlertDialog = builder.create ();
        holder = new DialogViewHolder ( view );
        holder.msgOkBtn.setTag ( 0x1 );
        holder.msgOkBtn.setOnClickListener ( this );
        holder.msgCancelBtn.setTag ( 0x2 );
        holder.msgCancelBtn.setOnClickListener ( this );
    }

    private void initPopupWindow ( Activity activity ) {

        mLoadingPopupWindow = LoadingPopupWindow.LoadingPWBuilder.getInstance ( activity )
                                                                 .getPopupWindow ();
    }

    @Override public void onClick ( View v ) {

        if ( mOnClickListener == null ) {
            return;
        }
        int tag = ( int ) v.getTag ();
        switch ( tag ) {
            case 0x1://ok
                mOnClickListener.onClicked ( v, true );
                break;
            case 0x2://false
                mOnClickListener.onClicked ( v, false );
                break;
        }
        mAlertDialog.dismiss ();
    }

    public class DialogViewHolder {

        public TextView msgTitleTv;
        public TextView msgContentTv;
        public Button msgOkBtn;
        public Button msgCancelBtn;

        public DialogViewHolder ( View view ) {

            msgTitleTv = ( TextView ) view.findViewById ( R.id.msgTitleTv );
            msgContentTv = ( TextView ) view.findViewById ( R.id.msgContentTv );
            msgCancelBtn = ( Button ) view.findViewById ( R.id.msgCancelBtn );
            msgOkBtn = ( Button ) view.findViewById ( R.id.msgOkBtn );
        }
    }

    public interface OnClickListener {

        void onClicked ( View view, boolean isOk );
    }

}
