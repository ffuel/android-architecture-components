package com.a65apps.architecturecomponents.sample.data.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.a65apps.architecturecomponents.sample.domain.contacts.ContactState;
import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsListState;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;

import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContactsSourceTest {

    @Mock
    private Context context;
    @Mock
    private ContentResolver contentResolver;
    @Mock
    private Cursor cursor;

    @Before
    public void setup() {
        when(context.getContentResolver()).thenReturn(contentResolver);
        when(contentResolver.query(any(), any(), any(), any(), any()))
                .thenReturn(cursor);
        int displayName = 1;
        when(cursor.getColumnIndex(eq(ContactsContract.Contacts.DISPLAY_NAME)))
                .thenReturn(displayName);
        when(cursor.getString(eq(displayName)))
                .thenReturn("name");
        int photoThumbnailUri = 2;
        when(cursor.getColumnIndex(eq(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI)))
                .thenReturn(photoThumbnailUri);
        when(cursor.getString(eq(photoThumbnailUri)))
                .thenReturn("photo url");
    }

    @Test
    public void dataTest() {
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext())
                .thenReturn(true)
                .thenReturn(false);
        ContactsSource source = new ContactsSource(context);
        TestObserver<ContactsListState> observer = new TestObserver<>();

        source.data("testQuery").subscribe(observer);

        observer.assertValue(ContactsListState.create(
                Collections.singletonList(ContactState.builder()
                        .name("name")
                        .photo("photo url")
                        .build())));
        verify(cursor, times(1)).close();
        verify(contentResolver, times(1))
                .query(eq(ContactsContract.Contacts.CONTENT_URI),
                        isNull(), eq("lower(display_name) LIKE ?"),
                        eq(new String[]{"%testquery%"}), isNull());
    }

    @Test
    public void dataEmptyQueryTest() {
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext())
                .thenReturn(true)
                .thenReturn(false);
        ContactsSource source = new ContactsSource(context);
        TestObserver<ContactsListState> observer = new TestObserver<>();

        source.data("").subscribe(observer);

        observer.assertValue(ContactsListState.create(
                Collections.singletonList(ContactState.builder()
                        .name("name")
                        .photo("photo url")
                        .build())));
        verify(cursor, times(1)).close();
        verify(contentResolver, times(1))
                .query(eq(ContactsContract.Contacts.CONTENT_URI),
                        isNull(), isNull(), isNull(), isNull());
    }
}
