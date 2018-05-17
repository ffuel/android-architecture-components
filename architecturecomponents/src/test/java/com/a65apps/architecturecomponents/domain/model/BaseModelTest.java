package com.a65apps.architecturecomponents.domain.model;

import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BaseModelTest {

    @Mock
    private State state;
    @Mock
    private Router router;

    @Test
    public void testDefaultState() {
        BaseModel<State, Router> model = new BaseModel<State, Router>(state, router) {};

        model.observeState().subscribe(state -> assertThat(state, equalTo(this.state)));
    }

    @Test
    public void testSetState() {
        BaseModel<State, Router> model = new BaseModel<State, Router>(state, router) {};
        final State newState = new State() {};

        model.setState(newState);
        model.observeState().subscribe(state -> assertThat(state, equalTo(newState)));
    }

    @Test
    public void testGetState() {
        BaseModel<State, Router> model = new BaseModel<State, Router>(state, router) {};
        final State newState = new State() {};

        assertThat(model.getState(), equalTo(state));
        model.setState(newState);
        assertThat(model.getState(), equalTo(newState));
    }
}
