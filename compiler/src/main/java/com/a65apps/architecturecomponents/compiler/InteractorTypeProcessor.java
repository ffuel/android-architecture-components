package com.a65apps.architecturecomponents.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;

public final class InteractorTypeProcessor extends TypeProcessor {

    InteractorTypeProcessor(@Nullable ClassName compositeState, @Nullable ClassName router,
                            @Nonnull TypeElement param, @Nonnull TypeName name) {
        super(compositeState, router, param, name);
    }

    @Override
    public boolean isFor(@Nonnull TypeParameterElement element) {
        return element.getSimpleName().toString().equals("I");
    }

    @Nonnull
    @Override
    public TypeName process() {
        if (hasCompositeState()) {
            return ParameterizedTypeName.get(ClassName.get(getParam()), getName(), getCompositeState(),
                    getRouter());
        }

        return ParameterizedTypeName.get(ClassName.get(getParam()), getName(), getRouter());
    }
}
