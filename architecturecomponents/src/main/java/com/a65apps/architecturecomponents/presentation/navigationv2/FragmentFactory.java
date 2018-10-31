package com.a65apps.architecturecomponents.presentation.navigationv2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

public interface FragmentFactory {

    @NonNull
    Fragment build(@NonNull Bundle bundle, @NonNull Screen screen);
}
