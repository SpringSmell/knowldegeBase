/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowldegelibrary.widgets.sortlistview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.yidingliu.dev.knowldegelibrary.R;
import com.yidingliu.dev.knowldegelibrary.tools.FormatUtils;


public class SideBarView extends View {


    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;
    /**
     * 右边滑动字母
     */
    private String[] b = {"A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" , "I" ,
                          "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
                          "U" , "V" ,
                          "W" , "X" , "Y" , "Z" , "#"};
    private int choose = -1;
    private Paint paint = new Paint ();
    private int sideTextSize = 30;
    private int sideTextColorNormal;
    private int sideTextColorFocus;
    private int sideTextBackGroundRes;

    private TextView mTextDialog;

    public void setTextView ( TextView mTextDialog ) {

        this.mTextDialog = mTextDialog;
    }


    public SideBarView ( Context context ) {

        super ( context );
        init ();
    }

    public SideBarView ( Context context, AttributeSet attrs ) {

        super ( context, attrs );
        init ();
    }

    public SideBarView ( Context context, AttributeSet attrs, int defStyle ) {

        super ( context, attrs, defStyle );

        init ();
    }

    private void init () {

        sideTextSize = FormatUtils.dip2px ( getContext (), 15 );
        sideTextColorNormal = Color.BLACK;
        sideTextColorFocus = Color.WHITE;
        sideTextBackGroundRes= R.drawable.shape_sidebar_background;

    }

    protected void onDraw ( Canvas canvas ) {

        super.onDraw ( canvas );

        int height       = getHeight ();
        int width        = getWidth ();
        int singleHeight = height / b.length;

        for ( int i = 0 ; i < b.length ; i++ ) {
            paint.setColor ( sideTextColorNormal );

            paint.setTypeface ( Typeface.DEFAULT_BOLD );
            paint.setAntiAlias ( true );
            paint.setTextSize ( sideTextSize );

            if ( i == choose ) {
                paint.setColor ( sideTextColorFocus );
                paint.setFakeBoldText ( true );
            }

            float xPos = width / 2 - paint.measureText ( b[i] ) / 2;
            float yPos = singleHeight * i + singleHeight;
            canvas.drawText ( b[i], xPos, yPos, paint );
            paint.reset ();//
        }

    }

    @Override
    public boolean dispatchTouchEvent ( MotionEvent event ) {

        final int                             action    = event.getAction ();
        final float                           y         = event.getY ();
        final int                             oldChoose = choose;
        final OnTouchingLetterChangedListener listener  = onTouchingLetterChangedListener;
        final int c = ( int ) ( y / getHeight () *
                b.length );

        switch ( action ) {
            case MotionEvent.ACTION_UP:
                setBackgroundDrawable ( new ColorDrawable ( 0x00000000 ) );
                choose = -1;//
                invalidate ();
                if ( mTextDialog != null ) {
                    mTextDialog.setVisibility ( View.INVISIBLE );
                }
                break;

            default:
                setBackgroundResource ( sideTextBackGroundRes );
                if ( oldChoose != c ) {
                    if ( c >= 0 && c < b.length ) {
                        if ( listener != null ) {
                            listener.onTouchingLetterChanged ( b[c] );
                        }
                        if ( mTextDialog != null ) {
                            mTextDialog.setText ( b[c] );
                            mTextDialog.setVisibility ( View.VISIBLE );
                        }

                        choose = c;
                        invalidate ();
                    }
                }

                break;
        }
        return true;
    }

    /**
     * 设置右边滑动字母集
     * @param sideLetters 滑动字母集
     */
    public void setSideLetter(String[] sideLetters){
        this.b=sideLetters;
        postInvalidate ();
    }

    public void setSideTextSize(int size){
        this.sideTextSize=size;
        postInvalidate ();
    }

    public void setSideTextBg(int sideTextBackGroundRes){
        this.sideTextBackGroundRes=sideTextBackGroundRes;
        postInvalidate ();
    }

    public void setSideTextColor(int sideTextColorNormal,int sideTextColorFocus){
        this.sideTextColorNormal=sideTextColorNormal;
        this.sideTextColorFocus=sideTextColorFocus;
        postInvalidate ();
    }

    public void setOnTouchingLetterChangedListener (
            OnTouchingLetterChangedListener onTouchingLetterChangedListener ) {

        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    public interface OnTouchingLetterChangedListener {

        void onTouchingLetterChanged ( String s );
    }

}