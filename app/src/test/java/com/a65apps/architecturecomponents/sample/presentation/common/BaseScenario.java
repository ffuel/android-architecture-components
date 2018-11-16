package com.a65apps.architecturecomponents.sample.presentation.common;

import com.a65apps.architecturecomponents.sample.TestsApp;
import com.google.common.reflect.TypeToken;
import com.tngtech.jgiven.impl.Scenario;
import com.tngtech.jgiven.junit.JGivenMethodRule;

import org.junit.Rule;
import org.robolectric.annotation.Config;

@Config(application = TestsApp.class)
public class BaseScenario<GIVEN extends BaseGivenState<GIVEN>,
        WHEN extends BaseWhenState<WHEN>,
        THEN extends BaseThenState<THEN>> {

    @Rule
    public final JGivenWriterRule writerRule = new JGivenWriterRule();

    @Rule
    public final JGivenMethodRule scenarioRule = new JGivenMethodRule(createScenario());

    @SuppressWarnings("unchecked")
    public Scenario<GIVEN, WHEN, THEN> getScenario() {
        return (Scenario<GIVEN, WHEN, THEN>) scenarioRule.getScenario();
    }

    public GIVEN given() {
        return getScenario().given();
    }

    public WHEN when() {
        return getScenario().when();
    }

    public THEN then() {
        return getScenario().then();
    }

    @SuppressWarnings( { "serial", "unchecked" } )
    private Scenario<GIVEN, WHEN, THEN> createScenario() {
        Class<GIVEN> givenClass = (Class<GIVEN>) new TypeToken<GIVEN>( getClass() ) {}.getRawType();
        Class<WHEN> whenClass = (Class<WHEN>) new TypeToken<WHEN>( getClass() ) {}.getRawType();
        Class<THEN> thenClass = (Class<THEN>) new TypeToken<THEN>( getClass() ) {}.getRawType();

        return new Scenario<>( givenClass, whenClass, thenClass );
    }
}
