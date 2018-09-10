package com.a65apps.architecturecomponents.domain.model;

import io.reactivex.annotations.NonNull;

class AppendOnlyList<T> {

    private final int capacity;
    private final Object[] head;
    @SuppressWarnings("PMD.UnusedPrivateField")
    private Object[] tail;
    private int offset;

    AppendOnlyList(int capacity) {
        this.capacity = capacity;
        this.head = new Object[capacity + 1];
        this.tail = head;
    }

    void add(T value) {
        final int c = capacity;
        int o = offset;
        if (o == c) {
            Object[] next = new Object[c + 1];
            tail[c] = next;
            tail = next;
            o = 0;
        }
        tail[o] = value;
        offset = o + 1;
    }

    @SuppressWarnings("unchecked")
    public void forEachWhile(@NonNull Predicate<? super T> consumer) {
        Object[] a = head;
        final int c = capacity;
        while (a != null) {
            for (int i = 0; i < c; i++) {
                Object o = a[i];
                if (o == null || consumer.test((T) o)) {
                    break;
                }
            }
            a = (Object[]) a[c];
        }
    }

    public interface Predicate<T> {
        boolean test(@NonNull T t);
    }
}
