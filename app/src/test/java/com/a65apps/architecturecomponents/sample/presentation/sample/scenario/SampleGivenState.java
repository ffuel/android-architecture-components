package com.a65apps.architecturecomponents.sample.presentation.sample.scenario;

import android.support.annotation.NonNull;

import com.a65apps.architecturecomponents.domain.source.SingleSource;
import com.a65apps.architecturecomponents.sample.TestSampleComponent;
import com.a65apps.architecturecomponents.sample.presentation.common.BaseGivenState;
import com.a65apps.architecturecomponents.sample.presentation.common.Quoted;
import com.a65apps.daggerarchitecturecomponents.source.Remote;

import org.mockito.Mockito;

import javax.inject.Inject;

import androidx.annotation.CallSuper;
import io.reactivex.Single;

public class SampleGivenState extends BaseGivenState<SampleGivenState> {

    @Inject
    @Remote
    SingleSource<String> source;

    @Override
    @CallSuper
    public void setup() {
        super.setup();
        //noinspection unchecked
        Mockito.reset(source);
    }

    @NonNull
    SampleGivenState network_source_emit_$_data(@NonNull @Quoted String data) {
        Mockito.when(source.data()).thenReturn(Single.just(data));
        return self();
    }

    @NonNull
    SampleGivenState network_source_emit_$_error(@NonNull @Quoted String error) {
        Mockito.when(source.data()).thenReturn(Single.error(new RuntimeException(error)));
        return self();
    }

    @NonNull
    SampleGivenState network_source_no_emit_data() {
        Mockito.when(source.data()).thenReturn(Single.never());
        return self();
    }

    @Override
    protected void inject(@NonNull TestSampleComponent component) {
        component.inject(this);
    }
}
