package com.a65apps.architecturecomponents.sample.presentation.mvi;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.presentation.mapper.ParcelableToStateMapper;
import com.a65apps.architecturecomponents.sample.domain.mvi.MviState;

import javax.inject.Inject;

class MviParcelMapper extends ParcelableToStateMapper<MviParcelable, MviState> {

    @Inject
    MviParcelMapper() {
//      Inject constructor
    }

    @NonNull
    @Override
    public MviState map(@NonNull MviParcelable mviParcelable) {
        return MviState.builder()
                .title(mviParcelable.title())
                .subtitle(mviParcelable.subtitle())
                .build();
    }
}
