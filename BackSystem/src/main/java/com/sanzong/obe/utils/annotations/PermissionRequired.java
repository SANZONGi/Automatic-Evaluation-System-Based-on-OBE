package com.sanzong.obe.utils.annotations;

import java.lang.annotation.*;
import java.util.List;

/**
 * @author SANZONG
 */
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.METHOD)
public @interface PermissionRequired {
    int[] role();
}
