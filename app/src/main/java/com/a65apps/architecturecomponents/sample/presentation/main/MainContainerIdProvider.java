package com.a65apps.architecturecomponents.sample.presentation.main;

import com.a65apps.architecturecomponents.sample.R;
import com.a65apps.architecturecomponents.presentation.activity.ContainerIdProvider;

import javax.inject.Inject;

public class MainContainerIdProvider implements ContainerIdProvider {

    @Inject
    public MainContainerIdProvider() {
        // @Inject constructor
    }

    @Override
    public int get() {
        return R.id.container;
    }
}
