package com.a65apps.architecturecomponents.presentation;


import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.main.MainActivity;
import com.a65apps.architecturecomponents.presentation.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module()
public interface ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModule.class, FragmentBuilder.class})
    @NonNull
    MainActivity bindMainActivity();
}
