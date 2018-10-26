package com.a65apps.architecturecomponents.domain.model;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.Intent;
import com.a65apps.architecturecomponents.domain.IntentFactory;

import java.util.Map;

import javax.inject.Inject;

public class DefaultIntentFactory implements IntentFactory {

    @NonNull
    private final Map<String, Intent> intentMap;

    @Inject
    public DefaultIntentFactory(@NonNull Map<String, Intent> intentMap) {
        this.intentMap = intentMap;
    }

    @NonNull
    @Override
    public Intent get(@NonNull String type) {
        Intent intent = intentMap.get(type);
        if (intent == null) {
            throw new IllegalArgumentException("unknown intent type: " + type);
        }

        return intent;
    }
}
