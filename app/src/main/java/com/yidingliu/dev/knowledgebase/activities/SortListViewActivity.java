/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowledgebase.activities;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yidingliu.dev.knowldegelibrary.b.BaseActivity;
import com.yidingliu.dev.knowldegelibrary.b.BaseParentViewHolder;
import com.yidingliu.dev.knowldegelibrary.tools.chineseUtils.CharacterUtils;
import com.yidingliu.dev.knowldegelibrary.widgets.ClearEditText;
import com.yidingliu.dev.knowledgebase.tools.PinyinComparator;
import com.yidingliu.dev.knowldegelibrary.widgets.SideBarView;
import com.yidingliu.dev.knowledgebase.adapter.SortAdapter;
import com.yidingliu.dev.knowledgebase.model.SortModel;
import com.yidingliu.dev.knowledgebase.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/10/10
 * @modifyInfo1 chriszou-16/10/10
 * @modifyContent
 */
public class SortListViewActivity extends BaseActivity {

    private ListView sortListView;
    private SideBarView mSideBarView;
    private TextView dialog;
    private SortAdapter adapter;
    private ClearEditText mClearEditText;

    private CharacterUtils mCharacterUtils;
    private List<SortModel> SourceDateList;

    private PinyinComparator pinyinComparator;

    @Override public int onResultLayoutResId () {

        return R.layout.activity_sortlistview;
    }

    @Override public void onInitView ( BaseParentViewHolder holder ) {

        mCharacterUtils = CharacterUtils.getInstance ();

        pinyinComparator = new PinyinComparator ();

        mSideBarView = ( SideBarView ) findViewById ( R.id.sidrbar );
        dialog = (TextView ) findViewById ( R.id.dialog );
        mSideBarView.setTextView ( dialog );

        mSideBarView.setOnTouchingLetterChangedListener ( new SideBarView.OnTouchingLetterChangedListener () {

            @Override
            public void onTouchingLetterChanged(String s) {

                int position = adapter.getPositionForSection(s.charAt(0));
                if(position != -1){
                    sortListView.setSelection(position);
                }

            }
        } );

        sortListView = (ListView ) findViewById (
                R.id.country_lvcountry );
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener () {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText ( getApplication (), ((SortModel )adapter.getItem ( position )).getName (), Toast.LENGTH_SHORT ).show ();
            }
        });

        SourceDateList = filledData (getResources().getStringArray (
                R.array.date ) );
        Collections.sort ( SourceDateList, pinyinComparator );

        List<String> sideTexts=new ArrayList<> (  );
        for(SortModel model:SourceDateList){
            if(!sideTexts.contains ( model.getSortLetters () )){
                sideTexts.add ( model.getSortLetters () );
            }
        }
        mSideBarView.setSideLetter ( sideTexts );

        adapter = new SortAdapter ( this, SourceDateList);
        sortListView.setAdapter(adapter);


        mClearEditText = (ClearEditText ) findViewById (
                R.id.filter_edit );

        mClearEditText.addTextChangedListener(new TextWatcher () {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged (Editable s ) {
            }
        });
    }

    @Override public void onInitLayout () {

        super.onInitLayout ();
        setBackGroundContentColor ( Color.WHITE );
    }

    @Override public void onBindData ( BaseParentViewHolder holder ) {

    }

    private List<SortModel> filledData(String [] date){
        List<SortModel> mSortList = new ArrayList<SortModel> ();

        for(int i=0; i<date.length; i++){
            SortModel sortModel = new SortModel();
            sortModel.setName(date[i]);

            String pinyin = mCharacterUtils.getSelling ( date[i] );
            sortModel.setPinying ( pinyin );
            String sortString = pinyin.substring(0, 1).toUpperCase();

            if(sortString.matches("[A-Z]")){
                sortModel.setSortLetters(sortString.toUpperCase());
            }else{
                sortModel.setSortLetters("#");
            }

            mSortList.add(sortModel);
        }
        return mSortList;

    }

    private void filterData(String filterStr){
        List<SortModel> filterDateList = new ArrayList<SortModel>();

        if( TextUtils.isEmpty ( filterStr )){
            filterDateList = SourceDateList;
        }else{
            filterDateList.clear();
            for(SortModel sortModel : SourceDateList){
                String name = sortModel.getName();
                if(name.indexOf(filterStr.toString()) != -1 || mCharacterUtils.getSelling ( name ).startsWith ( filterStr.toString () )){
                    filterDateList.add(sortModel);
                }
            }
        }

        Collections.sort(filterDateList, pinyinComparator);
        adapter.updateListView(filterDateList);
    }
}
