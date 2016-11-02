/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowledgebase.fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import com.yidingliu.dev.knowldegelibrary.b.BaseFragment;
import com.yidingliu.dev.knowldegelibrary.b.BaseParentViewHolder;
import com.yidingliu.dev.knowldegelibrary.m.Log;
import com.yidingliu.dev.knowldegelibrary.widgets.SlideDirectionView;
import com.yidingliu.dev.knowldegelibrary.widgets.VoiceWaveView;
import com.yidingliu.dev.knowledgebase.R;
import com.yidingliu.dev.knowledgebase.activities.SortListViewActivity;

import java.util.Random;

/**
 * 请在这里写上用途
 *
 * @author Chris zou
 * @Date 16/9/26
 * @modifyInfo1 Chris zou 16/9/26
 * @modifyContent
 */

public class TestFragment extends BaseFragment {

    private String TAG = "TestFragment";
    private VoiceWaveView mVoiceWaveView;
    private SlideDirectionView mSlideDirectionView;

    Handler mHandler = new Handler () {

        @Override public void handleMessage ( Message msg ) {

            mVoiceWaveView.setProgress ( new Random ().nextInt ( 100 ) + 1 );
            sendMsg ();
        }
    };

    @Override public int onResultLayoutResId () {

        return R.layout.fragment_test;
    }

    @Override public void onInitLayout () {

        super.onInitLayout ();
        setBackGroundContentColor ( Color.WHITE );
    }

    @Override public void onInitView ( BaseParentViewHolder viewHolder ) {

        mVoiceWaveView = viewHolder.getView ( R.id.voiceWaveView );
        mSlideDirectionView = viewHolder.getView ( R.id.sideDirection );
    }

    @Override public void onBindData ( BaseParentViewHolder viewHolder ) {

        viewHolder.setOnClickListener ( R.id.showNotiBtn, new View.OnClickListener () {

            @Override public void onClick ( View v ) {

                showNoti ();
            }
        } );

        viewHolder.setOnClickListener ( R.id.sortListView, new View.OnClickListener () {

            @Override public void onClick ( View v ) {

                SortListViewActivity
                        .startAction ( getActivity (), SortListViewActivity.class, "SortListView" );
            }
        } );
        mSlideDirectionView.setOnSideDirectionListener (
                new SlideDirectionView.OnSlideDirectionListener () {

                    @Override public void downSlide ( SlideDirectionView view ) {
                        view.setText ( "松开 完成" );
                        mVoiceWaveView.setTextHint ( "手指上滑,取消发送" );
                        mVoiceWaveView.setVisibility ( View.VISIBLE );
                    }

                    @Override
                    public void upSlide ( SlideDirectionView view, float dx, float dy, float mx, float my, float distance ) {
                        mVoiceWaveView.setTextHint ( "手指上滑,取消发送" );
                    }

                    @Override public void beyondSlide ( SlideDirectionView view, float distance ) {
                        view.setText ( "松开取消发送" );
                        mVoiceWaveView.setCancelTxt ( "松开手指,取消发送" ,Color.GREEN);
                    }

                    @Override public void confirm ( SlideDirectionView view ) {
                        showSnackbar ( "confirm" );
                        Log.e ( TAG,"teste" );
                        android.util.Log.e ( TAG,"test" );
                    }

                    @Override public void cancel ( SlideDirectionView view ) {
                        showSnackbar ( "cancel" );
                    }
                    @Override public void finishSlide ( SlideDirectionView view ) {
                        mVoiceWaveView.setVisibility ( View.GONE );
                        view.setText ( "按下 说话" );
                    }
                } );
        sendMsg ();
    }

    public void sendMsg () {

        new Handler ().postDelayed ( new Runnable () {

            @Override public void run () {

                mVoiceWaveView.setProgress ( new Random ().nextInt ( 100 ) + 1 );
                sendMsg ();
            }
        }, 200 );
    }

    private void showNoti () {

        NotificationManager manager = ( NotificationManager ) getActivity ()
                .getSystemService ( Context.NOTIFICATION_SERVICE );
        NotificationCompat.Builder builder = new NotificationCompat.Builder ( getActivity () );
        builder.setContentTitle ( "横幅通知" );
        builder.setContentText ( "请在设置通知管理中开启消息横幅提醒权限" );
        builder.setDefaults ( NotificationCompat.DEFAULT_ALL );
        builder.setSmallIcon ( R.mipmap.ic_launcher );
        builder.setLargeIcon (
                BitmapFactory.decodeResource ( getResources (), R.mipmap.ic_launcher ) );
        Intent intent = new Intent ( Intent.ACTION_VIEW, Uri
                .parse ( "https://www.baidu.com" ) );
        PendingIntent pIntent = PendingIntent.getActivity ( getActivity (), 1, intent, 0 );
        builder.setContentIntent ( pIntent );
        builder.setFullScreenIntent ( pIntent, true );
        builder.setAutoCancel ( true );
        builder.setPriority ( Notification.PRIORITY_MAX );

        manager.notify ( 6, builder.build () );

    }
}
