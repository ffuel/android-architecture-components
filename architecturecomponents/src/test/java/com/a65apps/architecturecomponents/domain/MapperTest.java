package com.a65apps.architecturecomponents.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MapperTest {

    @Mock
    private State state1;
    @Mock
    private State state2;

    @Mock(answer = Answers.CALLS_REAL_METHODS)
    private Mapper<State, State> mapper;

    @Test
    public void mapTest() {
        when(mapper.map(eq(state1))).thenReturn(state1);
        when(mapper.map(eq(state2))).thenReturn(state2);
        List<State> from = new ArrayList<>();
        from.add(state1);
        from.add(state2);

        List<State> result = mapper.map(from);

        assertNotNull(result);
        assertThat(result.size(), equalTo(2));
        for (State value: result) {
            assertNotNull(value);
        }
    }

    @Test(expected = NullPointerException.class)
    public void mapWithNullTest() {
        when(mapper.map(eq(state1))).thenReturn(state1);
        when(mapper.map(eq(state2))).thenThrow(NullPointerException.class);
        List<State> from = new ArrayList<>();
        from.add(state1);
        from.add(state2);

        mapper.map(from);
    }
}
