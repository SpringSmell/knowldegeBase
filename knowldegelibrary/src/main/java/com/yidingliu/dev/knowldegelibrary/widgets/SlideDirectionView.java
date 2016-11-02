package com.yidingliu.dev.knowldegelibrary.widgets;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

import com.yidingliu.dev.knowldegelibrary.m.Log;
import com.yidingliu.dev.knowldegelibrary.tools.CommonUtils;

/**
 * 监听滑动方向
 *
 * @author Chris zou
 * @Date 16/10/13
 * @modifyInfo1 chriszou-16/10/13
 * @modifyContent
 */
public class SlideDirectionView extends TextView {


    private static final String TAG = SlideDirectionView.class.getSimpleName ();
    private static final int MIN_OFFSET_VALUE = 20;
    private OnSlideDirectionListener mSlideDirectionListener;

    private int colorFocusBg;
    private int colorNormalBg;

    private String initTxt;
    private String focusTxt;
    private String cancelTxt;

    private float dx;
    private float dy;

    private float distance;
    private float currentDistance;

    public SlideDirectionView ( Context context ) {

        this ( context, null );
    }

    public SlideDirectionView ( Context context, AttributeSet attrs ) {

        super ( context, attrs );
        init ();
    }

    private void init () {

        initTxt = "按下 说话";
        focusTxt = "松开 完成";
        cancelTxt = "向上滑动取消";
        colorFocusBg = Color.GRAY;
        colorNormalBg = Color.WHITE;

        distance = 500;
    }

    @Override
    public boolean onTouchEvent ( MotionEvent event ) {

        setBackgroundColor ( colorFocusBg );
        setText ( focusTxt );
        switch ( event.getAction () ) {
            case MotionEvent.ACTION_DOWN:
                dx = event.getX ();
                dy = event.getY ();
                mSlideDirectionListener.downSlide ( this );
                break;
            case MotionEvent.ACTION_UP:
                setText ( initTxt );
                setBackgroundColor ( colorNormalBg );
                if ( currentDistance > distance ) {
                    mSlideDirectionListener.cancel ( this );
                } else {
                    mSlideDirectionListener.confirm ( this );
                }
                mSlideDirectionListener.finishSlide ( this );
                currentDistance=0;
                break;
            default:
                float offsetX = event.getX () - dx;//X方向偏移量
                float offsetY = event.getY () - dy;//Y方向偏移量

                if ( !( Math.abs ( offsetX ) > Math.abs ( offsetY ) ) ) {//上滑或者下滑
                    Log.e ( TAG, "onTouchEvent: " + "dx : " + dx + "  dy : " + dy + " mx : " +
                            event.getX () + " my : " + event.getY () );
                    if ( dy - event.getY () > MIN_OFFSET_VALUE ) {
                        currentDistance = CommonUtils
                                .getDistance ( dx, dy, event.getX (),
                                               Math.abs ( event.getY () ) +
                                                       dy );
                        if ( currentDistance > this.distance ) {
                            mSlideDirectionListener.beyondSlide ( this, currentDistance );
                        } else {
                            mSlideDirectionListener
                                    .upSlide ( this, dx, dy, event.getX (),
                                               Math.abs ( event.getY () ) + dy, distance );
                        }
                    }
                }
        }

        return true;
    }

    public interface OnSlideDirectionListener {

        void downSlide ( SlideDirectionView view );

        void upSlide ( SlideDirectionView view, float dx, float dy, float mx, float my, float distance );

        void beyondSlide ( SlideDirectionView view, float distance );

        void confirm ( SlideDirectionView view );

        void cancel ( SlideDirectionView view );

        void finishSlide ( SlideDirectionView view );

    }

    public void setDistance ( float distance ) {

        this.distance = distance;
    }

    public void setOnSideDirectionListener ( OnSlideDirectionListener listener ) {

        mSlideDirectionListener = listener;
    }
}
