package com.a65apps.architecturecomponents.data.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;

import net.jcip.annotations.ThreadSafe;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.disposables.Disposables;

@ThreadSafe
final class RxBroadcastReceiver implements ObservableOnSubscribe<Intent> {

    @NonNull
    private final WeakReference<Context> context;
    @NonNull
    private final IntentFilter filter;

    @NonNull
    static Observable<Intent> create(@NonNull final Context context,
                                     @NonNull final IntentFilter filter) {
        return Observable.defer(() -> Observable.create(new RxBroadcastReceiver(context, filter)));
    }

    private RxBroadcastReceiver(@NonNull Context context, @NonNull IntentFilter filter) {
        this.context = new WeakReference<>(context);
        this.filter = filter;
    }

    @Override
    public void subscribe(@NonNull final ObservableEmitter<Intent> emitter) {
        final BroadcastReceiver receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                emitter.onNext(intent);
            }
        };

        emitter.setDisposable(Disposables.fromAction(() -> {
            Context ctx = context.get();
            if (ctx != null) {
                ctx.unregisterReceiver(receiver);
            }
        }));

        Context ctx = context.get();
        if (ctx != null) {
            ctx.registerReceiver(receiver, filter);
        }
    }
}
