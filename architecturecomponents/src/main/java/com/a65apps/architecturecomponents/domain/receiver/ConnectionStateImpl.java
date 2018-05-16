

package com.a65apps.architecturecomponents.domain.receiver;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.jcip.annotations.ThreadSafe;

@ThreadSafe
final class ConnectionStateImpl extends ConnectionState {

    private static final int HASH_NUMBER = 1000003;
    private static final int HASH_BOOLEAN_FALSE = 1237;
    private static final int HASH_BOOLEAN_TRUE = 1231;

    private final int type;
    private final int subtype;
    private final String typeName;
    private final String subtypeName;
    private final ConnectionState.State state;
    private final ConnectionState.DetailedState detailedState;
    private final String reason;
    private final String extraInfo;
    private final boolean isFailover;
    private final boolean isAvailable;
    private final boolean isRoaming;

    @NonNull
    static ConnectionState create(@NonNull Builder builder) {
        return new ConnectionStateImpl(builder);
    }

    private ConnectionStateImpl(@NonNull Builder builder) {
        this.type = builder.type;
        this.subtype = builder.subtype;
        this.typeName = builder.typeName;
        this.subtypeName = builder.subtypeName;
        this.state = builder.state;
        this.detailedState = builder.detailedState;
        this.reason = builder.reason;
        this.extraInfo = builder.extraInfo;
        this.isFailover = builder.isFailover;
        this.isAvailable = builder.isAvailable;
        this.isRoaming = builder.isRoaming;
    }

    @Override
    public int type() {
        return type;
    }

    @Override
    public int subtype() {
        return subtype;
    }

    @NonNull
    @Override
    public String typeName() {
        return typeName;
    }

    @NonNull
    @Override
    public String subtypeName() {
        return subtypeName;
    }

    @NonNull
    @Override
    public ConnectionState.State state() {
        return state;
    }

    @NonNull
    @Override
    public ConnectionState.DetailedState detailedState() {
        return detailedState;
    }

    @NonNull
    @Override
    public String reason() {
        return reason;
    }

    @NonNull
    @Override
    public String extraInfo() {
        return extraInfo;
    }

    @Override
    public boolean isFailover() {
        return isFailover;
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public boolean isRoaming() {
        return isRoaming;
    }

    @Override
    public String toString() {
        return "ConnectionState{"
                + "type=" + type + ", "
                + "subtype=" + subtype + ", "
                + "typeName=" + typeName + ", "
                + "subtypeName=" + subtypeName + ", "
                + "state=" + state + ", "
                + "detailedState=" + detailedState + ", "
                + "reason=" + reason + ", "
                + "extraInfo=" + extraInfo + ", "
                + "isFailover=" + isFailover + ", "
                + "isAvailable=" + isAvailable + ", "
                + "isRoaming=" + isRoaming
                + "}";
    }

    @Override
    public boolean equals(@Nullable Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof ConnectionState) {
            ConnectionState that = (ConnectionState) o;
            return this.type == that.type()
                    && this.subtype == that.subtype()
                    && this.typeName.equals(that.typeName())
                    && this.subtypeName.equals(that.subtypeName())
                    && this.state.equals(that.state())
                    && this.detailedState.equals(that.detailedState())
                    && this.reason.equals(that.reason())
                    && this.extraInfo.equals(that.extraInfo())
                    && this.isFailover == that.isFailover()
                    && this.isAvailable == that.isAvailable()
                    && this.isRoaming == that.isRoaming();
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        hash *= HASH_NUMBER;
        hash ^= type;
        hash *= HASH_NUMBER;
        hash ^= subtype;
        hash *= HASH_NUMBER;
        hash ^= typeName.hashCode();
        hash *= HASH_NUMBER;
        hash ^= subtypeName.hashCode();
        hash *= HASH_NUMBER;
        hash ^= state.hashCode();
        hash *= HASH_NUMBER;
        hash ^= detailedState.hashCode();
        hash *= HASH_NUMBER;
        hash ^= reason.hashCode();
        hash *= HASH_NUMBER;
        hash ^= extraInfo.hashCode();
        hash *= HASH_NUMBER;
        hash ^= isFailover ? HASH_BOOLEAN_TRUE : HASH_BOOLEAN_FALSE;
        hash *= HASH_NUMBER;
        hash ^= isAvailable ? HASH_BOOLEAN_TRUE : HASH_BOOLEAN_FALSE;
        hash *= HASH_NUMBER;
        hash ^= isRoaming ? HASH_BOOLEAN_TRUE : HASH_BOOLEAN_FALSE;
        return hash;
    }

    static final class Builder extends ConnectionState.Builder {
        private Integer type;
        private Integer subtype;
        private String typeName;
        private String subtypeName;
        private ConnectionState.State state;
        private ConnectionState.DetailedState detailedState;
        private String reason;
        private String extraInfo;
        private Boolean isFailover;
        private Boolean isAvailable;
        private Boolean isRoaming;

        Builder() {
//           empty constructor
        }

        @Override
        public ConnectionState.Builder type(int type) {
            this.type = type;
            return this;
        }

        @Override
        public ConnectionState.Builder subtype(int subtype) {
            this.subtype = subtype;
            return this;
        }

        @Override
        public ConnectionState.Builder typeName(@NonNull String typeName) {
            this.typeName = typeName;
            return this;
        }

        @Override
        public ConnectionState.Builder subtypeName(@NonNull String subtypeName) {
            this.subtypeName = subtypeName;
            return this;
        }

        @Override
        public ConnectionState.Builder state(@NonNull ConnectionState.State state) {
            this.state = state;
            return this;
        }

        @Override
        public ConnectionState.Builder detailedState(
                @NonNull ConnectionState.DetailedState detailedState) {
            this.detailedState = detailedState;
            return this;
        }

        @Override
        public ConnectionState.Builder reason(@NonNull String reason) {
            this.reason = reason;
            return this;
        }

        @Override
        public ConnectionState.Builder extraInfo(@NonNull String extraInfo) {
            this.extraInfo = extraInfo;
            return this;
        }

        @Override
        public ConnectionState.Builder isFailover(boolean isFailover) {
            this.isFailover = isFailover;
            return this;
        }

        @Override
        public ConnectionState.Builder isAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        @Override
        public ConnectionState.Builder isRoaming(boolean isRoaming) {
            this.isRoaming = isRoaming;
            return this;
        }

        @Override
        public ConnectionState build() {
            String missing = "";
            if (this.type == null) {
                missing += " type";
            }
            if (this.subtype == null) {
                missing += " subtype";
            }
            if (this.typeName == null) {
                missing += " typeName";
            }
            if (this.subtypeName == null) {
                missing += " subtypeName";
            }
            if (this.state == null) {
                missing += " state";
            }
            if (this.detailedState == null) {
                missing += " detailedState";
            }
            if (this.reason == null) {
                missing += " reason";
            }
            if (this.extraInfo == null) {
                missing += " extraInfo";
            }
            if (this.isFailover == null) {
                missing += " isFailover";
            }
            if (this.isAvailable == null) {
                missing += " isAvailable";
            }
            if (this.isRoaming == null) {
                missing += " isRoaming";
            }
            if (!missing.isEmpty()) {
                throw new IllegalStateException("Missing required properties:" + missing);
            }
            return create(this);
        }
    }

}
