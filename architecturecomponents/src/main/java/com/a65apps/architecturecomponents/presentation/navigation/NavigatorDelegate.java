package com.a65apps.architecturecomponents.presentation.navigation;

import android.support.annotation.UiThread;

@UiThread
public interface NavigatorDelegate {

    void onAttach();

    void onDetach();
}
