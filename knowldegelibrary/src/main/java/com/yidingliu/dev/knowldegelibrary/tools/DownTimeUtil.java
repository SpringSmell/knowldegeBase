/*
 *
 *  * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 *
 */

package com.yidingliu.dev.knowldegelibrary.tools;

import android.graphics.Color;
import android.os.CountDownTimer;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.yidingliu.dev.knowldegelibrary.R;


/**
 * 验证码倒计时工具
 * Created by Administrator on 2016/4/26.
 */
public class DownTimeUtil extends CountDownTimer {

    private TextView mTextView;

    public DownTimeUtil(long millisInFuture, long countDownInterval, TextView mTextView) {
        super(millisInFuture, countDownInterval);
        this.mTextView = mTextView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        mTextView.setClickable(false);
        mTextView.setText(millisUntilFinished / 1000 + "秒");
        mTextView.setBackgroundResource(R.drawable.bg_gp_down_time_grey);

        SpannableString spannableString =new SpannableString(mTextView.getText().toString());
        ForegroundColorSpan span =new ForegroundColorSpan(Color.RED);
        spannableString.setSpan(span,0,2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        mTextView.setText(spannableString);
    }

    @Override
    public void onFinish() {
        mTextView.setText("重新获取");
        mTextView.setClickable(true);
        mTextView.setTextColor(Color.RED);
        mTextView.setBackgroundResource(R.drawable.bg_gp_down_time_white);
    }
}
