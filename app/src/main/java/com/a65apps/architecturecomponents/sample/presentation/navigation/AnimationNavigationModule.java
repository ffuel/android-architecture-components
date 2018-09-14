package com.a65apps.architecturecomponents.sample.presentation.navigation;

import android.support.annotation.NonNull;

import com.a65apps.ciceronearchitecturecomponents.FragmentAnimationFactory;

import java.util.Map;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import dagger.multibindings.Multibinds;
import dagger.multibindings.StringKey;

@Module
public interface AnimationNavigationModule {

    @Multibinds
    @NonNull
    Map<String, FragmentAnimationFactory> multibindsFragmentAnimationFactory();

    @Binds
    @IntoMap
    @StringKey(AnimationConstants.POSTS_ENTER_KEY)
    @NonNull
    FragmentAnimationFactory bindsPostsEnterAnimationFactory(
            @NonNull PostsEnterAnimationFactory factory);
}
