package com.a65apps.architecturecomponents.domain.contacts;

import android.support.annotation.NonNull;

import dagger.Binds;
import dagger.Module;

@Module
public interface ContactsDomainModule {

    @Binds
    @NonNull
    ContactsInteractor bindsTo(@NonNull ContactsModel model);
}
