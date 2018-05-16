package com.a65apps.architecturecomponents.compiler.annotation;

import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target(METHOD)
public @interface ContributesPresenterInjector {

    Class<?>[] modules() default {};

    boolean isChild() default false;
}
