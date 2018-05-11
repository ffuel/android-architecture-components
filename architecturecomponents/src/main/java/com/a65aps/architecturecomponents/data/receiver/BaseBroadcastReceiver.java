package com.a65aps.architecturecomponents.data.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.CallSuper;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public abstract class BaseBroadcastReceiver extends BroadcastReceiver {

    @Override
    @CallSuper
    public void onReceive(Context context, Intent intent) {
        inject(context);
    }

    protected abstract void inject(Context context);
}
