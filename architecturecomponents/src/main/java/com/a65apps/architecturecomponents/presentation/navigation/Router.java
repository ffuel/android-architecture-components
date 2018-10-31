package com.a65apps.architecturecomponents.presentation.navigation;

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

    /**
     * @deprecated use {@link com.a65apps.architecturecomponents.presentation.navigationv2}
     *
     * @param screenKey screen key
     */
    @Deprecated
    void newScreenChain(@NonNull String screenKey);

    /**
     * @deprecated use {@link com.a65apps.architecturecomponents.presentation.navigationv2}
     *
     * @param screenKey screen key
     */
    @Deprecated
    void newScreenChain(@NonNull String screenKey, @Nullable Object data);

    void finishChain();

    void exit();

    /**
     * @deprecated use {@link com.a65apps.architecturecomponents.presentation.navigationv2}
     *
     * @param message system message
     */
    @Deprecated
    void exitWithMessage(@NonNull String message);

    /**
     * @deprecated use {@link com.a65apps.architecturecomponents.presentation.navigationv2}
     *
     * @param message system message
     */
    @Deprecated
    void showSystemMessage(@NonNull String message);
}
