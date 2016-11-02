package com.yidingliu.dev.knowledgebase.activities;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.yidingliu.dev.knowldegelibrary.b.BaseActivity;
import com.yidingliu.dev.knowldegelibrary.b.BaseParentViewHolder;
import com.yidingliu.dev.knowldegelibrary.m.Log;
import com.yidingliu.dev.knowldegelibrary.widgets.SlideDirectionView;
import com.yidingliu.dev.knowldegelibrary.widgets.VoiceWaveView;
import com.yidingliu.dev.knowledgebase.R;
import com.yidingliu.dev.knowledgebase.tools.AudioRecordUtils;

/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/10/14
 * @modifyInfo1 chriszou-16/10/14
 * @modifyContent
 */
public class VoiceActivity extends BaseActivity {

    public static final String TAG="VoiceActivity";
    private TextView voiceValue;
    private VoiceWaveView mVoiceWaveView;
    private SlideDirectionView mSlideDirectionView;
    private AudioRecordUtils mAudioRecordUtils;

    @Override public int onResultLayoutResId () {

        return R.layout.activity_voice;
    }

    @Override public void onInitView ( BaseParentViewHolder holder ) {

        voiceValue = holder.getView ( R.id.voiceValue );
        mVoiceWaveView=holder.getView ( R.id.voiceWaveView );
        mSlideDirectionView=holder.getView ( R.id.slideDirection );
        mSlideDirectionView.setOnSideDirectionListener (
                new SlideDirectionView.OnSlideDirectionListener () {

                    @Override public void downSlide ( SlideDirectionView view ) {
                        startVoice ();
                        view.setText ( "松开 完成" );
                        mVoiceWaveView.setTextHint ( "手指上滑,取消发送" );
                        mVoiceWaveView.setVisibility ( View.VISIBLE );
                    }

                    @Override
                    public void upSlide ( SlideDirectionView view, float dx, float dy, float mx, float my, float distance ) {
                        mVoiceWaveView.setTextHint ( "手指上滑,取消发送" );
                    }

                    @Override public void beyondSlide ( SlideDirectionView view, float distance ) {
                        view.setText ( "松开取消发送" );
                        mVoiceWaveView.setCancelTxt ( "松开手指,取消发送" , Color.GREEN );
                    }

                    @Override public void confirm ( SlideDirectionView view ) {
                        showSnackbar ( "confirm" );
                        Log.e ( TAG,"teste" );
                        android.util.Log.e ( TAG,"test" );
                    }

                    @Override public void cancel ( SlideDirectionView view ) {
                        showSnackbar ( "cancel" );
                    }
                    @Override public void finishSlide ( SlideDirectionView view ) {
                        mVoiceWaveView.setVisibility ( View.GONE );
                        view.setText ( "按下 说话" );
                        stopVoice();
                    }
                } );
    }

    @Override public void onBindData ( BaseParentViewHolder holder ) {

    }

    private void startVoice(){
        mAudioRecordUtils=new AudioRecordUtils ();
        mAudioRecordUtils.getNoiseLevel ( new AudioRecordUtils.OnVoiceListener () {

            @Override public void onRealVolume ( double voice ) {
                mVoiceWaveView.setProgress ( ( int ) voice );
                voiceValue.setText ( voice+"" );
            }

            @Override public void onError ( String exMsg ) {

            }
        } );
    }

    private void stopVoice(){
        if(mAudioRecordUtils!=null){
            mAudioRecordUtils.stop ();
        }
    }
}
