package com.a65apps.architecturecomponents.presentation.navigationv2;

import android.support.annotation.NonNull;

public interface Router extends com.a65apps.architecturecomponents.presentation.navigation.Router {

    void navigateTo(@NonNull Screen screen);

    void newRootScreen(@NonNull Screen screen);

    void replaceScreen(@NonNull Screen screen);

    void backTo(@NonNull Screen screen);

    /**
     * Opens several screens inside single transaction.
     * @param screens array of screens
     */
    void newChain(@NonNull Screen... screens);

    /**
     * Clear current stack and open several screens inside single transaction.
     * @param screens array of screens
     */
    void newRootChain(@NonNull Screen... screens);

    /**
     * Remove all screens from the chain and exit.
     * It's mostly used to finish the application or close a supplementary navigation chain.
     */
    void finishChain();

    void exit();
}
