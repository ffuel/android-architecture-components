package com.a65aps.architecturecomponents.domain.receiver;

import android.support.annotation.NonNull;

import com.a65aps.architecturecomponents.domain.State;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public abstract class ConnectionState implements State {

    public abstract int type();

    public abstract int subtype();

    @NonNull
    public abstract String typeName();

    @NonNull
    public abstract String subtypeName();

    @NonNull
    public abstract State state();

    @NonNull
    public abstract DetailedState detailedState();

    @NonNull
    public abstract String reason();

    @NonNull
    public abstract String extraInfo();

    public abstract boolean isFailover();

    public abstract boolean isAvailable();

    public abstract boolean isRoaming();

    public boolean isConnected() {
        return state() == State.CONNECTED;
    }

    public static Builder builder() {
        return new ConnectionStateImpl.Builder();
    }

    /**
     * Coarse-grained network state. This is probably what most applications should
     * use, rather than {@link DetailedState DetailedState}.
     * The mapping between the two is as follows:
     * <br/><br/>
     * <table>
     * <tr><td><b>Detailed state</b></td><td><b>Coarse-grained state</b></td></tr>
     * <tr><td><code>IDLE</code></td><td><code>DISCONNECTED</code></td></tr>
     * <tr><td><code>SCANNING</code></td><td><code>CONNECTING</code></td></tr>
     * <tr><td><code>CONNECTING</code></td><td><code>CONNECTING</code></td></tr>
     * <tr><td><code>AUTHENTICATING</code></td><td><code>CONNECTING</code></td></tr>
     * <tr><td><code>CONNECTED</code></td><td><code>CONNECTED</code></td></tr>
     * <tr><td><code>DISCONNECTING</code></td><td><code>DISCONNECTING</code></td></tr>
     * <tr><td><code>DISCONNECTED</code></td><td><code>DISCONNECTED</code></td></tr>
     * <tr><td><code>UNAVAILABLE</code></td><td><code>DISCONNECTED</code></td></tr>
     * <tr><td><code>FAILED</code></td><td><code>DISCONNECTED</code></td></tr>
     * </table>
     */
    public enum State {
        CONNECTING, CONNECTED, SUSPENDED, DISCONNECTING, DISCONNECTED, UNKNOWN
    }

    public enum DetailedState {
        /** Ready to start data connection setup. */
        IDLE,
        /** Searching for an available access point. */
        SCANNING,
        /** Currently setting up data connection. */
        CONNECTING,
        /** Network link established, performing authentication. */
        AUTHENTICATING,
        /** Awaiting response from DHCP server in order to assign IP address information. */
        OBTAINING_IPADDR,
        /** IP traffic should be available. */
        CONNECTED,
        /** IP traffic is suspended */
        SUSPENDED,
        /** Currently tearing down data connection. */
        DISCONNECTING,
        /** IP traffic not available. */
        DISCONNECTED,
        /** Attempt to connect failed. */
        FAILED,
        /** Access to this network is blocked. */
        BLOCKED,
        /** Link has poor connectivity. */
        VERIFYING_POOR_LINK,
        /** Checking if network is a captive portal */
        CAPTIVE_PORTAL_CHECK
    }

    public abstract static class Builder {
        public abstract Builder type(int type);

        public abstract Builder subtype(int subtype);

        public abstract Builder typeName(String typeName);

        public abstract Builder subtypeName(String subtypeName);

        public abstract Builder state(State state);

        public abstract Builder detailedState(DetailedState detailedState);

        public abstract Builder reason(String reason);

        public abstract Builder extraInfo(String extraInfo);

        public abstract Builder isFailover(boolean isFailover);

        public abstract Builder isAvailable(boolean isAvailable);

        public abstract Builder isRoaming(boolean isRoaming);

        public abstract ConnectionState build();
    }
}
