package com.a65apps.architecturecomponents.domain.model;

import com.a65apps.architecturecomponents.domain.State;

public interface ReloadingState extends State {

    boolean hasData();

    boolean isLoading();
}
