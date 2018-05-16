package com.a65apps.architecturecomponents.compiler;

import com.a65apps.architecturecomponents.compiler.annotation.ContributesPresenterInjector;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.a65apps.architecturecomponents.compiler.annotation.ContributesPresenterInjector"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ContributesPresenterInjectorProcessor extends AbstractProcessor {

    private static final String COM_A65APPS_DAGGERARCHITECTURECOMPONENTS_PRESENTER =
            "com.a65apps.daggerarchitecturecomponents.presenter";
    private static final String PROVIDER_PRESENTER_COMPONENT = "ProviderPresenterComponent";
    private static final String PRESENTER_COMPONENT_BUILDER = "PresenterComponentBuilder";
    private static final String DAGGER = "dagger";
    private static final String S_BIND_S = "%s_Bind%s";
    private static final String S_SUBCOMPONENT = "%sSubcomponent";
    private static final String SUBCOMPONENT = "Subcomponent";
    private static final String MODULES = "modules";
    private static final String CHILD_PRESENTER_SCOPE = "ChildPresenterScope";
    private static final String PRESENTER_SCOPE = "PresenterScope";
    private static final String BUILDER = "Builder";
    private static final String MODULE = "Module";
    private static final String SUBCOMPONENTS = "subcomponents";
    private static final String BINDS = "Binds";
    private static final String DAGGER_MULTIBINDINGS = "dagger.multibindings";
    private static final String INTO_MAP = "IntoMap";
    private static final String BIND_PRESENTER_BUILDER = "bindPresenterBuilder";
    private static final String PRESENTER_KEY = "PresenterKey";
    private static final String VALUE = "value";
    private static final String T_CLASS = "$T.class";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements =
                roundEnv.getElementsAnnotatedWith(ContributesPresenterInjector.class);
        if (annotatedElements.isEmpty()) {
            return true;
        }

        for (Element type: annotatedElements) {
            if (type.getKind() != ElementKind.METHOD) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                        String.format("%s is not a method", type.getSimpleName().toString()));
                continue;
            }
            MethodInfo info = new MethodInfo((ExecutableElement) type);
            if (!info.isValid()) {
                processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                        String.format("Method %s is not valid.", info.getName()));
                continue;
            }

            TypeElement annotationType = processingEnv.getElementUtils()
                    .getTypeElement(ContributesPresenterInjector.class.getName());
            AnnotationValue valueModules = null;
            AnnotationValue valueIsChild = null;
            DeclaredType[] modules;
            boolean isChild = false;
            for (AnnotationMirror annotationMirror: type.getAnnotationMirrors()) {
                if (annotationMirror.getAnnotationType().asElement().getSimpleName()
                        .equals(annotationType.getSimpleName())) {
                    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                            :annotationMirror.getElementValues().entrySet()) {
                        if (entry.getKey().getSimpleName().toString().equals("modules")) {
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
                continue;
            }

            @SuppressWarnings("unchecked")
            List<AnnotationValue> list = (List<AnnotationValue>) valueModules.getValue();
            modules = new DeclaredType[list.size()];
            for (int i = 0; i < modules.length; i++) {
                DeclaredType declaredType = (DeclaredType) list.get(i).getValue();
                modules[i] = declaredType;
            }
            if (valueIsChild != null) {
                isChild = (boolean) valueIsChild.getValue();
            }
            if (modules.length == 0) {
                continue;
            }
            processModules(info, modules, isChild);
        }

        return true;
    }

    private void processModules(MethodInfo info, DeclaredType[] modules, boolean isChild) {
        ClassName providerPresenterComponentClass =
                ClassName.get(COM_A65APPS_DAGGERARCHITECTURECOMPONENTS_PRESENTER,
                        PROVIDER_PRESENTER_COMPONENT);
        ClassName presenterComponentBuilder =
                ClassName.get(COM_A65APPS_DAGGERARCHITECTURECOMPONENTS_PRESENTER,
                        PRESENTER_COMPONENT_BUILDER);
        List<TypeName> names = getComponentTypeNames(info);
        names.add(info.getReturnType());

        ParameterizedTypeName baseProvider = ParameterizedTypeName.get(providerPresenterComponentClass,
                names.toArray(new TypeName[names.size()]));
        String className = String.format(S_BIND_S, info.getParentName(),
                info.getReturnTypeName());
        String subcomponentName = String.format(S_SUBCOMPONENT, info.getReturnTypeName());

        TypeSpec.Builder subcomponentBuilder = TypeSpec.interfaceBuilder(subcomponentName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        AnnotationSpec.Builder annotationBuilder = AnnotationSpec
                .builder(ClassName.get(DAGGER, SUBCOMPONENT));
        StringBuilder format = new StringBuilder("{");
        TypeElement annotationType = processingEnv.getElementUtils()
                .getTypeElement(ContributesPresenterInjector.class.getName());
        List<ClassName> resultModules = new ArrayList<>();
        fillTypesData(modules, format, annotationType, resultModules);
        annotationBuilder.addMember(MODULES, format.toString(),
                resultModules.toArray(new Object[resultModules.size()]));
        if (isChild) {
            subcomponentBuilder.addAnnotation(ClassName.get(COM_A65APPS_DAGGERARCHITECTURECOMPONENTS_PRESENTER,
                    CHILD_PRESENTER_SCOPE));
        } else {
            subcomponentBuilder.addAnnotation(ClassName.get(COM_A65APPS_DAGGERARCHITECTURECOMPONENTS_PRESENTER,
                    PRESENTER_SCOPE));
        }
        subcomponentBuilder.addAnnotation(annotationBuilder.build());
        subcomponentBuilder.addSuperinterface(baseProvider);

        ParameterizedTypeName baseBuilder = ParameterizedTypeName.get(presenterComponentBuilder,
                ClassName.get("", subcomponentName));
        subcomponentBuilder.addType(TypeSpec.interfaceBuilder(BUILDER)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addAnnotation(ClassName.get(DAGGER, SUBCOMPONENT,
                        BUILDER))
                .addSuperinterface(baseBuilder)
                .build());

        TypeSpec module = TypeSpec.interfaceBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(ClassName.get(DAGGER,
                        MODULE))
                        .addMember(SUBCOMPONENTS, T_CLASS,
                                ClassName.get("", className, subcomponentName))
                        .build())
                .addMethod(MethodSpec.methodBuilder(BIND_PRESENTER_BUILDER)
                        .addModifiers(Modifier.PUBLIC, Modifier.ABSTRACT)
                        .addAnnotation(ClassName.get(DAGGER, BINDS))
                        .addAnnotation(ClassName.get(DAGGER_MULTIBINDINGS, INTO_MAP))
                        .addAnnotation(AnnotationSpec.builder(
                                ClassName.get(COM_A65APPS_DAGGERARCHITECTURECOMPONENTS_PRESENTER,
                                        PRESENTER_KEY))
                                .addMember(VALUE, T_CLASS, info.getReturnType())
                                .build())
                        .addParameter(ParameterSpec.builder(ClassName.get("",
                                subcomponentName, BUILDER),
                                BUILDER.toLowerCase(Locale.getDefault()))
                                .build())
                        .returns(presenterComponentBuilder)
                        .build())
                .addType(subcomponentBuilder.build())
                .build();

        try {
            JavaFile classFile = JavaFile.builder(info.getParentPackageName(), module)
                    .build();
            classFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    e.getMessage());
        }
    }

    private List<TypeName> getComponentTypeNames(MethodInfo info) {
        List<? extends TypeParameterElement> params = info.getReturnTypeGenerics();
        List<TypeElement> paramsArgs = info.getReturnArgumentsGenerics();
        List<TypeName> names = new ArrayList<>();
        ClassName cs = null;
        for (int i = 0; i < params.size(); i++) {
            TypeParameterElement element = params.get(i);
            switch (element.getSimpleName().toString()) {
                case "CS":
                    TypeElement typeElement = paramsArgs.get(i);
                    cs = ClassName.get(typeElement);
                    break;
                case "S":
                case "V":
                case "I":
                case "R":
                    TypeElement param = paramsArgs.get(i);
                    List<? extends TypeMirror> args = ((DeclaredType) param.asType()).getTypeArguments();
                    if (!args.isEmpty() && element.getSimpleName().toString().equals("V")) {
                        if (cs != null) {
                            names.add(ParameterizedTypeName.get(ClassName.get(param),
                                    names.get(0), cs));
                        } else {
                            names.add(ParameterizedTypeName.get(ClassName.get(param), names.get(0)));
                        }
                    } else {
                        names.add(ClassName.get(param));
                    }
                    break;
                default:
                    break;
            }
        }
        return names;
    }

    private void fillTypesData(DeclaredType[] modules, StringBuilder format, TypeElement annotationType,
                               List<ClassName> resultModules) {
        for (int i = 0; i < modules.length; i++) {
            boolean isSkip = false;
            for (Element element: modules[i].asElement().getEnclosedElements()) {
                if (element.getKind() == ElementKind.METHOD) {
                    MethodInfo childInfo = new MethodInfo((ExecutableElement) element);
                    for (AnnotationMirror annotationMirror: element.getAnnotationMirrors()) {
                        if (annotationMirror.getAnnotationType().asElement().getSimpleName()
                                .equals(annotationType.getSimpleName())) {
                            String childClassName = String.format(S_BIND_S,
                                    childInfo.getParentName(),
                                    childInfo.getReturnTypeName());
                            isSkip = true;
                            AnnotationValue valueModules = null;
                            for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                                    :annotationMirror.getElementValues().entrySet()) {
                                if (entry.getKey().getSimpleName().toString().equals(MODULES)) {
                                    valueModules = entry.getValue();
                                    break;
                                }
                            }
                            if (valueModules != null) {
                                @SuppressWarnings("unchecked")
                                List<AnnotationValue> list = (List<AnnotationValue>) valueModules.getValue();
                                for (int j = 0; j < list.size(); j++) {
                                    resultModules.add(ClassName.get(childInfo.getParentPackageName(),
                                            childClassName));
                                    format.append(T_CLASS);
                                    if (j < list.size()) {
                                        format.append(", ");
                                    }
                                }
                            }
                        }
                    }
                }
            }
            if (!isSkip) {
                resultModules.add(ClassName.get((TypeElement) modules[i].asElement()));
                format.append(T_CLASS);
                if (i + 1 < modules.length) {
                    format.append(", ");
                }
            }
        }
        format.append('}');
    }
}
