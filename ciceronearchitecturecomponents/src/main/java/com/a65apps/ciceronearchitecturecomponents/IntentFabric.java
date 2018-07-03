package com.a65apps.ciceronearchitecturecomponents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface IntentFabric {

    @NonNull
    Intent build(@NonNull Context context, @NonNull Bundle bundle,
                 @Nullable Object data);
}
