package com.a65apps.architecturecomponents.presentation.navigationv2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

public interface IntentFactory {

    @NonNull
    Intent build(@NonNull Context context, @NonNull Bundle bundle,
                 @NonNull Screen screen);
}
