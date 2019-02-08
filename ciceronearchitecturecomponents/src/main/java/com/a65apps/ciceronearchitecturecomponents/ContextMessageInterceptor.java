package com.a65apps.ciceronearchitecturecomponents;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import ru.terrakok.cicerone.commands.Command;

public interface ContextMessageInterceptor {
    boolean intercept(@NonNull FragmentActivity activity, @NonNull Command command);

    void dismiss();
}
