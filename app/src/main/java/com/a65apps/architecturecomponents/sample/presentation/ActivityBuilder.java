package com.a65apps.architecturecomponents.sample.presentation;


import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.presentation.main.MainActivity;
import com.a65apps.architecturecomponents.sample.presentation.main.MainActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityBuilder {

    @ContributesAndroidInjector(modules = {MainActivityModule.class, FragmentBuilder.class})
    @NonNull
    MainActivity bindMainActivity();
}
