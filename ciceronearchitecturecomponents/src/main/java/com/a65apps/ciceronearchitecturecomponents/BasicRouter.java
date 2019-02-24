package com.a65apps.ciceronearchitecturecomponents;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;

import com.a65apps.ciceronearchitecturecomponents.commands.ErrorContextMessage;
import com.a65apps.ciceronearchitecturecomponents.commands.NeutralContextMessage;
import com.a65apps.ciceronearchitecturecomponents.commands.PositiveContextMessage;

import ru.terrakok.cicerone.Router;

public class BasicRouter extends Router {

    BasicRouter() {
        super();
    }

    /**
     * Show negative context message.
     *
     * @param message message to show
     * @param anchor  anchorView
     */
    public void showErrorContextMessage(@NonNull String message, @IdRes int anchor) {
        new Handler(Looper.getMainLooper())
                .post(() -> executeCommands(new ErrorContextMessage(message, anchor)));
    }

    /**
     * Show positive context message.
     *
     * @param message message to show
     * @param anchor  anchorView
     */
    public void showPositiveContextMessage(@NonNull String message, @IdRes int anchor) {
        new Handler(Looper.getMainLooper())
                .post(() -> executeCommands(new PositiveContextMessage(message, anchor)));
    }

    /**
     * Show neutral context message.
     *
     * @param message message to show
     * @param anchor  anchorView
     */
    public void showNeutralContextMessage(@NonNull String message, @IdRes int anchor) {
        new Handler(Looper.getMainLooper())
                .post(() -> executeCommands(new NeutralContextMessage(message, anchor)));
    }
}
