/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */

package com.yidingliu.dev.knowldegelibrary.m.litepal.exceptions;

/**
 * This is where all the global exceptions declared of LitePal.
 * 
 * @author Tony Green
 * @since 1.0
 */
public class GlobalException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/**
	 * Application context is null.
	 */
	public static final String APPLICATION_CONTEXT_IS_NULL = "Application context is null. Maybe you haven't configured your application name with \"org.litepal.LitePalApplication\" in your AndroidManifest.xml. Or you can call LitePalApplication.initialize(Context) method instead.";

	/**
	 * Constructor of GlobalException.
	 * 
	 * @param errorMessage
	 *            the description of this exception.
	 */
	public GlobalException(String errorMessage) {
		super(errorMessage);
	}
}
