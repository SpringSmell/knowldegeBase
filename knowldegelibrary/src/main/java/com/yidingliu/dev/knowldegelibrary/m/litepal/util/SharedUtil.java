/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowldegelibrary.m.litepal.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.yidingliu.dev.knowldegelibrary.m.litepal.LitePalApplication;


/**
 * LitePal used shared preferences a lot for storing versions and a lot of other
 * necessary values. This utility helps LitePal save and read each key-value
 * pairs from shared preferences file.
 * 
 * @author Tony Green
 * @since 1.0
 */
public class SharedUtil {

	private static final String VERSION = "litepal_version";

	private static final String LITEPAL_PREPS = "litepal_prefs";

	/**
	 * Each time database upgrade, the version of database stored in shared
	 * preference will update.
	 *
	 * @param newVersion
     *          new version of database
	 */
	public static void updateVersion(int newVersion) {
		SharedPreferences.Editor sEditor = LitePalApplication.getContext ()
		                                                     .getSharedPreferences(LITEPAL_PREPS, Context.MODE_PRIVATE).edit();
		sEditor.putInt(VERSION, newVersion);
		sEditor.apply();
	}

	/**
	 * Get the last database version.
	 * 
	 * @return the last database version
	 */
	public static int getLastVersion() {
		SharedPreferences sPref = LitePalApplication.getContext().getSharedPreferences(
				LITEPAL_PREPS, Context.MODE_PRIVATE);
		return sPref.getInt(VERSION, 0);
	}

}
