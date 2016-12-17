package com.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * Get注解
 * @author yunfeng.cheng
 * @create 2016-08-03
 */

@Target(ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Get {
	public String encode() default "UTF-8";
}
