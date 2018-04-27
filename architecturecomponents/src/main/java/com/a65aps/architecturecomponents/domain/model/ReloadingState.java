package com.a65aps.architecturecomponents.domain.model;

import com.a65aps.architecturecomponents.domain.State;

public interface ReloadingState extends State {

    boolean hasData();

    boolean isLoading();
}
