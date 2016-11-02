/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowldegelibrary.m.okhttp;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.yidingliu.dev.knowldegelibrary.m.okhttp.builder.GetBuilder;
import com.yidingliu.dev.knowldegelibrary.m.okhttp.builder.PostFileBuilder;
import com.yidingliu.dev.knowldegelibrary.m.okhttp.builder.PostFormBuilder;
import com.yidingliu.dev.knowldegelibrary.m.okhttp.builder.PostStringBuilder;
import com.yidingliu.dev.knowldegelibrary.m.okhttp.callback.Callback;
import com.yidingliu.dev.knowldegelibrary.m.okhttp.cookie.CookiesManager;
import com.yidingliu.dev.knowldegelibrary.m.okhttp.https.HttpsUtils;
import com.yidingliu.dev.knowldegelibrary.m.okhttp.request.RequestCall;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.Call;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhy on 15/8/17.
 */
public class OkHttpUtils
{
    public static final String TAG = "OkHttpUtils";
    public static final long DEFAULT_MILLISECONDS = 10000;
    private static OkHttpUtils mInstance;
    private OkHttpClient mOkHttpClient;
    private Handler mDelivery;

    private OkHttpUtils ( Context context )
    {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //cookie enabled
        okHttpClientBuilder.cookieJar (new CookiesManager ( context) );
        mDelivery = new Handler(Looper.getMainLooper());


//        if (true) ssl请求专用
//        {
//            okHttpClientBuilder.hostnameVerifier(new HostnameVerifier()
//            {
//                @Override
//                public boolean verify(String hostname, SSLSession session)
//                {
//                    return true;
//                }
//            });
//        }

        mOkHttpClient = okHttpClientBuilder.build();
    }

    private boolean debug;
    private String tag;

    public OkHttpUtils debug(String tag)
    {
        debug = true;
        this.tag = tag;
        return this;
    }


    public static OkHttpUtils getInstance(Context context)
    {
        if (mInstance == null)
        {
            synchronized (OkHttpUtils.class)
            {
                if (mInstance == null)
                {
                    mInstance = new OkHttpUtils(context);
                }
            }
        }
        return mInstance;
    }


    public static OkHttpUtils getInstance()//此方法只用来取实例且需要getInstance(Context context)调用后才能使用 hzm
    {
//        if (mInstance == null)
//        {
//            synchronized (OkHttpUtils.class)
//            {
//                if (mInstance == null)
//                {
////                    mInstance = new OkHttpUtils();
//                }
//            }
//        }
        return mInstance;
    }


    public Handler getDelivery()
    {
        return mDelivery;
    }

    public OkHttpClient getOkHttpClient()
    {
        return mOkHttpClient;
    }


    public static GetBuilder get()
    {
        return new GetBuilder ();
    }

    public static PostStringBuilder postString ()
    {
        return new PostStringBuilder();
    }

    public static PostFileBuilder postFile ()
    {
        return new PostFileBuilder();
    }

    public static PostFormBuilder post ()
    {
        return new PostFormBuilder();
    }


    public void execute ( final RequestCall requestCall, Callback callback )
    {
        if (debug)
        {
            if(TextUtils.isEmpty(tag))
            {
                tag = TAG;
            }
            if (debug)  Log.d(tag, "{method:" + requestCall.getRequest().method() + ", detail:" + requestCall.getOkHttpRequest().toString() + "}");
        }
        if (debug) Log.d(tag, "execute执行了啊......");
        if (callback == null)
            callback = Callback.CALLBACK_DEFAULT;
        final Callback finalCallback = callback;

        final Call call = requestCall.getCall();
        
        call.enqueue(new okhttp3.Callback() {
        	
			@Override
			public void onResponse(Response response) throws IOException {
				if (debug){
					
					 Log.d(tag, "Response  cache response:    " + response.cacheResponse()+"");
					 Log.d(tag, "Response  network response:  " + response.networkResponse()+"");
					 
					 Log.d(tag, response.code()+"");
					// Log.d(tag, response.body().string());//悲剧啊， 这句打开会抛异常导致无法联网
					 
					 Headers responseHeaders = response.headers();
			         for (int i = 0, size = responseHeaders.size(); i < size; i++) {
			        	Log.d(tag, responseHeaders.name(i) + ": " + responseHeaders.value(i));
			            //System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
			         }
				}   
				 if (response.code() >= 400 && response.code() <= 599)
	                {
	                    try
	                    {   if (debug) Log.d(tag, "response.code():400-600");
	                        sendFailResultCallback(call, new RuntimeException(response.body().string()), finalCallback);
	                    } catch (IOException e)
	                    {   if (debug) Log.d(tag, "response.code():400-600 on IOException");
	                        e.printStackTrace();
	                    }
	                    return;
	                }

	                try
	                {   if (debug) Log.d(tag, "response.code():<400 || >600");
	                    Object o = finalCallback.parseNetworkResponse(response);
	                    sendSuccessResultCallback(o, finalCallback);
	                } catch (Exception e)
	                {   if (debug) Log.d(tag, "response.code():<400 || >600 on Exception");
	                    sendFailResultCallback(call, e, finalCallback);
	                }
			}
			
			@Override
			public void onFailure(Request arg0, IOException e) {
				 if (debug) Log.d(tag, "onFailure, Request :"+arg0.toString());
				 sendFailResultCallback(call, e, finalCallback);
				
			}
		});
    }


    public void sendFailResultCallback(final Call call, final Exception e, final Callback callback)
    {
        if (callback == null) return;

        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onError(call, e);
                callback.onAfter();
            }
        });
    }

    public void sendSuccessResultCallback(final Object object, final Callback callback)
    {
        if (callback == null) return;
        mDelivery.post(new Runnable()
        {
            @Override
            public void run()
            {
                callback.onResponse(object);
                callback.onAfter();
            }
        });
    }

    public void cancelTag(Object tag)
    {
        for (Call call : mOkHttpClient.dispatcher().queuedCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
        for (Call call : mOkHttpClient.dispatcher().runningCalls())
        {
            if (tag.equals(call.request().tag()))
            {
                call.cancel();
            }
        }
    }


    public void setCertificates(InputStream... certificates)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .sslSocketFactory ( HttpsUtils.getSslSocketFactory ( certificates, null, null ) )
                .build();
    }


    public void setConnectTimeout(int timeout, TimeUnit units)
    {
        mOkHttpClient = getOkHttpClient().newBuilder()
                .connectTimeout(timeout, units)
                .build();
    }
}

