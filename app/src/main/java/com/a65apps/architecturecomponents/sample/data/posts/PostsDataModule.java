package com.a65apps.architecturecomponents.sample.data.posts;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.Mapper;
import com.a65apps.architecturecomponents.sample.data.common.SourceKey;
import com.a65apps.architecturecomponents.sample.data.common.SourceType;
import com.a65apps.architecturecomponents.sample.domain.posts.PostState;
import com.a65apps.architecturecomponents.sample.domain.posts.PostsSource;

import java.util.List;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public interface PostsDataModule {

    @Singleton
    @NonNull
    @Binds
    PostsSource bindsPostsDataSource(@NonNull PostsDataSource source);

    @Singleton
    @NonNull
    @Binds
    @IntoMap
    @SourceKey(SourceType.NETWORK)
    PostsSource bindsPostsNetworkSource(@NonNull PostsNetworkSource source);

    @Singleton
    @NonNull
    @Binds
    @IntoMap
    @SourceKey(SourceType.DB)
    PostsSource bindsPostsDataBaseSource(@NonNull PostsDataBaseSource source);

    @Singleton
    @NonNull
    @Binds
    Mapper<PostDb, PostState> bindsDbToStateMapper(@NonNull DbToStateMapper mapper);

    @Singleton
    @NonNull
    @Binds
    Mapper<PostsJson, List<PostState>> bindsNetworkToStateMapper(@NonNull NetworkToStateMapper mapper);

    @Singleton
    @NonNull
    @Binds
    Mapper<PostState, PostDb> bindsStateToDbMapper(@NonNull StateToDbMapper mapper);
}
