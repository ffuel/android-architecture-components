package com.a65apps.architecturecomponents.sample.domain.posts;

import com.a65apps.architecturecomponents.domain.paging.PagingInteractor;
import com.a65apps.architecturecomponents.presentation.navigation.Router;

public interface PostsInteractor extends PagingInteractor<PostState, PostsState, Router> {
}
