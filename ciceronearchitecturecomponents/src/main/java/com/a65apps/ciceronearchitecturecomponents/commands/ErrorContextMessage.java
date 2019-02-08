package com.a65apps.ciceronearchitecturecomponents.commands;

import android.support.annotation.NonNull;

public class ErrorContextMessage extends ContextMessage {
    public ErrorContextMessage(@NonNull String message, int anchor) {
        super(message, anchor);
    }
}
