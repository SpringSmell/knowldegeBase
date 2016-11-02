/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowledgebase.activities;

import com.yidingliu.dev.knowldegelibrary.b.BaseFragmentTabHost;
import com.yidingliu.dev.knowledgebase.R;
import com.yidingliu.dev.knowledgebase.fragments.TestFragment;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Administrator
 * @comment
 * @Date 2016/9/27 0027
 * @modifyInfo1 Administrator-2016/9/27 0027
 * @modifyContent
 */
public class TabHostDomeFragment extends BaseFragmentTabHost {

    TabHostMenu tabHostMenu;

    @Override
    protected List< TabHostMenu > initData ( List< TabHostMenu > mIndexMenus ) {

        for ( int i = 0 ; i < 3 ; i++ ) {
            tabHostMenu = new TabHostMenu ();
            tabHostMenu.setName ( "首页" + i );
            tabHostMenu.setFragmentIndex ( TestFragment.class );
            tabHostMenu.setIcon ( R.drawable.selector_tab );
            tabHostMenu.setType ( 1 );
            tabHostMenu.setTablayout ( R.layout.fragment_test );
            mIndexMenus.add ( tabHostMenu );
        }
        return mIndexMenus;
    }
}
