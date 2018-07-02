package com.a65apps.architecturecomponents.domain;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("squid:S1610")
public abstract class Mapper<From, To> {
    @NonNull
    public abstract To map(@NonNull From from);

    @NonNull
    public List<To> map(@NonNull Collection<From> from) {
        if (from.isEmpty()) {
            return Collections.emptyList();
        }

        List<To> to = new ArrayList<>(from.size());
        for (From fromItem : from) {
            to.add(map(fromItem));
        }
        return to;
    }
}
