package com.a65apps.architecturecomponents.compiler;

import com.squareup.javapoet.ClassName;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;

final class MethodInfo {

    private final ExecutableElement element;

    MethodInfo(ExecutableElement executableElement) {
        this.element = executableElement;
    }

    boolean isValid() {
        return !element.getModifiers().contains(Modifier.PROTECTED)
                && !element.getModifiers().contains(Modifier.PRIVATE)
                && element.getReturnType().getKind() == TypeKind.DECLARED;
    }

    String getName() {
        return element.getSimpleName().toString();
    }

    String getParentName() {
        return element.getEnclosingElement().getSimpleName().toString();
    }

    String getParentPackageName() {
        return getParentPackageName(element.getEnclosingElement());
    }

    private String getParentPackageName(Element parent) {
        if (parent.getKind() != ElementKind.CLASS && parent.getKind() != ElementKind.INTERFACE) {
            return getParentPackageName(parent.getEnclosingElement());
        }

        TypeElement typeElement = (TypeElement) parent;
        return ClassName.get(typeElement).packageName();
    }

    ClassName getReturnType() {
        TypeMirror type = element.getReturnType();
        if (type.getKind() != TypeKind.DECLARED) {
            return null;
        }
        DeclaredType classType = (DeclaredType) type;
        return ClassName.get((TypeElement) classType.asElement());
    }

    String getReturnTypeName() {
        TypeMirror type = element.getReturnType();
        if (type.getKind() != TypeKind.DECLARED) {
            return "";
        }
        DeclaredType classType = (DeclaredType) type;
        return classType.asElement().getSimpleName().toString();
    }

    List<? extends TypeParameterElement> getReturnTypeGenerics() {
        TypeMirror type = element.getReturnType();
        if (type.getKind() != TypeKind.DECLARED) {
            return Collections.emptyList();
        }

        return getReturnTypeGenerics((TypeElement) ((DeclaredType) type).asElement());
    }

    private List<? extends TypeParameterElement> getReturnTypeGenerics(TypeElement classType) {
        List<? extends TypeParameterElement> types = classType.getTypeParameters();
        if (types.isEmpty()) {
            TypeMirror supperType = classType.getSuperclass();
            if (supperType.getKind() != TypeKind.DECLARED) {
                return Collections.emptyList();
            }
            DeclaredType supperClassType = (DeclaredType) supperType;
            return getReturnTypeGenerics((TypeElement) supperClassType.asElement());
        }

        return types;
    }

    List<TypeElement> getReturnArgumentsGenerics() {
        TypeMirror type = element.getReturnType();
        if (type.getKind() != TypeKind.DECLARED) {
            return Collections.emptyList();
        }

        return getReturnArgumentsGenerics((DeclaredType) type);
    }

    private List<TypeElement> getReturnArgumentsGenerics(DeclaredType classType) {
        List<? extends TypeMirror> types = classType.getTypeArguments();
        if (types.isEmpty()) {
            TypeMirror supperType = ((TypeElement) classType.asElement()).getSuperclass();
            if (supperType.getKind() != TypeKind.DECLARED) {
                return Collections.emptyList();
            }
            DeclaredType supperClassType = (DeclaredType) supperType;
            return getReturnArgumentsGenerics(supperClassType);
        }

        List<TypeElement> result = new ArrayList<>();
        for (TypeMirror typeMirror: types) {
            if (typeMirror.getKind() == TypeKind.DECLARED) {
                result.add((TypeElement) ((DeclaredType) typeMirror).asElement());
            }
        }

        return result;
    }
}
