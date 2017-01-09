package com.cyfonly.nettice.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Namespace注解
 * @author yunfeng.cheng
 * @create 2016-08-03
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface Namespace {
	public String value() default "/";
}
