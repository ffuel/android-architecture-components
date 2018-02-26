package com.a65aps.architecturecomponents.presentation.navigation;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import ru.terrakok.cicerone.android.SupportAppNavigator;
import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Replace;

public abstract class BaseNavigator extends SupportAppNavigator {

    private static boolean backCommandActivated = false;

    @NonNull
    private final FragmentManager fragmentManager;

    public static boolean isBackCommandActivated() {
        synchronized (BaseNavigator.class) {
            return backCommandActivated;
        }
    }

    private static void setBackCommandActivated(boolean value) {
        synchronized (BaseNavigator.class) {
            backCommandActivated = value;
        }
    }

    public BaseNavigator(@NonNull FragmentActivity activity, int containerId) {
        super(activity, containerId);
        fragmentManager = activity.getSupportFragmentManager();
    }

    public BaseNavigator(@NonNull FragmentActivity activity,
                         @NonNull FragmentManager fragmentManager,
                         int containerId) {
        super(activity, fragmentManager, containerId);
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void applyCommand(@NonNull Command command) {
        if (command instanceof BackTo) {
            setBackCommandActivated(true);
            super.applyCommand(command);
            setBackCommandActivated(false);
            return;
        } else if (command instanceof NewRootScreenIfNotExistsCommand) {
            NewRootScreenIfNotExistsCommand newRootScreenIfNotExistsCommand =
                    (NewRootScreenIfNotExistsCommand) command;
            Fragment fragment = fragmentManager
                    .findFragmentByTag(newRootScreenIfNotExistsCommand.getScreenKey());
            if (fragment == null) {
                setBackCommandActivated(true);
                super.applyCommand(new BackTo(null));
                setBackCommandActivated(false);
                super.applyCommand(new Replace(newRootScreenIfNotExistsCommand.getScreenKey(),
                        newRootScreenIfNotExistsCommand.getData()));
            }
            return;
        }

        super.applyCommand(command);
    }
}
