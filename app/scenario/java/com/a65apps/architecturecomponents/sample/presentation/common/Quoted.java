package com.a65apps.architecturecomponents.sample.presentation.common;

import com.tngtech.jgiven.annotation.Format;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Format(args = "\"%s\"" )
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.PARAMETER, ElementType.ANNOTATION_TYPE } )
public @interface Quoted {
}
