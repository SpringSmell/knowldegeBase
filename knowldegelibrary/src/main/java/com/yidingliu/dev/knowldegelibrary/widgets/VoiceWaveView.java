package com.yidingliu.dev.knowldegelibrary.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.yidingliu.dev.knowldegelibrary.R;
import com.yidingliu.dev.knowldegelibrary.m.ImgManager;
import com.yidingliu.dev.knowldegelibrary.tools.DevicesUtils;
import com.yidingliu.dev.knowldegelibrary.tools.FormatUtils;

/**
 * 语音波浪起伏控件
 *
 * @author Chris zou
 * @Date 16/10/12
 * @modifyInfo1 chriszou-16/10/12
 * @modifyContent
 */
public class VoiceWaveView extends View {

    private Paint imgPaint;
    /**
     * 图片从中心向上偏移量
     */
    private int imgCenterGoUpOffset;
    private boolean isDrawImg;

    private Paint txtPaint;
    private Point txtLtPoint ;
    private String txtHint;
    private int txtColor;
    private int txtColorCancel;
    private int txtSize;
    private boolean isCancelTxt;

    private Paint barPaint;
    private int barWidth;
    private int barHeight;
    private int barColor;
    private int barSpace;
    private int barRadius;

    /**
     * 最大bar与其他Bar的差值
     */
    private int barMaxAndOtherDifference;

    /**
     * 小条相对于图片Y轴的距离
     */
    private int barRelativityImgY;

    private int progress = 0;
    private int maxBarCount = 4;

    private int imgMainResId;
    private int imgSecondResId;
    private int imgThridResId;
    private Bitmap mMainBitmap;
    private Bitmap mSecondBitmap;
    private Bitmap mThridBitmap;


    public VoiceWaveView ( Context context ) {

        this ( context, null );
    }

    public VoiceWaveView ( Context context, AttributeSet attrs ) {

        super ( context, attrs );
        init ();
    }

    private void init () {

        txtHint = "手指上滑,取消发送";
        txtColor = Color.WHITE;
        txtColorCancel = Color.RED;
        txtSize = FormatUtils.dip2px ( getContext (), 14 );
        txtPaint = new Paint ();
        txtPaint.setColor ( txtColor );
        txtPaint.setTextSize ( txtSize );
        txtPaint.setAntiAlias ( true );

        barColor = Color.WHITE;
        barWidth = FormatUtils.dip2px ( getContext (), 3 );
        barHeight = FormatUtils.dip2px ( getContext (), 15 );

        barRadius = FormatUtils.dip2px ( getContext (), 4 );
        if( DevicesUtils.getDeviceSDK ()>=20){
            barRelativityImgY = FormatUtils.dip2px ( getContext (), 7 );
            barSpace = FormatUtils.dip2px ( getContext (), 2 );
            barMaxAndOtherDifference = FormatUtils.dip2px ( getContext (), 3 );
        }else{
            barRelativityImgY = FormatUtils.dip2px ( getContext (), 10 )+1;
            barSpace = FormatUtils.dip2px ( getContext (), 3 );
            barMaxAndOtherDifference = FormatUtils.dip2px ( getContext (), 4 );
        }


        barPaint = new Paint ();
        barPaint.setAntiAlias ( true );
        barPaint.setColor ( barColor );

        imgMainResId = R.mipmap.ic_voice_main;
        imgSecondResId=R.mipmap.ic_voice_second;
        imgThridResId=R.mipmap.ic_voice_thrid;
        imgCenterGoUpOffset = FormatUtils.dip2px ( getContext (), 10 );
        imgPaint = new Paint ();
        imgPaint.setAntiAlias ( true );


        ImgManager.loadImage ( getContext (), imgMainResId, new SimpleTarget< Bitmap > () {

            @Override
            public void onResourceReady ( Bitmap resource, GlideAnimation< ? super Bitmap > glideAnimation ) {

                mMainBitmap = resource;
                ImgManager.loadImage ( getContext (), imgSecondResId, new SimpleTarget< Bitmap > () {

                    @Override
                    public void onResourceReady ( Bitmap resource, GlideAnimation< ? super Bitmap > glideAnimation ) {

                        mSecondBitmap = resource;
                        ImgManager.loadImage ( getContext (), imgThridResId, new SimpleTarget< Bitmap > () {

                            @Override
                            public void onResourceReady ( Bitmap resource, GlideAnimation< ? super Bitmap > glideAnimation ) {

                                mThridBitmap = resource;
                                postInvalidate ();
                            }
                        } );
                    }
                } );
            }
        } );
    }


