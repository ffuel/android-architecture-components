package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.a65apps.architecturecomponents.domain.Mapper;
import com.a65apps.architecturecomponents.sample.domain.posts.PostState;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

class NetworkToStateMapper extends Mapper<PostsJson, List<PostState>> {

    @Inject
    NetworkToStateMapper() {
//      Inject constructor
    }

    @NonNull
    @Override
    public List<PostState> map(@NonNull PostsJson postsJson) {
        List<PostJson> items = postsJson.items();
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }

        return Observable.fromIterable(items)
                .map(item -> PostState.builder()
                        .createdBy(safeString(item.createdBy()))
                        .curated(safeBoolean(item.curated()))
                        .description(safeString(item.description()))
                        .displayName(TextUtils.isEmpty(item.displayName()) ? safeString(item.name())
                                : item.displayName())
                        .featured(safeBoolean(item.featured()))
                        .name(safeString(item.name()))
                        .score(safeDouble(item.score()))
                        .shortDescription(safeString(item.shortDescription()))
                        .build())
                .toList()
                .blockingGet();
    }

    @NonNull
    private String safeString(@Nullable String value) {
        return value != null ? value : "";
    }

    private boolean safeBoolean(@Nullable Boolean value) {
        return value != null && value;
    }

    private double safeDouble(@Nullable Double value) {
        return value != null ? value : 0.0;
    }
}
