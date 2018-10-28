package com.a65apps.architecturecomponents.domain;

import android.support.annotation.NonNull;

public interface IntentFactory {

    @NonNull
    Intent get(@NonNull String type);
}
