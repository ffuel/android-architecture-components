package com.a65aps.architecturecomponents.presentation.navigation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface Router {

    void navigateTo(@NonNull String screenKey);

    void navigateTo(@NonNull String screenKey, @Nullable Object data);

    void replaceScreen(@NonNull String screenKey);

    void replaceScreen(@NonNull String screenKey, @Nullable Object data);

    void backTo(@Nullable String screenKey);

    void newRootScreen(@NonNull String screenKey);

    void newRootScreen(@NonNull String screenKey, @Nullable Object data);

    void newScreenChain(@NonNull String screenKey);

    void newScreenChain(@NonNull String screenKey, @Nullable Object data);

    void finishChain();

    void exit();

    void exitWithMessage(@NonNull String message);

    void showSystemMessage(@NonNull String message);
}
