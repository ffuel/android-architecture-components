package com.a65apps.daggerarchitecturecomponents.receiver;

import android.content.Context;

import com.a65apps.architecturecomponents.data.receiver.BaseBroadcastReceiver;

import dagger.android.AndroidInjection;

public abstract class DaggerReceiver extends BaseBroadcastReceiver {

    @Override
    protected void inject(Context context) {
        AndroidInjection.inject(this, context);
    }
}
