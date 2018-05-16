package com.a65apps.architecturecomponents.domain;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class Mapper<From, To> {
    @NonNull
    public abstract To map(@NonNull From from);

    @NonNull
    public final List<To> map(@NonNull Collection<From> from) {
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
