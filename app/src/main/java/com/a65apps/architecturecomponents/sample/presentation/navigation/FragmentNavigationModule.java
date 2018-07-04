package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.domain.main.MainConstants;
import com.a65apps.ciceronearchitecturecomponents.FragmentFabric;

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
    Map<String, FragmentFabric> multibindsFragmentFabric();

    @Binds
    @IntoMap
    @StringKey(MainConstants.SAMPLE_KEY)
    @NonNull
    FragmentFabric bindsSampleScreenFabric(@NonNull SampleScreenFabric fabric);

    @Binds
    @IntoMap
    @StringKey(MainConstants.CONTACTS_KEY)
    @NonNull
    FragmentFabric bindsContactsScreenFabric(@NonNull ContactsScreenFabric fabric);

    @Binds
    @IntoMap
    @StringKey(MainConstants.PERMISSION_EXPLANATION_KEY)
    @NonNull
    FragmentFabric bindsPermissionsExplanationScreenFabric(@NonNull PermissionsExplanationScreenFabric fabric);

    @Binds
    @IntoMap
    @StringKey(MainConstants.POSTS_KEY)
    @NonNull
    FragmentFabric bindsPostsScreenFabric(@NonNull PostsScreenFabric fabric);
}
