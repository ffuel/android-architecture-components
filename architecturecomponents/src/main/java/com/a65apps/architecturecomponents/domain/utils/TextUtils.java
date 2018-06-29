package com.a65apps.architecturecomponents.domain.utils;

import android.support.annotation.Nullable;

public final class TextUtils {

    private TextUtils() {

    }

    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }
}
