package com.a65apps.ciceronev4architecturecomponents;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import ru.terrakok.cicerone.commands.Command;

public interface FragmentAnimationFactory {

    void setupTransactionAnimation(@NonNull Command command,
                                   @Nullable Fragment currentFragment,
                                   @Nullable Fragment nextFragment,
                                   @NonNull FragmentTransaction fragmentTransaction);
}
