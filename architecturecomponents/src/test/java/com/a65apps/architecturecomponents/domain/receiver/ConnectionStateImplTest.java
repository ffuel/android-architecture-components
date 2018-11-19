package com.a65apps.architecturecomponents.domain.receiver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionStateImplTest {
    private final ConnectionState a = ConnectionState.builder()
            .type(0)
            .subtype(0)
            .typeName("a")
            .subtypeName("a")
            .state(ConnectionState.State.CONNECTED)
            .detailedState(ConnectionState.DetailedState.CONNECTED)
            .reason("")
            .extraInfo("")
            .isFailover(false)
            .isAvailable(true)
            .isRoaming(false)
            .build();
    private final ConnectionState b = ConnectionState.builder()
            .type(0)
            .subtype(0)
            .typeName("a")
            .subtypeName("a")
            .state(ConnectionState.State.CONNECTED)
            .detailedState(ConnectionState.DetailedState.CONNECTED)
            .reason("")
            .extraInfo("")
            .isFailover(false)
            .isAvailable(true)
            .isRoaming(false)
            .build();
    private final ConnectionState c = ConnectionState.builder()
            .type(1)
            .subtype(1)
            .typeName("c")
            .subtypeName("c")
            .state(ConnectionState.State.DISCONNECTED)
            .detailedState(ConnectionState.DetailedState.DISCONNECTING)
            .reason("")
            .extraInfo("")
            .isFailover(false)
            .isAvailable(false)
            .isRoaming(false)
            .build();
    private final ConnectionState d = ConnectionState.builder()
            .type(0)
            .subtype(0)
            .typeName("a")
            .subtypeName("a")
            .state(ConnectionState.State.CONNECTED)
            .detailedState(ConnectionState.DetailedState.CONNECTED)
            .reason("")
            .extraInfo("")
            .isFailover(false)
            .isAvailable(true)
            .isRoaming(false)
            .build();


    @Test
    public void equalsTest() {
        assertThat(a.equals(b), equalTo(true));
        assertThat(b.equals(a), equalTo(true));
        assertThat(b.equals(d), equalTo(true));
        assertThat(a.equals(d), equalTo(true));
        assertThat(c.equals(a), equalTo(false));
        assertThat(c.equals(b), equalTo(false));

        assertThat(a.equals(b), equalTo(true));
        assertThat(a.equals(b), equalTo(true));
    }

    @Test
    public void hashCodeTest() {
        assertThat(a.hashCode() == b.hashCode(), equalTo(true));
        assertThat(b.hashCode() == a.hashCode(), equalTo(true));
        assertThat(b.hashCode() == d.hashCode(), equalTo(true));
        assertThat(a.hashCode() == d.hashCode(), equalTo(true));
        assertThat(c.hashCode() == a.hashCode(), equalTo(false));
        assertThat(c.hashCode() == b.hashCode(), equalTo(false));

        assertThat(a.hashCode() == b.hashCode(), equalTo(true));
        assertThat(a.hashCode() == b.hashCode(), equalTo(true));
    }

    @Test
    public void toStringTest() {
        assertThat(a.toString(), equalTo("ConnectionState{"
                + "type=" + 0 + ", "
                + "subtype=" + 0 + ", "
                + "typeName=a, "
                + "subtypeName=a, "
                + "state=" + ConnectionState.State.CONNECTED + ", "
                + "detailedState=" + ConnectionState.DetailedState.CONNECTED + ", "
                + "reason=, "
                + "extraInfo=, "
                + "isFailover=" + false + ", "
                + "isAvailable=" + true + ", "
                + "isRoaming=" + false
                + "}"));
        assertThat(b.toString(), equalTo("ConnectionState{"
                + "type=" + 0 + ", "
                + "subtype=" + 0 + ", "
                + "typeName=a, "
                + "subtypeName=a, "
                + "state=" + ConnectionState.State.CONNECTED + ", "
                + "detailedState=" + ConnectionState.DetailedState.CONNECTED + ", "
                + "reason=, "
                + "extraInfo=, "
                + "isFailover=" + false + ", "
                + "isAvailable=" + true + ", "
                + "isRoaming=" + false
                + "}"));
        assertThat(c.toString(), equalTo("ConnectionState{"
                + "type=" + 1 + ", "
                + "subtype=" + 1 + ", "
                + "typeName=c, "
                + "subtypeName=c, "
                + "state=" + ConnectionState.State.DISCONNECTED + ", "
                + "detailedState=" + ConnectionState.DetailedState.DISCONNECTING + ", "
                + "reason=, "
                + "extraInfo=, "
                + "isFailover=" + false + ", "
                + "isAvailable=" + false + ", "
                + "isRoaming=" + false
                + "}"));
    }
}
