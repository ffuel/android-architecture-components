package com.a65apps.architecturecomponents.compiler;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;

public final class AnnotationInfo {

    @Nonnull
    private final ProcessingEnvironment processingEnv;
    @Nonnull
    private final Element type;
    @Nonnull
    private final TypeElement annotationType;

    private boolean isChild;
    @Nonnull
    private final List<DeclaredType> modules = new ArrayList<>();

    @Nonnull
    public static AnnotationInfo computeInfo(@Nonnull ProcessingEnvironment processingEnv,
                                             @Nonnull  Element type,
                                             @Nonnull Class<? extends Annotation> annotationClass) {
        return new AnnotationInfo(processingEnv, type, annotationClass).compute();
    }

    private AnnotationInfo(@Nonnull ProcessingEnvironment processingEnv,
                           @Nonnull  Element type,
                           @Nonnull Class<? extends Annotation> annotation) {
        this.processingEnv = processingEnv;
        this.type = type;
        this.annotationType = processingEnv.getElementUtils()
                .getTypeElement(annotation.getName());
    }

    public boolean isValid() {
        return !modules.isEmpty();
    }

    @Nonnull
    public DeclaredType[] getModules() {
        return modules.toArray(new DeclaredType[modules.size()]);
    }

    boolean isChild() {
        return isChild;
    }

    @Nonnull
    private AnnotationInfo compute() {
        AnnotationValue valueModules = null;
        AnnotationValue valueIsChild = null;
        for (AnnotationMirror annotationMirror: type.getAnnotationMirrors()) {
            if (annotationMirror.getAnnotationType().asElement().getSimpleName()
                    .equals(annotationType.getSimpleName())) {
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                        :annotationMirror.getElementValues().entrySet()) {
                    if (entry.getKey().getSimpleName().toString().equals(Const.MODULES)) {
                        valueModules = entry.getValue();
                    } else if (entry.getKey().getSimpleName().toString().equals("isChild")) {
                        valueIsChild = entry.getValue();
                    }
                }
                break;
            }
        }
        if (valueModules == null) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    "modules of annotation is not found.");
            return this;
        }

        @SuppressWarnings("unchecked")
        List<AnnotationValue> list = (List<AnnotationValue>) valueModules.getValue();
        for (AnnotationValue value: list) {
            DeclaredType declaredType = (DeclaredType) value.getValue();
            modules.add(declaredType);
        }
        if (valueIsChild != null) {
            isChild = (boolean) valueIsChild.getValue();
        }

        return this;
    }
}
