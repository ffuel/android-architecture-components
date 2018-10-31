package com.a65apps.ciceronev4architecturecomponents;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;

import ru.terrakok.cicerone.commands.Command;

public interface NavigationInterceptor {

    boolean intercept(@NonNull FragmentActivity activity, @NonNull Command command);
}
