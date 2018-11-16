package com.a65apps.architecturecomponents.sample.data.contacts;

import com.a65apps.architecturecomponents.domain.source.SingleSourceWithParam;
import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsListState;

import org.mockito.Mockito;

import javax.inject.Singleton;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class TestContactsDataModule {

    @SuppressWarnings("unchecked")
    private final SingleSourceWithParam<ContactsListState, String> source = Mockito.mock(SingleSourceWithParam.class);

    @Provides
    @Singleton
    @NonNull
    SingleSourceWithParam<ContactsListState, String> providesContactsSource() {
        return source;
    }
}
