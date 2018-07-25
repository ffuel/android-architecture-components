package com.a65apps.architecturecomponents.compiler;

import com.a65apps.architecturecomponents.compiler.annotation.ContributesPresenterInjector;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;

public final class SubComponentBuilder {

    @Nonnull
    private final ProcessingEnvironment processingEnv;
    @Nonnull
    private final String subComponentName;
    @Nonnull
    private final DeclaredType[] modules;
    @Nonnull
    private final ParameterizedTypeName baseProvider;
    @Nonnull
    private final ClassName presenterComponentBuilder;
    private final boolean isChild;

    SubComponentBuilder(@Nonnull ProcessingEnvironment processingEnv,
                        @Nonnull String subComponentName,
                        @Nonnull DeclaredType[] modules,
                        @Nonnull ParameterizedTypeName baseProvider,
                        @Nonnull ClassName presenterComponentBuilder,
                        boolean isChild) {
        this.processingEnv = processingEnv;
        this.subComponentName = subComponentName;
        this.modules = Arrays.copyOf(modules, modules.length);
        this.baseProvider = baseProvider;
        this.presenterComponentBuilder = presenterComponentBuilder;
        this.isChild = isChild;
    }

    @Nonnull
    public TypeSpec construct() {
        TypeSpec.Builder subcomponentBuilder = TypeSpec.interfaceBuilder(subComponentName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        AnnotationSpec.Builder annotationBuilder = AnnotationSpec
                .builder(ClassName.get(Const.DAGGER, Const.SUBCOMPONENT));
        StringBuilder format = new StringBuilder("{");
        List<ClassName> resultModules = new ArrayList<>();
        fillTypesData(modules, format, resultModules);
        annotationBuilder.addMember(Const.MODULES, format.toString(),
                resultModules.toArray(new Object[resultModules.size()]));
        if (isChild) {
            subcomponentBuilder.addAnnotation(ClassName.get(Const.COM_A65APPS_DAGGERARCHITECTURECOMPONENTS_PRESENTER,
                    Const.CHILD_PRESENTER_SCOPE));
        } else {
            subcomponentBuilder.addAnnotation(ClassName.get(Const.COM_A65APPS_DAGGERARCHITECTURECOMPONENTS_PRESENTER,
                    Const.PRESENTER_SCOPE));
        }
        subcomponentBuilder.addAnnotation(annotationBuilder.build());
        subcomponentBuilder.addSuperinterface(baseProvider);

        ParameterizedTypeName baseBuilder = ParameterizedTypeName.get(presenterComponentBuilder,
                ClassName.get("", subComponentName));
        subcomponentBuilder.addType(TypeSpec.interfaceBuilder(Const.BUILDER)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addAnnotation(ClassName.get(Const.DAGGER, Const.SUBCOMPONENT,
                        Const.BUILDER))
                .addSuperinterface(baseBuilder)
                .build());

        return subcomponentBuilder.build();
    }

    private void fillTypesData(@Nonnull DeclaredType[] modules, @Nonnull StringBuilder format,
                               @Nonnull List<ClassName> resultModules) {
        for (int i = 0; i < modules.length; i++) {
            boolean isSkip = false;
            for (Element element: modules[i].asElement().getEnclosedElements()) {
                if (element.getKind() == ElementKind.METHOD) {
                    isSkip = processAnnotatedMethod(element, format, resultModules);
                }
            }
            if (!isSkip) {
                resultModules.add(ClassName.get((TypeElement) modules[i].asElement()));
                format.append(Const.T_CLASS);
                if (i + 1 < modules.length) {
                    format.append(", ");
                }
            }
        }
        format.append('}');
    }

    private boolean processAnnotatedMethod(@Nonnull Element element,
                                           @Nonnull StringBuilder format,
                                           @Nonnull List<ClassName> resultModules) {
        MethodInfo childInfo = new MethodInfo((ExecutableElement) element);
        AnnotationInfo annotationInfo = AnnotationInfo.computeInfo(processingEnv, element,
                ContributesPresenterInjector.class);
        if (annotationInfo.isValid()) {
            DeclaredType[] childModules = annotationInfo.getModules();
            String childClassName = String.format(Const.S_BIND_S,
                    childInfo.getParentName(),
                    childInfo.getReturnTypeName());
            for (int j = 0; j < childModules.length; j++) {
                resultModules.add(ClassName.get(childInfo.getParentPackageName(),
                        childClassName));
                format.append(Const.T_CLASS);
                if (j < childModules.length) {
                    format.append(", ");
                }
            }
            return true;
        }

        return false;
    }
}
