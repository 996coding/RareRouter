package com.lxf.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/*
仅仅限制于该SDK内部使用
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface FlagAnnotation {
    String info() default "this is a flag,do not use!";
}
