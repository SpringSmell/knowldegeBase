/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowledgebase.activities;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.yidingliu.dev.knowldegelibrary.b.BaseActivity;
import com.yidingliu.dev.knowldegelibrary.b.BaseParentViewHolder;
import com.yidingliu.dev.knowldegelibrary.b.adapter.RecyclerViewBaseParentAdapter;
import com.yidingliu.dev.knowldegelibrary.widgets.XRecyclerView;
import com.yidingliu.dev.knowldegelibrary.widgets.xrecyclerview.ProgressStyle;
import com.yidingliu.dev.knowledgebase.R;
import com.yidingliu.dev.knowledgebase.adapter.XRecyclerAdapter;
import com.yidingliu.dev.knowledgebase.model.TestBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/10/9
 * @modifyInfo1 chriszou-16/10/9
 * @modifyContent
 */
public class XRecyclerViewActivity extends BaseActivity implements XRecyclerView.OnLoadingListener {

    private XRecyclerView mRecyclerView;
    private XRecyclerAdapter mRecyclerAdapter;


    @Override public int onResultLayoutResId () {

        return R.layout.activity_xrecyclerview;
    }

    @Override public void onInit () {

        super.onInit ();
        mRecyclerAdapter=new XRecyclerAdapter ();
    }

    @Override public void onInitLayout () {

        super.onInitLayout ();
        setBackGroundContentColor ( Color.WHITE );
        showTitleBar ( false );
    }

    @Override public void onInitView ( BaseParentViewHolder holder ) {
        final Toolbar toolbar = (Toolbar) findViewById ( R.id.toolbar );
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mRecyclerView=holder.getView ( R.id.xRecyclerView );
        mRecyclerView.setAdapter ( mRecyclerAdapter);
        LinearLayoutManager manager= new LinearLayoutManager (this);
        manager.setOrientation ( OrientationHelper.VERTICAL );
        mRecyclerView.setLayoutManager ( manager );
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView (R.mipmap.ic_font_downgrey );
        mRecyclerView.setOnLoadingListener ( this );
        mRecyclerAdapter.setOnItemClickListener (
                new RecyclerViewBaseParentAdapter.OnItemClickListener () {

                    @Override public void onClick ( View v, int position ) {
                        showSnackbar ( position+"" );
                    }
                } );
        mRecyclerView.setRefreshing ( true );
    }

    @Override public void onBindData ( BaseParentViewHolder holder ) {

    }

    @Override public void onRefresh () {
        new Handler (  ).postDelayed ( new Runnable () {

            @Override public void run () {
                List<TestBean> testBeen=new ArrayList<> (  );
                for(int i=0;i<15;i++){
                    TestBean bean=new TestBean ();
                    bean.setA ( i+"" );
                    testBeen.add ( bean );
                }
                mRecyclerAdapter.setDatas ( testBeen );
                mRecyclerView.refreshComplete (true);

            }
        } ,1000);
    }

    @Override public void onLoadMore () {
        new Handler (  ).postDelayed ( new Runnable () {

            @Override public void run () {
                if(mRecyclerAdapter.getItemCount ()>50){
                    mRecyclerView.setNoMore ( true ,"0位好友");
                    return;
                }
                List<TestBean> testBeen=new ArrayList<> (  );
                for(int i=mRecyclerAdapter.getItemCount ();i<mRecyclerAdapter.getItemCount ()+10;i++){
                    TestBean bean=new TestBean ();
                    bean.setA ( i+"" );
                    testBeen.add ( bean );
                }
                mRecyclerAdapter.getDatas ().addAll ( testBeen );
                mRecyclerView.loadMoreComplete ();
            }
        } ,1000);
    }
}
