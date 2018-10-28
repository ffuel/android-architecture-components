package com.a65apps.architecturecomponents.sample.presentation.mvi;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.mapper.StateToParcelableMapper;
import com.a65apps.architecturecomponents.sample.domain.mvi.MviState;

import javax.inject.Inject;

class MviStateMapper extends StateToParcelableMapper<MviState, MviParcelable> {

    @Inject
    MviStateMapper() {
//      Inject constructor
    }

    @NonNull
    @Override
    public MviParcelable map(@NonNull MviState mviState) {
        return MviParcelable.builder()
                .title(mviState.title())
                .subtitle(mviState.subtitle())
                .build();
    }
}
