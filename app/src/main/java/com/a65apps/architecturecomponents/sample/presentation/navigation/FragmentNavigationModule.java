package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.domain.navigation.NavigationConstants;
import com.a65apps.architecturecomponents.presentation.navigationv2.FragmentFactory;

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
    @StringKey(NavigationConstants.SAMPLE_KEY)
    @NonNull
    FragmentFactory bindsSampleScreenFactory(@NonNull SampleScreenFactory factory);

    @Binds
    @IntoMap
    @StringKey(NavigationConstants.CONTACTS_KEY)
    @NonNull
    FragmentFactory bindsContactsScreenFactory(@NonNull ContactsScreenFactory factory);

    @Binds
    @IntoMap
    @StringKey(NavigationConstants.PERMISSION_EXPLANATION_KEY)
    @NonNull
    FragmentFactory bindsPermissionsExplanationScreenFactory(@NonNull PermissionsExplanationScreenFactory factory);

    @Binds
    @IntoMap
    @StringKey(NavigationConstants.POSTS_KEY)
    @NonNull
    FragmentFactory bindsPostsScreenFactory(@NonNull PostsScreenFactory factory);

    @Binds
    @IntoMap
    @StringKey(NavigationConstants.MVI_KEY)
    @NonNull
    FragmentFactory bindsMviScreenFactory(@NonNull MviScreenFactory factory);
}
