package org.bytedeco.javacpp.annotation;

import org.bytedeco.javacpp.tools.Generator;

import java.lang.annotation.*;

/**
 * Annotation indicating that {@link java.lang.String} should be mapped
 * to array of UTF-16 code units ({@code unsigned short*}) instead
 * of byte array ({@code const char*})
 *
 * @see Generator
 */

@Documented @Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.PARAMETER})
public @interface AsUtf16 { }