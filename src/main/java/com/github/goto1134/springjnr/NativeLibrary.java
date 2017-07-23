package com.github.goto1134.springjnr;

import java.lang.annotation.*;

/**
 * Use this to mark an interface as native library
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NativeLibrary {
}
