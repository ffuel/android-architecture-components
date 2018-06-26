package com.a65apps.architecturecomponents.data.receiver;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.a65apps.architecturecomponents.domain.receiver.ConnectionReceiverSource;
import com.a65apps.architecturecomponents.domain.receiver.ConnectionState;

import net.jcip.annotations.ThreadSafe;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;

@ThreadSafe
public class ConnectionBroadcastReceiverSource implements ConnectionReceiverSource {

    @NonNull
    private static final ConnectionState DEFAULT = ConnectionState.builder()
            .type(-1)
            .subtype(-1)
            .typeName("")
            .subtypeName("")
            .state(ConnectionState.State.UNKNOWN)
            .detailedState(ConnectionState.DetailedState.IDLE)
            .reason("")
            .extraInfo("")
            .isFailover(false)
            .isAvailable(false)
            .isRoaming(false)
            .build();

    @NonNull
    private final Context context;

    @Inject
    ConnectionBroadcastReceiverSource(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Observable<ConnectionState> observeReceiver() {
        return RxBroadcastReceiver.create(context,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
                .map(it -> getConnectionState());
    }

    @NonNull
    @Override
    public Single<ConnectionState> single() {
        return Single.fromCallable(this::getConnectionState);
    }

    @NonNull
    private ConnectionState getConnectionState() {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return DEFAULT;
        }
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info == null) {
            return DEFAULT;
        }

        String typeName = TextUtils.isEmpty(info.getTypeName()) ? ""
                : info.getTypeName();
        String subtypeName = TextUtils.isEmpty(info.getSubtypeName()) ? ""
                : info.getSubtypeName();
        String reason = TextUtils.isEmpty(info.getReason()) ? ""
                : info.getReason();
        String extraInfo = TextUtils.isEmpty(info.getExtraInfo()) ? ""
                : info.getExtraInfo();

        return ConnectionState.builder()
                .type(info.getType())
                .subtype(info.getSubtype())
                .typeName(typeName)
                .subtypeName(subtypeName)
                .state(ConnectionState.State.values()[info.getState().ordinal()])
                .detailedState(ConnectionState.DetailedState.values()
                        [info.getDetailedState().ordinal()])
                .reason(reason)
                .extraInfo(extraInfo)
                .isFailover(info.isFailover())
                .isAvailable(info.isAvailable())
                .isRoaming(info.isRoaming())
                .build();
    }
}
