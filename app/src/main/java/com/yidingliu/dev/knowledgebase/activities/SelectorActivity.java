/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowledgebase.activities;

import android.view.View;
import android.widget.Button;

import com.yidingliu.dev.knowldegelibrary.b.BaseActivity;
import com.yidingliu.dev.knowldegelibrary.b.BaseParentViewHolder;
import com.yidingliu.dev.knowldegelibrary.function.SelectorAreaDialog;
import com.yidingliu.dev.knowldegelibrary.function.SelectorTimeDialog;
import com.yidingliu.dev.knowledgebase.R;

/**
 * Created by chriszou on 16/9/27.
 */

public class SelectorActivity extends BaseActivity {

    private Button btnSelectorArea;
    private Button btnSelectorTime;

    private SelectorAreaDialog mSelectorAreaDialog;
    private SelectorTimeDialog mSelectorTimeDialog;

    @Override
    public int onResultLayoutResId () {

        return R.layout.activity_selector;
    }


    @Override public void onInit () {

        super.onInit ();

    }

    @Override
    public void onInitView ( final BaseParentViewHolder holder ) {


    }

    @Override public void onInitLayout () {

        super.onInitLayout ();
        setBackGround ( R.color.colorPrimary );
    }

    @Override
    public void onBindData ( final BaseParentViewHolder holder ) {
        btnSelectorArea=holder.getView ( R.id. btnSelectorArea);
        btnSelectorTime=holder.getView ( R.id.btnSelectorTime );
        holder.setOnClickListener ( R.id.btnSelectorArea, new View.OnClickListener () {

            @Override
            public void onClick ( View v ) {
                if(mSelectorAreaDialog==null){
                    mSelectorAreaDialog = new SelectorAreaDialog ( SelectorActivity.this,
                                                                   new SelectorAreaDialog.OnResultHandler () {

                                                                       @Override
                                                                       public void handle ( String[] location ) {
                                                                           btnSelectorArea.setText ( location[0]+"-"+ location[1]+"-"+ location[2] );
                                                                       }
                                                                   } );
                }
                mSelectorAreaDialog.show ();
            }
        } );


        holder.setOnClickListener ( R.id.btnSelectorTime, new View.OnClickListener () {

            @Override
            public void onClick ( View v ) {

                if(mSelectorTimeDialog==null){
                    mSelectorTimeDialog = new SelectorTimeDialog ( SelectorActivity.this,
                                                                   new SelectorTimeDialog.OnResultHandler () {

                                                                       @Override
                                                                       public void handle ( String time ) {
                                                                           btnSelectorTime.setText (time );
                                                                       }
                                                                   }, "1890-01-01 00:00",
                                                                   "2020-01-01 00:00" );
                }
                mSelectorTimeDialog.show ();
            }
        } );



    }


}
