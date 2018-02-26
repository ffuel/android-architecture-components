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
        return backCommandActivated;
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
            backCommandActivated = true;
            super.applyCommand(command);
            backCommandActivated = false;
            return;
        } else if (command instanceof NewRootScreenIfNotExistsCommand) {
            NewRootScreenIfNotExistsCommand newRootScreenIfNotExistsCommand =
                    (NewRootScreenIfNotExistsCommand) command;
            Fragment fragment = fragmentManager
                    .findFragmentByTag(newRootScreenIfNotExistsCommand.getScreenKey());
            if (fragment == null) {
                backCommandActivated = true;
                super.applyCommand(new BackTo(null));
                backCommandActivated = false;
                super.applyCommand(new Replace(newRootScreenIfNotExistsCommand.getScreenKey(),
                        newRootScreenIfNotExistsCommand.getData()));
            }
            return;
        }

        super.applyCommand(command);
    }
}
