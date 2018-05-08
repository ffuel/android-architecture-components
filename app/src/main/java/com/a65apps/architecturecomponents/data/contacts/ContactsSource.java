package com.a65apps.architecturecomponents.data.contacts;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.contacts.ContactState;
import com.a65apps.architecturecomponents.domain.contacts.ContactsListState;
import com.a65aps.architecturecomponents.domain.source.SingleSourceWithParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import io.reactivex.Single;

final class ContactsSource implements SingleSourceWithParam<ContactsListState, String> {

    private static final String WHERE_QUERY = "lower(" + ContactsContract.Contacts.DISPLAY_NAME + ") LIKE ?";

    @NonNull
    private final Context context;

    @Inject
    ContactsSource(@NonNull Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Single<ContactsListState> data(@NonNull final String query) {
        return Single.fromCallable(() -> {
            ContentResolver cr = context.getContentResolver();
            Cursor cursor = null;
            List<ContactState> result = new ArrayList<>();
            try {
                String whereQuery = null;
                String[] args = null;
                if (!query.isEmpty()) {
                    whereQuery = WHERE_QUERY;
                    args = new String[]{"%" + query.toLowerCase(Locale.getDefault()) + "%"};
                }
                cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null, whereQuery,
                        args, null);
                if (cursor == null || cursor.getCount() == 0) {
                    return ContactsListState.create(Collections.emptyList());
                }

                while (cursor.moveToNext()) {
                    String name = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.Contacts.DISPLAY_NAME));
                    String photo = cursor.getString(cursor.getColumnIndex(
                            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                    result.add(ContactState.builder()
                            .name(name == null ? "" : name)
                            .photo(photo == null ? "" : photo)
                            .build());
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            return ContactsListState.create(result);
        });
    }
}
