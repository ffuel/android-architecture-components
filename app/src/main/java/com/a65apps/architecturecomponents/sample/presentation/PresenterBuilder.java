package com.a65apps.architecturecomponents.sample.presentation;

import dagger.Module;

@Module(includes = {
        com.a65apps.architecturecomponents.sample.presentation.main.MainPresenterComponent_BindMainPresenter.class
})
public interface PresenterBuilder {
}
