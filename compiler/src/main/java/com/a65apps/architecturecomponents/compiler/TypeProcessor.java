package com.a65apps.architecturecomponents.compiler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;

public abstract class TypeProcessor {

    @Nullable
    private final ClassName compositeState;
    @Nullable
    private final ClassName router;
    @Nonnull
    private final TypeElement param;
    @Nonnull
    private final TypeName name;

    TypeProcessor(@Nullable ClassName compositeState, @Nullable ClassName router,
                  @Nonnull TypeElement param, @Nonnull TypeName name) {
        this.compositeState = compositeState;
        this.router = router;
        this.param = param;
        this.name = name;
    }

    boolean hasCompositeState() {
        return compositeState != null;
    }

    @Nullable
    ClassName getCompositeState() {
        return compositeState;
    }

    @Nullable
    ClassName getRouter() {
        return router;
    }

    @Nonnull
    TypeElement getParam() {
        return param;
    }

    @Nonnull
    TypeName getName() {
        return name;
    }

    public abstract boolean isFor(@Nonnull TypeParameterElement element);

    @Nonnull
    public abstract TypeName process();
}
