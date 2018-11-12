package com.a65apps.architecturecomponents.sample.presentation.common;

import com.tngtech.jgiven.annotation.IsTag;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IsTag
@Retention(RetentionPolicy.RUNTIME)
public @interface Story {
    String[] value();
}
