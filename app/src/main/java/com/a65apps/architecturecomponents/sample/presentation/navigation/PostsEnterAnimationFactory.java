package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.a65apps.ciceronev4architecturecomponents.FragmentAnimationFactory;

import javax.inject.Inject;

import ru.terrakok.cicerone.commands.Command;

class PostsEnterAnimationFactory implements FragmentAnimationFactory {

    @Inject
    PostsEnterAnimationFactory() {
//      Inject constructor
    }

    @Override
    public void setupTransactionAnimation(@NonNull Command command,
                                          @Nullable Fragment currentFragment,
                                          @Nullable Fragment nextFragment,
                                          @NonNull FragmentTransaction fragmentTransaction) {
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,
                android.R.anim.slide_out_right);
    }
}
