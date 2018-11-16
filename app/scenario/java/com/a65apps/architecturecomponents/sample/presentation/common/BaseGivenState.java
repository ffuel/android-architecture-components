package com.a65apps.architecturecomponents.sample.presentation.common;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;

import com.a65apps.architecturecomponents.domain.permissions.PermissionState;
import com.a65apps.architecturecomponents.domain.permissions.PermissionsSource;
import com.a65apps.architecturecomponents.domain.receiver.ConnectionReceiverSource;
import com.a65apps.architecturecomponents.domain.receiver.ConnectionState;
import com.a65apps.architecturecomponents.sample.TestSampleComponent;
import com.a65apps.architecturecomponents.sample.TestsApp;
import com.a65apps.architecturecomponents.sample.presentation.main.MainActivity;
import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.AfterScenario;
import com.tngtech.jgiven.annotation.BeforeScenario;
import com.tngtech.jgiven.annotation.BeforeStage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import org.junit.Rule;
import org.mockito.Mockito;

import javax.inject.Inject;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.test.rule.ActivityTestRule;
import io.reactivex.Observable;
import io.reactivex.Single;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.eq;

public abstract class BaseGivenState<T extends BaseGivenState<T>> extends Stage<T> {

    private static final ConnectionState CONNECTED = ConnectionState.builder()
            .type(ConnectivityManager.TYPE_WIFI)
            .subtype(ConnectivityManager.TYPE_WIFI)
            .typeName("WIFI")
            .subtypeName("WIFI")
            .state(ConnectionState.State.CONNECTED)
            .detailedState(ConnectionState.DetailedState.CONNECTED)
            .reason("")
            .extraInfo("")
            .isFailover(false)
            .isAvailable(false)
            .isRoaming(false)
            .build();
    private static final ConnectionState DISCONNECTED = ConnectionState.builder()
            .type(ConnectivityManager.TYPE_WIFI)
            .subtype(ConnectivityManager.TYPE_WIFI)
            .typeName("WIFI")
            .subtypeName("WIFI")
            .state(ConnectionState.State.DISCONNECTED)
            .detailedState(ConnectionState.DetailedState.DISCONNECTED)
            .reason("")
            .extraInfo("")
            .isFailover(false)
            .isAvailable(false)
            .isRoaming(false)
            .build();

    @Rule
    @ProvidedScenarioState
    public ActivityTestRule<MainActivity> activityRule = new ActivityTestRule<>(MainActivity.class,
            false, false);

    @Inject
    ConnectionReceiverSource connectionSource;
    @Inject
    SharedPreferences sharedPreferences;
    @Inject
    PermissionsSource permissionsSource;
    @Inject
    @ProvidedScenarioState
    public ScreenshotCapture screenshotCapture;

    @NonNull
    private final SharedPreferences.Editor editor = Mockito.mock(SharedPreferences.Editor.class);

    @BeforeScenario
    public void init() {
        TestsApp app = TestsApp.getInstance();
        if (app == null) {
            throw new IllegalStateException();
        }
        inject(app.getComponent());
    }

    @AfterScenario
    public void tearDown() {
//      empty
    }

    @BeforeStage
    @CallSuper
    public void setup() throws Exception {
        Mockito.reset(connectionSource, sharedPreferences, permissionsSource);
    }

    @NonNull
    public T has_internet_connection() {
        Mockito.when(connectionSource.observeReceiver()).thenReturn(Observable.just(CONNECTED));
        Mockito.when(connectionSource.single()).thenReturn(Single.just(CONNECTED));

        return self();
    }

    @NonNull
    public T no_internet_connection() {
        Mockito.when(connectionSource.observeReceiver()).thenReturn(Observable.just(DISCONNECTED));
        Mockito.when(connectionSource.single()).thenReturn(Single.just(DISCONNECTED));

        return self();
    }

    @NonNull
    public T put_to_shared_preferences_whit_key_$_and_value_$_succeed(@NonNull @Quoted String key,
                                                                      @NonNull @Quoted String value) {

        Mockito.when(sharedPreferences.edit()).thenReturn(editor);
        Mockito.when(editor.putString(eq(key), eq(value))).thenReturn(editor);
        Mockito.when(editor.commit()).thenReturn(true);

        return self();
    }

    @NonNull
    public T get_from_shared_preferences_with_key_$_return_$(@NonNull @Quoted String key,
                                                             @NonNull @Quoted String value) {
        Mockito.when(sharedPreferences.getString(eq(key), eq(""))).thenReturn(value);

        return self();
    }

    @NonNull
    public T has_permission_$(@NonNull @Quoted String permission) {
        Mockito.when(permissionsSource.requestPermission(anyBoolean(), eq(permission)))
                .thenReturn(Single.just(PermissionState.GRANTED));

        return self();
    }

    protected abstract void inject(@NonNull TestSampleComponent component);
}
