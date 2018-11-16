package com.a65apps.architecturecomponents.sample.data;

import android.content.SharedPreferences;

import com.a65apps.architecturecomponents.sample.data.contacts.TestContactsDataModule;
import com.a65apps.architecturecomponents.sample.data.posts.PostsDataBaseModule;
import com.a65apps.architecturecomponents.sample.data.posts.PostsDataModule;
import com.a65apps.architecturecomponents.sample.data.posts.TestPostsNetworkModule;
import com.a65apps.architecturecomponents.sample.data.sample.TestSampleDataModule;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module(includes = {
        TestSampleDataModule.class,
        TestContactsDataModule.class,
        PostsDataModule.class,
        TestPostsNetworkModule.class,
        PostsDataBaseModule.class
})
public class TestAppDataModule {

    private final SharedPreferences preferences = Mockito.mock(SharedPreferences.class);

    @Provides
    SharedPreferences providesSharedPreferences() {
        return preferences;
    }
}
