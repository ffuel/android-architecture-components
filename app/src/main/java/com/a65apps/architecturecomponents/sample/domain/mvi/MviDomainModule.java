package com.a65apps.architecturecomponents.sample.domain.mvi;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.Intent;
import com.a65apps.architecturecomponents.domain.IntentFactory;
import com.a65apps.architecturecomponents.domain.StateProvider;
import com.a65apps.architecturecomponents.domain.model.DefaultIntentFactory;
import com.a65apps.architecturecomponents.domain.model.IntentInteractor;
import com.a65apps.architecturecomponents.domain.model.IntentModel;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public interface MviDomainModule {

    @Binds
    @NonNull
    IntentInteractor<MviState, Router> bindsInteractor(@NonNull IntentModel<MviState, Router> model);

    @Binds
    @NonNull
    IntentFactory bindsIntentFactory(@NonNull DefaultIntentFactory factory);

    @Binds
    @NonNull
    StateProvider<MviState> bindsStateProvider(@NonNull DefaultMviStateProvider stateProvider);

    @Binds
    @IntoMap
    @StringKey(MviConstants.TITLE_INTENT)
    @NonNull
    Intent bindsTitleIntent(@NonNull TitleIntent intent);

    @Binds
    @IntoMap
    @StringKey(MviConstants.SUBTITLE_INTENT)
    @NonNull
    Intent bindsSubtitleIntent(@NonNull SubtitleIntent intent);
}
