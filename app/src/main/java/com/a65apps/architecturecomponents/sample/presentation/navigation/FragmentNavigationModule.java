package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.domain.main.MainConstants;
import com.a65apps.ciceronearchitecturecomponents.FragmentFactory;

import java.util.Map;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.Multibinds;
import dagger.multibindings.StringKey;

@Module
public interface FragmentNavigationModule {

    @Multibinds
    @NonNull
    Map<String, FragmentFactory> multibindsFragmentFactory();

    @Binds
    @IntoMap
    @StringKey(MainConstants.SAMPLE_KEY)
    @NonNull
    FragmentFactory bindsSampleScreenFactory(@NonNull SampleScreenFactory factory);

    @Binds
    @IntoMap
    @StringKey(MainConstants.CONTACTS_KEY)
    @NonNull
    FragmentFactory bindsContactsScreenFactory(@NonNull ContactsScreenFactory factory);

    @Binds
    @IntoMap
    @StringKey(MainConstants.PERMISSION_EXPLANATION_KEY)
    @NonNull
    FragmentFactory bindsPermissionsExplanationScreenFactory(@NonNull PermissionsExplanationScreenFactory factory);

    @Binds
    @IntoMap
    @StringKey(MainConstants.POSTS_KEY)
    @NonNull
    FragmentFactory bindsPostsScreenFactory(@NonNull PostsScreenFactory factory);

    @Binds
    @IntoMap
    @StringKey(MainConstants.MVI_KEY)
    @NonNull
    FragmentFactory bindsMviScreenFactory(@NonNull MviScreenFactory factory);
}
