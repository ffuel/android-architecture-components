package com.a65apps.architecturecomponents.sample.presentation.sample.contacts;

import android.Manifest;

import com.a65apps.architecturecomponents.sample.domain.contacts.ContactState;
import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsListState;
import com.a65apps.architecturecomponents.sample.presentation.common.BaseScenario;
import com.a65apps.architecturecomponents.sample.presentation.common.Story;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleGivenState;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleThenState;
import com.a65apps.architecturecomponents.sample.presentation.sample.SampleWhenState;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ContactsClickScenario extends BaseScenario<SampleGivenState, SampleWhenState, SampleThenState> {

    @Test
    @Story("contacts")
    public void user_click_contacts_should_open_contacts_screen() {
        given().has_permission_$(Manifest.permission.READ_CONTACTS)
                .and().first_start_with_success()
                .and().contacts_source_on_query_$_return_$("тест", ContactsListState.create(Collections
                .singletonList(ContactState.builder()
                        .name("Тест Тестович")
                        .photo("Тестовое Фото")
                        .build())));

        when().user_click_contacts();

        then().user_sees_text_$("тест")
                .and().user_sees_text_$_in_contacts_list_at_position_$("Тест Тестович", 0);
    }
}
