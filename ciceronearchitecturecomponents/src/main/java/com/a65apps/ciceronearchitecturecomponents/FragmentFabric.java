package com.a65apps.ciceronearchitecturecomponents;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public interface FragmentFabric {

    @NonNull
    Fragment build(@NonNull Bundle bundle, @Nullable Object data);
}
