package com.a65apps.daggerarchitecturecomponents.presenter;

public interface PresenterComponentBuilder<C extends PresenterComponent> {

    C build();
}
