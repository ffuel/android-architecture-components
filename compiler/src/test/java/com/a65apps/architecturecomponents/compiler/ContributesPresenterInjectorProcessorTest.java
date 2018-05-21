package com.a65apps.architecturecomponents.compiler;

import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;

import org.junit.Test;

import java.util.Arrays;

import javax.tools.JavaFileObject;

public class ContributesPresenterInjectorProcessorTest {

    @Test
    public void moduleWithOneMethodAndNoChildDependency() {
        final JavaFileObject input = JavaFileObjects.forResource("ComponentInput.java");
        final JavaFileObject output = JavaFileObjects.forResource("ComponentOutput.java");

        testProcessor(input, output);
    }

    @Test
    public void moduleWithOneMethodAndChildDependency() {
        final JavaFileObject input = JavaFileObjects.forResource("ComponentsInput.java");
        final JavaFileObject main = JavaFileObjects.forResource("MainOutput.java");
        final JavaFileObject inputChild = JavaFileObjects
                .forSourceString("com.a65apps.architecturecomponents.sample.presentation.main.ChildPresenterComponent",
                        "package com.a65apps.architecturecomponents.sample.presentation.main;\n" +
                                "\n" +
                                "import com.a65apps.architecturecomponents.compiler.annotation.ContributesPresenterInjector;\n" +
                                "import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsPresenter;\n" +
                                "import com.a65apps.architecturecomponents.sample.presentation.contacts.ContactsPresenterModule;\n" +
                                "import com.a65apps.architecturecomponents.sample.presentation.sample.SamplePresenter;\n" +
                                "import com.a65apps.architecturecomponents.sample.presentation.sample.SamplePresenterModule;\n" +
                                "\n" +
                                "import dagger.Module;\n" +
                                "\n" +
                                "@Module\n" +
                                "public interface ChildPresenterComponent {\n" +
                                "\n" +
                                "    @ContributesPresenterInjector(modules = SamplePresenterModule.class, isChild = true)\n" +
                                "    SamplePresenter bindSamplePresenter();\n" +
                                "\n" +
                                "    @ContributesPresenterInjector(modules = ContactsPresenterModule.class, isChild = true)\n" +
                                "    ContactsPresenter bindContactsPresenter();\n" +
                                "}\n");
        final JavaFileObject sample = JavaFileObjects.forResource("SampleOutput.java");
        final JavaFileObject contacts = JavaFileObjects.forResource("ContactsOutput.java");

        testProcessor(input, main);
        testProcessor(inputChild, sample, contacts);
    }

    private void testProcessor(JavaFileObject input, JavaFileObject output, JavaFileObject... rest) {
        Truth.assert_()
                .about(JavaSourcesSubjectFactory.javaSources())
                .that(Arrays.asList(input))
                .processedWith(new ContributesPresenterInjectorProcessor())
                .compilesWithoutError()
                .and()
                .generatesSources(output, rest);
    }
}
