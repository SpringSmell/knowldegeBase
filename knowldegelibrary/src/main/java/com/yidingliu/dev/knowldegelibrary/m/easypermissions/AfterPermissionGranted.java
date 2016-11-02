/*
 * yidingliu.com Inc. * Copyright (c) 2016 All Rights Reserved.
 */
package com.yidingliu.dev.knowldegelibrary.m.easypermissions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AfterPermissionGranted {

    int value ();

}
