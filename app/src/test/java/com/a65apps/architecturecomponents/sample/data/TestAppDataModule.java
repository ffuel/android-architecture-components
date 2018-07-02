package com.a65apps.architecturecomponents.sample.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.sample.data.contacts.ContactsDataModel;
import com.a65apps.architecturecomponents.sample.data.posts.PostsDataBaseModule;
import com.a65apps.architecturecomponents.sample.data.posts.PostsDataModule;
import com.a65apps.architecturecomponents.sample.data.posts.PostsNetworkModule;
import com.a65apps.architecturecomponents.sample.data.sample.TestSampleDataModule;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@Module(includes = {
        TestSampleDataModule.class,
        ContactsDataModel.class,
        PostsDataModule.class,
        PostsNetworkModule.class,
        PostsDataBaseModule.class
})
public class TestAppDataModule {

    private final SharedPreferences preferences = Mockito.mock(SharedPreferences.class);

    @Provides
    SharedPreferences providesSharedPreferences(@NonNull Context context) {
        when(context.getSharedPreferences(any(), anyInt()))
                .thenReturn(preferences);
        return preferences;
    }
}
