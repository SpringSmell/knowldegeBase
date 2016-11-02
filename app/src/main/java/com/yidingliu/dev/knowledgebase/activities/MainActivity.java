/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowledgebase.activities;

import android.content.Intent;
import android.view.View;
import android.widget.ExpandableListView;

import com.yidingliu.dev.knowldegelibrary.b.BaseSideSlipMenuActivity;
import com.yidingliu.dev.knowldegelibrary.function.AlertWindows;
import com.yidingliu.dev.knowldegelibrary.function.SelectorImgActivity;
import com.yidingliu.dev.knowledgebase.R;
import com.yidingliu.dev.knowledgebase.fragments.TestFragment;

import java.util.LinkedList;

public class MainActivity extends BaseSideSlipMenuActivity {

    @Override
    protected LinkedList< MainMenu > initData ( LinkedList< MainMenu > mainMenuContentList ) {
        mainMenuContentList.add ( new MainMenu ( "选择器" ) );
        mainMenuContentList.add ( new MainMenu ( "相片选择" ) );
        mainMenuContentList.add ( new MainMenu ( "AlertDialog" ) );
        mainMenuContentList.add ( new MainMenu ( "请求权限") );
        mainMenuContentList.add ( new MainMenu ( "NIO") );
        mainMenuContentList.add ( new MainMenu ( "XRecyclerViewActivity") );
        mainMenuContentList.add ( new MainMenu ( "录音") );
        mainMenuContentList.add ( new MainMenu ( "麦克风") );

        return mainMenuContentList;
    }

    @Override public void onBind () {
        replaceFragment ( new TestFragment () );
    }

    @Override public void onInitTitle () {
        super.onInitTitle ();
//        setBackGroundContent ( R.color.colorWhite );
        setBackGroundMenu ( R.color.colorWhite );
//        setBackGroundContent ( R.color.colorPrimary );
//        setTitleBackGroundRes ( R.color.colorPrimary );
    }

    @Override
    public boolean onChildClick ( ExpandableListView parent, View v, int groupPosition, int childPosition, long id ) {
        return false;
    }

    @Override
    public boolean onGroupClick ( ExpandableListView parent, View v, int groupPosition, long id ) {
        switch (groupPosition){
            case 0:
                SelectorActivity.startAction(this,SelectorActivity.class,"选择器");
                break;
            case 1:
//                startActivity ( new Intent ( this, TabHostDomeFragment.class ) );
                SelectorImgActivity.startAction ( this, 0x12, "选择照片", null, 6 );
                break;
            case 2:
                AlertWindows.getInstance ().alertIsOkDialog ( this, "你接收到一个标题", "你有一个消息内容",
                                                              new AlertWindows.OnClickListener () {

                                                                  @Override
                                                                  public void onClicked ( View view, boolean isOk ) {

                                                                  }
                                                              } );
                break;
            case 3:
                PermissionActivity.startAction ( this,PermissionActivity.class,"请求权限" );
                break;
            case 4:
                TestNIOActivity.startAction ( this,TestNIOActivity.class,"NIO" );
                break;
            case 5:
                XRecyclerViewActivity.startAction ( this,XRecyclerViewActivity.class,"XRecyclerView" );
break;
            case 6:
                startActivity ( new Intent (this,AnalyzeActivity.class) );
                break;
            case 7:
                VoiceActivity.startAction ( this,VoiceActivity.class,"获取麦克风音量" );
        }
        return false;
    }
}
