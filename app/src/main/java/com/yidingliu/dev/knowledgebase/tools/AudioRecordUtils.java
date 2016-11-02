package com.yidingliu.dev.knowledgebase.tools;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;

import com.yidingliu.dev.knowldegelibrary.m.Log;

/**
 * 请填写方法内容
 *
 * @author Chris zou
 * @Date 16/10/14
 * @modifyInfo1 chriszou-16/10/14
 * @modifyContent
 */
public class AudioRecordUtils {

    private static final String TAG = "AudioRecord";
    static final int SAMPLE_RATE_IN_HZ = 8000;
    static final int BUFFER_SIZE = AudioRecord.getMinBufferSize ( SAMPLE_RATE_IN_HZ,
                                                                  AudioFormat.CHANNEL_IN_DEFAULT,
                                                                  AudioFormat.ENCODING_PCM_16BIT );
    private OnVoiceListener mOnVoiceListener;

    private AudioRecord mAudioRecord;
    private boolean isGetVoiceRun;
    private Object mLock;
    private VoiceThread mVoiceThread;

    private Handler mHandler = new Handler () {

        @Override public void handleMessage ( Message msg ) {

            if ( mOnVoiceListener != null ) {
                mOnVoiceListener.onRealVolume ( ( Double ) msg.obj );
            }
        }
    };

    public AudioRecordUtils () {
        mLock = new Object ();
    }

    private void sendMessage ( double volume ) {

        Message msg = new Message ();
        msg.obj = volume;
        mHandler.sendMessage ( msg );
    }

    public void getNoiseLevel ( OnVoiceListener onVoiceListener ) {

        this.mOnVoiceListener = onVoiceListener;
        if ( isGetVoiceRun ) {
            Log.e ( TAG, "还在录着呢" );
            mOnVoiceListener.onError ("已经开始啦");
            return;
        }
        mAudioRecord = new AudioRecord ( MediaRecorder.AudioSource.MIC,
                                         SAMPLE_RATE_IN_HZ, AudioFormat.CHANNEL_IN_DEFAULT,
                                         AudioFormat.ENCODING_PCM_16BIT, BUFFER_SIZE );
        if ( mAudioRecord == null ) {
            Log.e ( "sound", "mAudioRecord初始化失败" );
            mOnVoiceListener.onError ("mAudioRecord初始化失败");
            return;
        }
        start ();
    }

    public void start () {

        isGetVoiceRun = true;
        mVoiceThread = new VoiceThread ();
        mVoiceThread.start ();
    }

    public void stop () {

        mAudioRecord.stop ();
        mAudioRecord.release ();
        mAudioRecord = null;
        isGetVoiceRun = false;
        if ( mVoiceThread != null ) {
            mVoiceThread.interrupt ();
            mVoiceThread = null;
        }
    }

    public class VoiceThread extends Thread {

        @Override public void run () {

            mAudioRecord.startRecording ();
            short[] buffer = new short[BUFFER_SIZE];
            while ( isGetVoiceRun ) {
                //r是实际读取的数据长度，一般而言r会小于buffersize
                int  r = mAudioRecord.read ( buffer, 0, BUFFER_SIZE );
                long v = 0;
                // 将 buffer 内容取出，进行平方和运算
                for ( int i = 0 ; i < buffer.length ; i++ ) {
                    v += buffer[i] * buffer[i];
                }
                // 平方和除以数据总长度，得到音量大小。
                double mean   = v / ( double ) r;
                double volume = 10 * Math.log10 ( mean );
                Log.d ( TAG, "分贝值:" + volume );
                sendMessage ( volume );
                // 大概一秒十次
                synchronized ( mLock ) {
                    try {
                        mLock.wait ( 100 );
                    } catch ( InterruptedException e ) {
                        e.printStackTrace ();
                    }
                }
            }
        }
    }

    public interface OnVoiceListener {

        void onRealVolume ( double voice );

        void onError(String exMsg);
    }
}
