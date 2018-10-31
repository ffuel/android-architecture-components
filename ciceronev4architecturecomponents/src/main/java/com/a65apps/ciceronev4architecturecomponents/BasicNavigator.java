package com.a65apps.ciceronev4architecturecomponents;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.a65apps.architecturecomponents.presentation.activity.ContainerIdProvider;

import java.util.Map;

import ru.terrakok.cicerone.Screen;
import ru.terrakok.cicerone.android.support.SupportAppNavigator;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

public class BasicNavigator extends SupportAppNavigator {

    @NonNull
    private final Map<String, NavigationInterceptor> interceptorMap;
    @NonNull
    private final Map<String, FragmentAnimationFactory> animationMap;
    @NonNull
    private final FragmentActivity activity;

    public BasicNavigator(@NonNull FragmentActivity activity,
                          @NonNull ContainerIdProvider idProvider,
                          @NonNull Map<String, NavigationInterceptor> interceptorMap,
                          @NonNull Map<String, FragmentAnimationFactory> animationMap) {
        super(activity, idProvider.get());
        this.interceptorMap = interceptorMap;
        this.activity = activity;
        this.animationMap = animationMap;
    }

    @Override
    protected void applyCommand(Command command) {
        String key = null;
        if (command instanceof BackTo) {
            Screen screen = ((BackTo) command).getScreen();
            if (screen != null) {
                key = screen.getScreenKey();
            }
        }
        if (command instanceof Forward) {
            Screen screen = ((Forward) command).getScreen();
            if (screen != null) {
                key = screen.getScreenKey();
            }
        }
        if (command instanceof Replace) {
            Screen screen = ((Replace) command).getScreen();
            if (screen != null) {
                key = screen.getScreenKey();
            }
        }
        if (key != null) {
            NavigationInterceptor interceptor = interceptorMap.get(key);
            if (interceptor != null && interceptor.intercept(activity, command)) {
                return;
            }
        }

        super.applyCommand(command);
    }

    @Override
    protected void setupFragmentTransaction(Command command, Fragment currentFragment,
                                            Fragment nextFragment,
                                            FragmentTransaction fragmentTransaction) {
        if (currentFragment == null && nextFragment == null) {
            throw new IllegalStateException("Both transition fragments can't be null");
        }

        AnimationKey key = AnimationKey.create(
                currentFragment != null ? currentFragment.getClass() : null,
                nextFragment != null ? nextFragment.getClass() : null,
                command.getClass());
        String value = key.toString();
        FragmentAnimationFactory factory = animationMap.get(value);
        if (factory != null) {
            factory.setupTransactionAnimation(command, currentFragment, nextFragment,
                    fragmentTransaction);
        }
    }

    private static final class AnimationKey {

        private static final int PRIME_NUMBER = 31;

        @Nullable
        private final Class<? extends Fragment> currentFragment;
        @Nullable
        private final Class<? extends Fragment> nextFragment;
        @NonNull
        private final Class<? extends Command> command;

        @NonNull
        public static AnimationKey create(@Nullable Class<? extends Fragment> currentFragment,
                                          @Nullable Class<? extends Fragment> nextFragment,
                                          @NonNull Class<? extends Command> command) {
            return new AnimationKey(currentFragment, nextFragment, command);
        }

        private AnimationKey(@Nullable Class<? extends Fragment> currentFragment,
                             @Nullable Class<? extends Fragment> nextFragment,
                             @NonNull Class<? extends Command> command) {
            this.currentFragment = currentFragment;
            this.nextFragment = nextFragment;
            this.command = command;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            AnimationKey that = (AnimationKey) o;

            return (currentFragment != null ? currentFragment.equals(that.currentFragment)
                    : that.currentFragment == null)
                    && (nextFragment != null ? nextFragment.equals(that.nextFragment)
                    : that.nextFragment == null)
                    && command.equals(that.command);
        }

        @Override
        public int hashCode() {
            int result = currentFragment != null ? currentFragment.hashCode() : 0;
            result = PRIME_NUMBER * result + (nextFragment != null ? nextFragment.hashCode() : 0);
            result = PRIME_NUMBER * result + command.hashCode();
            return result;
        }

        @Override
        public String toString() {
            return (currentFragment != null ? currentFragment.getSimpleName() : "null")
                    + " " + (nextFragment != null ? nextFragment.getSimpleName() : "null")
                    + " " + command.getSimpleName();
        }
    }
}
