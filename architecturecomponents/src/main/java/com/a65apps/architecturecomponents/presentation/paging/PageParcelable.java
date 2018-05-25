package com.a65apps.architecturecomponents.presentation.paging;

import android.os.Parcelable;

import com.a65apps.architecturecomponents.domain.paging.PageState;

public abstract class PageParcelable<T> extends PageState<T> implements Parcelable {
}
