/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowldegelibrary.m.okhttp.cookie;

import android.content.Context;

import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class CookiesManager implements CookieJar {
	Context context;
	private static PersistentCookieStore cookieStore;
	public CookiesManager(Context cn){
		
		context=cn;
		cookieStore = new PersistentCookieStore(context);
	}
	
    

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if (cookies != null && cookies.size() > 0) {
            for (Cookie item : cookies) {
                cookieStore.add(url, item);
            }
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = cookieStore.get(url);
        return cookies;
    }
}
