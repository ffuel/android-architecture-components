package com.a65apps.architecturecomponents.domain.paging;

import android.support.annotation.NonNull;

public final class PagingConfig {

    private final int pageSize;
    private final int pageLoadingOffset;
    private final int pageBoundaryOffset;

    @NonNull
    public static PagingConfig.Builder builder(int pageSize) {
        return new PagingConfig.Builder(pageSize);
    }

    private PagingConfig(@NonNull PagingConfig.Builder builder) {
        this.pageSize = builder.pageSize;
        this.pageLoadingOffset = builder.pageLoadingOffset;
        this.pageBoundaryOffset = builder.pageBoundaryOffset;
    }

    public int pageSize() {
        return pageSize;
    }

    public int pageLoadingOffset() {
        return pageLoadingOffset;
    }

    public int getPageBoundaryOffset() {
        return pageBoundaryOffset;
    }

    public static final class Builder {

        private final int pageSize;
        private int pageLoadingOffset;
        private int pageBoundaryOffset = 0;

        private Builder(int pageSize) {
            this.pageSize = pageSize;
            this.pageLoadingOffset = pageSize;
        }

        @NonNull
        public Builder withLoadingOffset(int pageLoadingOffset) {
            this.pageLoadingOffset = pageLoadingOffset;
            return this;
        }

        @NonNull
        public Builder withPageBoundaryOffset(int pageBoundaryOffset) {
            this.pageBoundaryOffset = pageBoundaryOffset;
            return this;
        }

        @NonNull
        public PagingConfig build() {
            return new PagingConfig(this);
        }
    }
}
