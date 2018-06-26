package com.a65apps.architecturecomponents.compiler;

import com.a65apps.architecturecomponents.compiler.annotation.ContributesPresenterInjector;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;

@AutoService(Processor.class)
@SupportedAnnotationTypes({"com.a65apps.architecturecomponents.compiler.annotation.ContributesPresenterInjector"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ContributesPresenterInjectorProcessor extends AbstractProcessor {

    private static final List<String> TYPES;

    static {
        TYPES = new ArrayList<>();
        TYPES.add("S");
        TYPES.add("V");
        TYPES.add("I");
        TYPES.add("R");
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> annotatedElements =
                roundEnv.getElementsAnnotatedWith(ContributesPresenterInjector.class);
        if (!annotatedElements.isEmpty()) {
            for (Element type : annotatedElements) {
                MethodInfo info = new MethodInfo((ExecutableElement) type);
                AnnotationInfo annotationInfo = AnnotationInfo.computeInfo(processingEnv, type,
                        ContributesPresenterInjector.class);
                if (!validateType(type, info, annotationInfo)) {
                    continue;
                }

                processModules(info, annotationInfo.getModules(), annotationInfo.isChild());
            }
        }

        return true;
    }

    private boolean validateType(@Nonnull Element type, @Nonnull MethodInfo methodInfo,
                                 @Nonnull AnnotationInfo annotationInfo) {
        if (type.getKind() != ElementKind.METHOD) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,
                    String.format("%s is not a method", type.getSimpleName().toString()));
            return false;
        }
        if (!methodInfo.isValid()) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
                    String.format("Method %s is not valid.", methodInfo.getName()));
            return false;
        }

        return annotationInfo.isValid();
    }

    private void processModules(@Nonnull MethodInfo info, @Nonnull DeclaredType[] modules, boolean isChild) {
        ClassName providerPresenterComponentClass =
                ClassName.get(Const.COM_A65APPS_DAGGERARCHITECTURECOMPONENTS_PRESENTER,
                        Const.PROVIDER_PRESENTER_COMPONENT);
        ClassName presenterComponentBuilder =
                ClassName.get(Const.COM_A65APPS_DAGGERARCHITECTURECOMPONENTS_PRESENTER,
                        Const.PRESENTER_COMPONENT_BUILDER);
        List<TypeName> names = getComponentTypeNames(info);
        names.add(info.getReturnType());

        ParameterizedTypeName baseProvider = ParameterizedTypeName.get(providerPresenterComponentClass,
                names.toArray(new TypeName[names.size()]));
        String className = String.format(Const.S_BIND_S, info.getParentName(),
                info.getReturnTypeName());
        String subComponentName = String.format(Const.S_SUBCOMPONENT, info.getReturnTypeName());

        TypeSpec subComponent = new SubComponentBuilder(
                processingEnv, subComponentName, modules, baseProvider, presenterComponentBuilder, isChild)
                .construct();

        TypeSpec module = new ModuleTypeSpecBuilder(className, subComponentName, info,
                presenterComponentBuilder, subComponent)
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

    @Nonnull
    private List<TypeName> getComponentTypeNames(@Nonnull MethodInfo info) {
        List<? extends TypeParameterElement> params = info.getReturnTypeGenerics();
        List<TypeElement> paramsArgs = info.getReturnArgumentsGenerics();
        List<TypeName> names = new ArrayList<>();

        ClassName cs = getCompositeStateClassName(params, paramsArgs);
        ClassName routerName = getRouterClassName(params, paramsArgs);
        for (int i = 0; i < params.size(); i++) {
            TypeElement param = paramsArgs.get(i);
            if (i == 0) {
                names.add(ClassName.get(param));
                continue;
            }

            TypeParameterElement element = params.get(i);
            List<? extends TypeMirror> args = ((DeclaredType) param.asType()).getTypeArguments();
            List<TypeProcessor> processors = generateTypeProcessors(cs, routerName, param, names.get(0));
            if (!isTypeFor(element)) {
                continue;
            }
            if (!args.isEmpty()) {
                for (TypeProcessor processor: processors) {
                    if (processor.isFor(element)) {
                        names.add(processor.process());
                        break;
                    }
                }
            } else {
                names.add(ClassName.get(param));
            }
        }

        return names;
    }

    private boolean isTypeFor(@Nonnull TypeParameterElement element) {
        String name = element.getSimpleName().toString();
        for (String type: TYPES) {
            if (type.equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    private ClassName getCompositeStateClassName(@Nonnull List<? extends TypeParameterElement> params,
                                                 @Nonnull List<TypeElement> paramsArgs) {
        for (int i = 0; i < params.size(); i++) {
            TypeParameterElement element = params.get(i);
            if (element.getSimpleName().toString().equals("CS")) {
                TypeElement typeElement = paramsArgs.get(i);
                return ClassName.get(typeElement);
            }
        }

        return null;
    }

    @Nullable
    private ClassName getRouterClassName(@Nonnull List<? extends TypeParameterElement> params,
                                       @Nonnull List<TypeElement> paramsArgs) {
        for (int i = 0; i < params.size(); i++) {
            TypeParameterElement arg = params.get(i);
            if (arg.getSimpleName().toString().equals("R")) {
                TypeElement routerArg = paramsArgs.get(i);
                return ClassName.get(routerArg);
            }
        }

        return null;
    }

    @Nonnull
    private List<TypeProcessor> generateTypeProcessors(@Nullable ClassName compositeState,
                                                       @Nullable ClassName router,
                                                       @Nonnull TypeElement param,
                                                       @Nonnull TypeName name) {
        List<TypeProcessor> processors = new ArrayList<>();
        processors.add(new ViewTypeProcessor(compositeState, router, param, name));
        processors.add(new InteractorTypeProcessor(compositeState, router, param, name));
        return Collections.unmodifiableList(processors);
    }
}
