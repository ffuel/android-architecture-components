package com.a65aps.daggerarchitecturecomponents.receiver;

import android.content.Context;

import com.a65aps.architecturecomponents.data.receiver.BaseBroadcastReceiver;

import dagger.android.AndroidInjection;

public abstract class DaggerReceiver extends BaseBroadcastReceiver {

    @Override
    protected void inject(Context context) {
        AndroidInjection.inject(this, context);
    }
}
