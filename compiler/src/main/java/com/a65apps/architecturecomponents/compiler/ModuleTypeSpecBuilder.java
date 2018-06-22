package com.a65apps.architecturecomponents.compiler;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.lang.model.element.Modifier;

public final class ModuleTypeSpecBuilder {

    @Nonnull
    private final String className;
    @Nonnull
    private final String subComponentName;
    @Nonnull
    private final MethodInfo info;
    @Nonnull
    private final ClassName presenterComponentBuilder;
    @Nonnull
    private final TypeSpec subComponent;

    ModuleTypeSpecBuilder(@Nonnull String className, @Nonnull String subComponentName,
                                 @Nonnull MethodInfo info, @Nonnull ClassName presenterComponentBuilder,
                                 @Nonnull TypeSpec subComponent) {
        this.className = className;
        this.subComponentName = subComponentName;
        this.info = info;
        this.presenterComponentBuilder = presenterComponentBuilder;
        this.subComponent = subComponent;
    }

    @Nonnull
    public TypeSpec build() {
        return TypeSpec.interfaceBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(ClassName.get(Const.DAGGER,
                        Const.MODULE))
                        .addMember(Const.SUBCOMPONENTS, Const.T_CLASS,
                                ClassName.get("", className, subComponentName))
                        .build())
                .addMethod(MethodSpec.methodBuilder(Const.BIND_PRESENTER_BUILDER)
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .addAnnotation(ClassName.get(Const.DAGGER, Const.BINDS))
                        .addAnnotation(ClassName.get(Const.DAGGER_MULTIBINDINGS, Const.INTO_MAP))
                        .addAnnotation(AnnotationSpec.builder(
                                ClassName.get(Const.COM_A65APPS_DAGGERARCHITECTURECOMPONENTS_PRESENTER,
                                        Const.PRESENTER_KEY))
                                .addMember(Const.VALUE, Const.T_CLASS, info.getReturnType())
                                .build())
                        .addParameter(ParameterSpec.builder(ClassName.get("",
                                subComponentName, Const.BUILDER),
                                Const.BUILDER.toLowerCase(Locale.getDefault()))
                                .build())
                        .returns(presenterComponentBuilder)
                        .build())
                .addType(subComponent)
                .build();
    }
}
