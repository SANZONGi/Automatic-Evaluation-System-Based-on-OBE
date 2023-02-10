package com.sanzong.obe.utils.annotations;

import java.lang.annotation.*;

/**
 * @author SANZONG
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.METHOD)
public @interface LoginRequired {
    boolean required() default false;
}