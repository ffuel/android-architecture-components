package com.a65apps.ciceronearchitecturecomponents.commands;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import ru.terrakok.cicerone.commands.Command;

public class ContextMessage implements Command {
    @NonNull
    private String message;
    @IdRes
    private int anchor;

    public ContextMessage(@NonNull String message, int anchor) {
        this.message = message;
        this.anchor = anchor;
    }

    @NonNull
    public String getMessage() {
        return message;
    }

    public int getAnchor() {
        return anchor;
    }
}