    @Override protected void onDraw ( final Canvas canvas ) {

        super.onDraw ( canvas );
        if ( mMainBitmap == null ||mSecondBitmap==null||mThridBitmap==null) {
            return;
        }
        //draw img

        Point imgLtPoint = new Point ();//left and top at position
        imgLtPoint.x = getWidth () / 2 - mMainBitmap.getWidth () / 2;
        imgLtPoint.y = getHeight () / 2 - mMainBitmap.getHeight () / 2 - imgCenterGoUpOffset;
//        if ( !isDrawImg ) {
        drawImg ( canvas, mMainBitmap, imgLtPoint, imgPaint );
//        }

        //draw text
        txtPaint.setColor ( txtColor );
        if ( isCancelTxt ) {
            txtPaint.setColor ( txtColorCancel );
        }
        txtLtPoint = new Point ();
        int   txtWidth   = ( int ) txtPaint.measureText ( txtHint );
        txtLtPoint.x = getWidth () / 2 - txtWidth / 2;
        if(DevicesUtils.getDeviceSDK ()>=20){
            txtLtPoint.y = ( int ) ( getHeight () * 0.8 );
        }else{
            txtLtPoint.y = ( int ) ( getHeight () * 0.9 );
        }

        drawText ( canvas, txtHint, txtLtPoint, txtPaint );

        //draw bar
        Point lLtPoint = new Point ();
        Point lRbPoint = new Point ();

        Point rLtPoint = new Point ();
        Point rRbPoint = new Point ();
        for ( int i = 0 ; i < maxBarCount ; i++ ) {
            switch ( i ) {
                case 0://max size
                    //left
                    lLtPoint.x = imgLtPoint.x - barSpace * ( i + 1 ) - ( mSecondBitmap.getWidth () * ( i + 1 ) );
                    lLtPoint.y = imgLtPoint.y + barRelativityImgY;

                    lRbPoint.x = lLtPoint.x + barWidth;
                    lRbPoint.y = lLtPoint.y + barHeight - barMaxAndOtherDifference;

                    //right
                    rLtPoint.x = imgLtPoint.x + mMainBitmap.getWidth () + barSpace * ( i + 1 ) ;
                    rLtPoint.y = lLtPoint.y;

                    rRbPoint.x = rLtPoint.x + barWidth;
                    rRbPoint.y = lRbPoint.y;
                    drawImg (canvas,mSecondBitmap,lLtPoint,imgPaint);
                    drawImg (canvas,mSecondBitmap,rLtPoint,imgPaint);
                    break;

                default:
                    //left
                    lLtPoint.x = imgLtPoint.x - barSpace * ( i + 1 ) - ( mThridBitmap.getWidth () * ( i + 1 ) );
                    lLtPoint.y = imgLtPoint.y + barRelativityImgY + barMaxAndOtherDifference / 2;

                    lRbPoint.x = lLtPoint.x + mThridBitmap.getWidth ();
                    lRbPoint.y = lLtPoint.y + barHeight - barMaxAndOtherDifference * 2;

                    //right
                    rLtPoint.x = imgLtPoint.x + mMainBitmap.getWidth () + barSpace * ( i + 1 ) +
                            ( mThridBitmap.getWidth () * ( i ) );
                    rLtPoint.y = lLtPoint.y;

                    rRbPoint.x = rLtPoint.x + mThridBitmap.getWidth ();
                    rRbPoint.y = lRbPoint.y;
                    drawImg (canvas,mThridBitmap,lLtPoint,imgPaint);
                    drawImg (canvas,mThridBitmap,rLtPoint,imgPaint);
                    break;
            }
//            drawBar ( canvas, lLtPoint, lRbPoint, barRadius, barRadius, barPaint );//draw left bar
//            drawBar ( canvas, rLtPoint, rRbPoint, barRadius, barRadius, barPaint );//draw right bar

        }
    }

    private void drawImg ( Canvas canvas, Bitmap bitmap, Point point, Paint paint ) {

        canvas.drawBitmap ( bitmap, point.x, point.y, paint );
        isDrawImg = true;
    }

    private void drawBar ( Canvas canvas, Point ltPoint, Point rbPoint, int rx, int ry, Paint paint ) {

        RectF rectF = new RectF ( ltPoint.x, ltPoint.y, rbPoint.x, rbPoint.y );
        canvas.drawRoundRect ( rectF, rx, ry, paint );
    }

    private void drawText ( Canvas canvas, String text, Point point, Paint paint ) {

        canvas.drawText ( text, point.x, point.y, paint );
    }

    /**
     * set progress,the max progress to 100
     *
     * @param progress
     */
    public void setProgress ( int progress ) {

        if ( progress > 100 ) {
            progress = 100;
        } else if ( progress < 0 ) {
            progress = 0;
        }
        maxBarCount = progress / 10 / 2;
        postInvalidate ();
    }

    public void setTextHint ( String txtHint ) {

        setTextHint ( txtHint,txtColor );
    }
    public void setTextHint ( String txtHint ,int color) {

        this.isCancelTxt = false;
        this.txtHint = txtHint;
        this.txtColor=color;
        postInvalidate ();
    }

    public void setCancelTxt ( String cancelTxt ) {

        setCancelTxt(cancelTxt,txtColorCancel);
    }

    public void setCancelTxt ( String cancelTxt,int color ) {

        this.isCancelTxt = true;
        this.txtHint = cancelTxt;
        this.txtColorCancel=color;
        postInvalidate ();
    }

    public void drawImg () {

        isDrawImg = false;
        postInvalidate ();
    }
}
