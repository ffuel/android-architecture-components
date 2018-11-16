package com.a65apps.architecturecomponents.sample.presentation.sample;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.source.SingleSource;
import com.a65apps.architecturecomponents.domain.source.SingleSourceWithParam;
import com.a65apps.architecturecomponents.sample.TestSampleComponent;
import com.a65apps.architecturecomponents.sample.data.posts.PostsDataBase;
import com.a65apps.architecturecomponents.sample.data.sample.PreferenceKeys;
import com.a65apps.architecturecomponents.sample.domain.contacts.ContactsListState;
import com.a65apps.architecturecomponents.sample.presentation.common.BaseGivenState;
import com.a65apps.architecturecomponents.sample.presentation.common.Quoted;
import com.a65apps.daggerarchitecturecomponents.source.Remote;
import com.tngtech.jgiven.CurrentStep;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import com.tngtech.jgiven.attachment.Attachment;

import org.mockito.Mockito;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.CallSuper;
import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.mockito.ArgumentMatchers.eq;

public class SampleGivenState extends BaseGivenState<SampleGivenState> {

    @ExpectedScenarioState
    CurrentStep currentStep;

    @Inject
    @Remote
    SingleSource<String> source;
    @Inject
    SingleSourceWithParam<ContactsListState, String> contactsSource;
    @Inject
    PostsDataBase dataBase;

    @ProvidedScenarioState
    public MockWebServer server;

    @Override
    @CallSuper
    public void setup() throws Exception {
        super.setup();
        //noinspection unchecked
        Mockito.reset(source, contactsSource);
//      TODO remove fromAction when Robolectric fix https://github.com/robolectric/robolectric/issues/4081
        Completable.fromAction(() -> {
            dataBase.getPostDao().deleteAll();
            server = new MockWebServer();
            server.start(8888);
        }).subscribeOn(Schedulers.io())
                .blockingAwait();
    }

    @Override
    public void tearDown() {
        try {
            server.shutdown();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @NonNull
    public SampleGivenState network_source_emit_$_data(@NonNull @Quoted String data) {
        Mockito.when(source.data()).thenReturn(Single.just(data));
        return self();
    }

    @NonNull
    public SampleGivenState network_source_emit_$_error(@NonNull @Quoted String error) {
        Mockito.when(source.data()).thenReturn(Single.error(new RuntimeException(error)));
        return self();
    }

    @NonNull
    public SampleGivenState network_source_no_emit_data() {
        Mockito.when(source.data()).thenReturn(Single.never());
        return self();
    }

    @NonNull
    public SampleGivenState first_start_with_success() {
        return has_internet_connection()
                .and().network_source_emit_$_data("ArchitectureComponents")
                .and().put_to_shared_preferences_whit_key_$_and_value_$_succeed(PreferenceKeys.SAMPLE_DATA_KEY,
                        "ArchitectureComponents")
                .and().get_from_shared_preferences_with_key_$_return_$(PreferenceKeys.SAMPLE_DATA_KEY,
                        "ArchitectureComponents");
    }

    @NonNull
    public SampleGivenState contacts_source_on_query_$_return_$(@NonNull @Quoted String query,
                                                                @NonNull ContactsListState state) {
        Mockito.when(contactsSource.data(eq(query)))
                .thenReturn(Single.just(state));
        return self();
    }

    @NonNull
    public SampleGivenState server_response_$_with_body_$(int code, @NonNull @Quoted String path) {
        try {
            String json = getJson(path);
            currentStep.addAttachment(Attachment.json(json)
                    .withTitle(path).withFileName(path));
            server.enqueue(new MockResponse().setResponseCode(code)
                    .setBody(json)
                    .throttleBody(100L, 100L, TimeUnit.MILLISECONDS));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return self();
    }

    @Override
    protected void inject(@NonNull TestSampleComponent component) {
        component.inject(this);
    }

    private String getJson(@NonNull String path) throws Exception {
        String text;
        try (Scanner scanner = new Scanner(this.getClass().getResourceAsStream(path),
                "UTF-8")) {
            text = scanner.useDelimiter("\\A").next();
        }

        return text;
    }
}
