package com.a65apps.architecturecomponents.domain.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.a65apps.architecturecomponents.domain.Interactor;
import com.a65apps.architecturecomponents.domain.State;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import java.util.Collection;

public interface IntentInteractor<S extends State, R extends Router> extends Interactor<S, R> {

    void execute(@NonNull String type);

    void execute(@NonNull String type, @Nullable Object data);

    void execute(@NonNull Collection<Command> collection);

    /**
     * Supports only SingleIntent
     *
     * This method executes intents ordered
     *
     * @param collection of commands to execute
     */
    void chainExecute(@NonNull Collection<Command> collection);

    void dispose(@NonNull String type);

    final class Command {

        private static final int HASH_NUMBER = 31;

        @NonNull
        private final String type;
        @Nullable
        private final Object data;

        @NonNull
        public static Command create(@NonNull String type) {
            return new Command(type, null);
        }

        @NonNull
        public static Command create(@NonNull String type, @Nullable Object data) {
            return new Command(type, data);
        }

        private Command(@NonNull String type, @Nullable Object data) {
            this.type = type;
            this.data = data;
        }

        @NonNull
        public String getType() {
            return type;
        }

        @Nullable
        public Object getData() {
            return data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            Command command = (Command) o;

            if (!type.equals(command.type)) {
                return false;
            }
            return data != null ? data.equals(command.data) : command.data == null;
        }

        @Override
        public int hashCode() {
            int result = type.hashCode();
            result = HASH_NUMBER * result + (data != null ? data.hashCode() : 0);
            return result;
        }
    }
}
