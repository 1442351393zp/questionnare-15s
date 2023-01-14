package com.infoac.cas.dto;

import java.lang.annotation.*;


/*
 * 自定义注解 拦截Controller
 */
@Target({ElementType.PARAMETER, ElementType.METHOD })//方法注解
@Retention(RetentionPolicy.RUNTIME)//运行时可见
public @interface Log {
	String operateType();// 记录日志的操作类型
}
