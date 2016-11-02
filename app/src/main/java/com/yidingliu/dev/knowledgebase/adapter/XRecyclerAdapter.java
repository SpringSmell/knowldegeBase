/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowledgebase.adapter;

import com.yidingliu.dev.knowldegelibrary.b.BaseRecyclerViewAdapter;
import com.yidingliu.dev.knowledgebase.R;
import com.yidingliu.dev.knowledgebase.model.TestBean;

/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/10/9
 * @modifyInfo1 chriszou-16/10/9
 * @modifyContent
 */
public class XRecyclerAdapter extends BaseRecyclerViewAdapter<TestBean> {

    @Override
    public void onBindData ( BaseViewHolder holder, int position, TestBean itemData ) {
        holder.setText ( R.id.xRecyclerContentA,itemData.getA () );
    }

    @Override public int resultLayoutResId () {

        return R.layout.item_xrecyclerview;
    }
}
